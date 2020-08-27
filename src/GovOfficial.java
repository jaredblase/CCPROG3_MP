import java.util.Calendar;
import java.util.function.Predicate;

/**
 * This GovOfficial class extends the Citizen class and includes administrator
 * facilities such as viewing analytics, assigning cases, and promoting or terminating accounts.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 0.1
 * @see Citizen
 */
public class GovOfficial extends Citizen {
    /** The String array containing the menu options of the user. */
    protected static Menu userMenu = new Menu("User","Check in", "Report positive",
            "Update profile information", "Show Unassigned Cases", "Show Contact Tracing Updates",
            "Analytics", "Create Government Official Account", "Create Contact Tracer Account",
            "Terminate Account", "Logout");

    /** The String array containing the analytics menu options of the user. */
    public static final Menu ANALYTICS_MENU = new Menu("Analytics",
            "Number of positive cases in a city within a duration",
            "Number of positive cases within a duration",
            "Number of positive cases in a city", "Back");

    /**
     * Receives a Citizen class and makes an exact copy of its attributes.
     * @param citizen the object used to construct the new object.
     */
    public GovOfficial(Citizen citizen) {
        super(citizen);
    }

    /**
     * Returns the Menu object for the Citizen class.
     * @return the Menu object for the Citizen class.
     */
    @Override
    public Menu getUserMenu() {
        return userMenu;
    }

    /**
     * Displays all the case number of unassigned cases and returns the array
     * of those case numbers.
     * @return the array of case numbers of unassigned cases.
     */
    public int[] showUnassigned() {
        int[] caseNums = new int[Case.getCount()];
        int ctr = 0;

        for (Case i : UserSystem.getCases()) {
            if (i.getTracer().equals("000")) { // no tracer assigned
                System.out.println(i.getCaseNum()); // display case num
                caseNums[ctr++] = i.getCaseNum(); // add case num to array of caseNums
            }
        }

        if (ctr == 0) { // no cases
            caseNums = null;
        }

        return caseNums;
    }

    /**
     * Assigns a case to a contact tracer based on the given case number and the given
     * username of the contact tracer.
     * @param caseNum the case number of the case to be assigned.
     * @param tracer the username of the contact tracer the case is to be assigned to.
     */
    public void assignCase(int caseNum, String tracer) {
        for (Case i: UserSystem.getCases()) {
            if (i.getCaseNum() == caseNum) {
                i.setTracer(tracer);
                break;
            }
        }
    }

    /**
     * Displays a list of positive cases and their statuses from a given date range.
     * @param dates the date range.
     */
    public void showUpdates(Calendar[] dates) {
        for (Case i: UserSystem.getCases()) {
            if (i.getReportDate().compareTo(dates[0]) >= 0 && i.getReportDate().before(dates[1])) {
                System.out.println(i.toString());
            }
        }
    }

    /**
     * Filters the cases given the criteria (parameter) and returns the count.
     * @param dates the date range.
     * @param city the name of the city.
     * @return the number of cases that fit the criteria.
     * @throws Exception if an invalid city was received.
     */
    public int showAnalytics(Calendar[] dates, String city) throws Exception {
        final String CITY = city.toUpperCase();
        if (isInvalidCity(CITY)) {
            throw new Exception("Invalid City!");
        }

        Predicate<Case> filter = (case1) -> {
            Citizen temp = UserSystem.getUser(case1.getUsername());

            if (temp != null) {
                return case1.getReportDate().compareTo(dates[0]) >= 0
                        && case1.getReportDate().before(dates[1])
                        && temp.getHomeAddress().toUpperCase().contains(CITY);
            } else {
                return false;
            }
        };

        return countCases(filter);
    }

    /**
     * Filters the cases given the criteria (parameter) and returns the count.
     * @param dates the date range.
     * @return the number of cases that fit the criteria.
     */
    public int showAnalytics(Calendar[] dates) {
        Predicate<Case> filter = (case1) -> case1.getReportDate().compareTo(dates[0]) >= 0 &&
                    case1.getReportDate().before(dates[1]);

        return countCases(filter);
    }

    /**
     * Filters the cases given the criteria (parameter) and returns the count.
     * @param city the name of the city.
     * @return the number of cases that fit the criteria.
     * @throws Exception if an invalid city was received.
     */
    public int showAnalytics(String city) throws Exception {
        final String CITY = city.toUpperCase();
        if (isInvalidCity(CITY)) {
            throw new Exception("Invalid city!");
        }

        Predicate<Case> filter = (case1) -> {
            Citizen temp = UserSystem.getUser(case1.getUsername());

            if (temp != null) {
                return temp.getHomeAddress().toUpperCase().contains(CITY);
            } else {
                return false;
            }
        };

        return countCases(filter);
    }

    /**
     * Iterates through all the cases and counts the cases that pass
     * the test (filter). Displays the final count.
     * @param filter the test to be performed on each entry.
     */
    private int countCases(Predicate<Case> filter) {
        int ctr = 0;

        for (Case i: UserSystem.getCases()) {
            if (filter.test(i)) {
                ctr++;
            }
        }
        return ctr;
    }

    /**
     * Obtains an input from the user and check if it is a valid input for a city
     * or not (empty or contains a number and other special characters).<br>
     * Precondition: There are no cities which names have numbers and special symbols in them except
     * apostrophes, periods, hyphens, and accented characters.
     * @return the valid String representing the chosen city in uppercase form.
     */
    private static boolean isInvalidCity(String city) {
        // checks if the input is valid, loops if not
        // first, it replaces all invalid characters with "1" before checking for no numbers
        return city.isEmpty() ||
                !city.replaceAll("[^-.'\\s\\w\\u00C0-\\u00FF]+", "1").matches("\\D+");

    }

    /**
     * Handles the role modification of an existing account or the creation
     * of a new account.
     * @param role the new role to be assigned to an account.
     * @param username the username of the account to be modified or the account to be made.
     * @throws Exception if invalid operations are performed.
     */
    public void modifyRole(String role, String username) throws Exception {
        int index = UserSystem.getIndexOf(username);

        if (username.equals(this.getUsername())) {
            throw new Exception("You cannot modify your own role!");
        } else if (index != -1) {
            if (UserSystem.getRoleOf(index).equals(role)) {
                switch (role) {
                    case "official" -> throw new Exception("Account is already a government official!");
                    case "tracer" -> throw new Exception("Account is already a contact tracer!");
                    default -> throw new Exception("Cannot terminate citizen account!");
                }
            } else {
                UserSystem.setRoleOf(index, role);
            }
        } else if (!role.equals("citizen")) {  // if not terminating account to citizen
            throw new AccountCreationNeededException();
        } else {
            throw new Exception("Account does not exist!");
        }
    }
}

class AccountCreationNeededException extends Exception {

}