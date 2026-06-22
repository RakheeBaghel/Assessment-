package com.assignment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Task4 {

    private DataSource dataSource;

    public List<ReportEntry> fetchMonthlyReport(String accountId,
                                                int month,
                                                int year)
            throws SQLException {

        List<ReportEntry> entries = new ArrayList<>();

        //Use try-with-resources to automatically close. Connection and PreparedStatement after use.
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM report_entries " +
                             "WHERE account_id = ? AND MONTH(entry_date) = ? " +
                             "AND YEAR(entry_date) = ?")) {

            ps.setString(1, accountId);
            ps.setInt(2, month);
            ps.setInt(3, year);

            // Use try-with-resources for ResultSet so it is automatically closed after processing.
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    entries.add(mapRow(rs));
                }
            }
        }

        return entries;
    }
}
