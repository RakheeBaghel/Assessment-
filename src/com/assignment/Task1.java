import java.util.*;

public class Task1 {

    public List<LoanAccount> getOverdueLoans(List<LoanAccount> accounts) {

        //Initialize the result list instead of keeping it null Earlier it was null, so result.add(account) was throwing NullPointerException.
        List<LoanAccount> result = new ArrayList<>();

        // Return an empty list if the input list is null. This prevents NullPointerException while iterating over accounts.
        if (accounts == null) {
            return result;
        }


        for (LoanAccount account : accounts) {

            // Check if dueDate is null before calling before().
            // The problem statement says dueDate may be null for restructured accounts. Calling before() on a null object causes NullPointerException.
            if (account.getDueDate() != null &&
                    account.getDueDate().before(new Date())) {



                // Add only overdue accounts having a positive outstanding balance. Accounts with zero balance are already paid and should not be returned.
                if (account.getOutstandingBalance() > 0) {
                    result.add(account);
                }
            }
        }

        return result;
    }


}