package com.assignment;

import javax.swing.text.Document;
import java.util.List;

public class Task5 {

    //Use SLF4J logger instead of printStackTrace()
    private static final Logger logger = LoggerFactory.getLogger(DocumentValidator.class);

    public ValidationResult validate(Document doc) {
        try {

            // FIX: Throw IllegalArgumentException for expected validation failures
            if (doc == null) {
                throw new IllegalArgumentException("Document is null");
            }

            String content = doc.extractContent();

            // FIX: Handle null/empty content properly
            if (content == null || content.isEmpty()) {
                throw new IllegalArgumentException("Empty content");
            }

            return runValidationRules(content);

        } catch (IllegalArgumentException e) {
            //Log expected validation failures without stack trace
            logger.warn("Validation failed: {}", e.getMessage());

            //Return a valid ValidationResult instead of null
            return ValidationResult.invalid(e.getMessage());

        } catch (Exception e) {
            //Log unexpected runtime errors with stack trace
            logger.error("Unexpected error while validating document", e);

            return ValidationResult.invalid("Unexpected validation error");
        }
    }

    public void validateBatch(List<Document> docs) {

        for (Document doc : docs) {
            try {

                ValidationResult r = validate(doc);

                //Check for null/invalid result before using it
                if (r != null && r.isValid()) {
                    saveResult(r);
                }

            } catch (Exception e) {
                // Do not swallow exceptions; log them
                logger.error("Error while processing document in batch", e);
            }
        }
    }
}
