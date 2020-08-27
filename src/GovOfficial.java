import java.util.Calendar;
import java.util.Scanner;
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
        System.out.println("Unassigned Cases:");
        for (Case i : UserSystem.getCases()) {
            if (i.getTracer().equals("000")) { // no tracer assigned
                System.out.println(i.getCaseNum()); // display case num
                caseNums[ctr++] = i.getCaseNum(); // add case num to array of caseNums
            }
        }

        if (ctr == 0) { // no cases
            System.out.println("No cases to display.");
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

//    /**
//     * Asks the user what filter will be used when displaying the cases, can be:
//     * within a date range, within a city (case insensitive), or both.
//     */
//    private void showAnalytics() {
//        int opt;
//        Predicate<Case> filter = null;
//
//        do {
//            ANALYTICS_MENU.display();
//
//            switch (opt) {
//                case 1 -> {
//                    Calendar[] dates = obtainDateRange();
//                    String city = obtainValidCity();
//
//                    filter = (case1) -> {
//                        Citizen temp = UserSystem.getUser(case1.getUsername());
//
//                        if (temp != null) {
//                            return case1.getReportDate().compareTo(dates[0]) >= 0
//                                    && case1.getReportDate().before(dates[1])
//                                    && temp.getHomeAddress().toUpperCase().contains(city);
//                        } else {
//                            return false;
//                        }
//                    };
//                }
//                case 2 -> {
//                    Calendar[] dates = obtainDateRange();
//
//                    filter = (case1) -> case1.getReportDate().compareTo(dates[0]) >= 0 &&
//                            case1.getReportDate().before(dates[1]);
//                }
//                case 3 -> {
//                    String city = obtainValidCity();
//
//                    filter = (case1) -> {
//                        Citizen temp = UserSystem.getUser(case1.getUsername());
//
//                        if (temp != null) {
//                            return temp.getHomeAddress().toUpperCase().contains(city);
//                        } else {
//                            return false;
//                        }
//                    };
//                }
//            }
//
//            if (opt != 4) {
//                countCases(filter);
//            }
//        } while (opt != ANALYTICS_MENU.length);
//    }

    /**
     * Iterates through all the cases and counts the cases that pass
     * the test (filter). Displays the final count.
     * @param filter the test to be performed on each entry.
     */
    private void countCases(Predicate<Case> filter) {
        int ctr = 0;

        System.out.println();
        for (Case i: UserSystem.getCases()) {
            if (filter.test(i)) {
                ctr++;
            }
        }

        if (ctr != 0) {
            System.out.println("Number of cases that match the criteria:");
            System.out.println(ctr);
        } else {
            System.out.println("No cases match the criteria specified.");
        }
        System.out.println();
    }

    /**
     * Obtains an input from the user and check if it is a valid input for a city
     * or not (empty or contains a number and other special characters).<br>
     * Precondition: There are no cities which names have numbers and special symbols in them except
     * apostrophes, periods, hyphens, and accented characters.
     * @return the valid String representing the chosen city in uppercase form.
     */
    private static String obtainValidCity() {
        Scanner input = new Scanner(System.in);
        System.out.print("Input city: ");
        String city = input.nextLine();

        // checks if the input is valid, loops if not
        // first, it replaces all invalid characters with "1" before checking for no numbers
        while (city.isEmpty() ||
                !city.replaceAll("[^-.'\\s\\w\\u00C0-\\u00FF]+", "1").matches("\\D+")) {
            System.out.println("Invalid input for city!\n");
            System.out.print("Input city: ");
            city = input.nextLine();
        }
        return city.toUpperCase();
    }

    /**
     * Handles the role modification of an existing account or the creation
     * of a new account.
     * @param role the new role to be assigned to an account.
     * @param username the username of the account to be modified or the account to be made.
     */
    public void modifyRole(String role, String username) {
        int index = UserSystem.getIndexOf(username);

        if (username.equals(this.getUsername())) {
            System.out.println("You cannot modify your own role!");
        } else if (index != -1) {
            if (UserSystem.getRoleOf(index).equals(role)) {
                switch (role) {
                    case "official" -> System.out.println("Account is already a government official!");
                    case "tracer" -> System.out.println("Account is already a contact tracer!");
                    default -> System.out.println("Cannot terminate citizen account!");
                }
            } else {
                UserSystem.setRoleOf(index, role);
            }
        } else if (!role.equals("citizen")) {  // if not terminating account to citizen
            if(Menu.YorN("This user does not exist. Create account") == 'Y') {
                Driver.register(username, 1);
                UserSystem.setRoleOf(UserSystem.getNumUsers() - 1, role);
            }
        } else {
            System.out.println("Account does not exist!");
        }
    }
}