package model;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.function.Predicate;

/**
 * This GovOfficial class extends the Citizen class and includes administrator
 * facilities such as viewing analytics, assigning cases, and promoting or terminating accounts.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 1.1
 * @see Citizen
 */
public class GovOfficial extends Citizen {
    /** The Menu object for the menu options of the user. */
    protected static Menu userMenu = new Menu("User","Check in", "Report positive",
            "Update profile information", "Show Unassigned Cases", "Show Contact Tracing Updates",
            "Analytics", "Create Government Official Account", "Create Contact Tracer Account",
            "Terminate Account", "Logout");

    /** The Menu object for the analytics menu options of the user. */
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
     * Returns the Menu object for the GovOfficial class.
     * @return the Menu object for the GovOfficial class.
     */
    @Override
    public Menu getUserMenu() {
        return userMenu;
    }

    /**
     * Displays all the case numbers of unassigned cases and returns the array
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
     * Assigns the given case to a contact tracer based on the given username
     * of the contact tracer.
     * @param positive the case number of the case to be assigned.
     * @param tracer the username of the contact tracer the case is to be assigned to.
     */
    public void assignCase(Case positive, String tracer) {
        positive.setTracer(tracer);
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

    public boolean filter(Case c, String city, char status, LocalDate start, LocalDate end) {
        if (city == null) {
            return filter(c, status, start, end);
        }

        Citizen temp = UserSystem.getUser(c.getUsername());
        return temp != null && temp.getHomeAddress().toUpperCase().contains(city.toUpperCase()) && filter(c, status, start, end);
    }

    public boolean filter(Case c, char status, LocalDate start, LocalDate end) {
        return (status == '\0')? filter(c, start, end) : filter(c, start, end) &&status == c.getStatus();
    }

    public boolean filter(Case c, LocalDate start, LocalDate end) {
        Calendar dateStart, dateEnd;
        dateStart = Calendar.getInstance();
        dateEnd = Calendar.getInstance();

        // to remove time values
        dateStart.clear();
        dateEnd.clear();

        if (start == null && end == null) {
            return true;
        } else if (start != null && end != null) {
            dateStart.set(start.getYear(), start.getMonthValue() - 1, start.getDayOfMonth());
            dateEnd.set(end.getYear(), end.getMonthValue() - 1, end.getDayOfMonth());

            return c.getReportDate().compareTo(dateStart) >= 0 && c.getReportDate().before(dateEnd);
        } else if (end != null) {
            dateEnd.set(end.getYear(), end.getMonthValue() - 1, end.getDayOfMonth());
            return c.getReportDate().before(dateEnd);
        }

        dateStart.set(start.getYear(), start.getMonthValue() - 1, start.getDayOfMonth());
        return c.getReportDate().compareTo(dateStart) >= 0;
    }

    /**
     * Iterates through all the cases and counts the cases that pass
     * the test (filter). Returns the final count.
     * @param filter the test to be performed on each entry.
     * @return the number of cases that fit the criteria.
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
     * Obtains an input from the user and checks if it is a invalid input (empty or contains a number
     * and other special characters) for a city or not.<br>
     * Precondition: There are no cities which names have numbers and special symbols in them except
     * apostrophes, periods, hyphens, and accented characters.
     * @param city the name to be checked for validity.
     * @return true if the input is invalid, false otherwise.
     */
    private static boolean isInvalidCity(String city) {
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
        if (username.isBlank()) {
            throw new Exception("Invalid username!");
        }

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
            throw new AccountCreationNeededException(role);
        } else {
            throw new Exception("Account does not exist!");
        }
    }
}

class AccountCreationNeededException extends Exception {
    private final String ROLE;

    public AccountCreationNeededException(String role) {
        this.ROLE = role;
    }

    public String getRole() {
        return ROLE;
    }
}