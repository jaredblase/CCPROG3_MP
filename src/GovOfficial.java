import java.util.Calendar;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 * This Government class extends the Citizen class and includes administrator
 * facilities such as viewing analytics, assigning cases, and promoting or terminating accounts.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 0.1
 * @see Citizen
 */
public class GovOfficial extends Citizen {
    /** The String array containing the menu options of the user */
    protected static String[] menuOptions = {"Check in", "Report positive", "Update profile information",
            "Show Unassigned Cases", "Show Contact Tracing Updates", "Analytics",
            "Create Government Official Account", "Create Contact Tracer Account", "Terminate Account", "Logout"};

    /** The String array containing the analytics menu options of the user */
    private static final String[] ANALYTICS_MENU = {"Number of positive cases in a city within a duration",
            "Number of positive cases within a duration", "Number of positive cases in a city", "Back"};

    /**
     * Receives a Citizen class and makes an exact copy of its attributes.
     * @param citizen the object used to construct the new object.
     */
    public GovOfficial(Citizen citizen) {
        super(citizen);
    }

    /**
     * Main entry point of the user after logging in.
     */
    @Override
    public void showMenu() {
        int opt;

        do {
            super.prompt();
            opt = Menu.display("User", menuOptions);
            chooseMenu(opt);
        } while (opt != menuOptions.length);

        super.logOut();
    }

    /**
     * Calls the appropriate function based on the user's input.
     * @param opt integer representing the chosen menu option.
     */
    @Override
    protected void chooseMenu(int opt) {
        if (opt < 4) {
            super.chooseMenu(opt);
        } else {
            switch (opt) {
                case 4 -> showUnassigned();
                case 5 -> showUpdates();
                case 6 -> showAnalytics();
                case 7 -> modifyRole("official");
                case 8 -> modifyRole("tracer");
                case 9 -> modifyRole("citizen");
            }
        }
    }

    /**
     * Displays all the unassigned cases and displays their case numbers.
     */
    private void showUnassigned() {
        int[] caseNums = new int[Case.getCount()];
        int ctr = 0;
        System.out.println("Unassigned Cases:");
        for (Case i: UserSystem.getCases()) {
            if (i.getTracer().equals("000")) { // no tracer assigned
                System.out.println(i.getCaseNum()); // display case num
                caseNums[ctr++] = i.getCaseNum(); // add case num to array of caseNums
            }
        }

        if (ctr == 0) { // no cases
            System.out.println("No cases to display.");
        } else {
            if (UserSystem.getNumTracers() == 0) // no tracers
                System.out.println("Create contact tracer accounts to assign cases.\n");
            else {
                Scanner input = new Scanner(System.in);
                boolean status = false;
                int posCase = 0;

                // get case number
                do {
                    try {
                        System.out.print("Assign case number: ");
                        posCase = Integer.parseInt(input.nextLine());

                        for (int j = 0; j < ctr; j++)
                            if (caseNums[j] == posCase) {
                                status = true;
                                break;
                            }

                        if (!status)
                            throw new Exception();
                    } catch (Exception e) {
                        System.out.println("Invalid input!\n");
                    }
                } while (!status);

                status = false;

                // get tracer username
                do {
                    System.out.print("Enter username of tracer: ");
                    String tracer = input.nextLine();

                    int index = UserSystem.getIndexOf(tracer);
                    if (index == -1) { // user not found
                        System.out.println("No user with username \"" + tracer + "\" found.\n");
                    } else if (UserSystem.getRoleOf(index).equals("tracer")) {
                        status = true;

                        // assign tracer
                        for (Case i : UserSystem.getCases()) {
                            if (i.getCaseNum() == posCase) {
                                i.setTracer(tracer);
                                break;
                            }
                        }
                    } else // user is found but is not a tracer
                        System.out.println("User " + tracer + " is not a contact tracer.\n");
                } while (!status);
            }
        }
    }

    /**
     * Displays a list of positive cases from a specific input date range and
     * their status.
     */
    private void showUpdates() {
        Calendar[] dates = obtainDateRange();

        for (Case i: UserSystem.getCases()) {
            if (i.getReportDate().after(dates[0]) && i.getReportDate().before(dates[1])) {
                System.out.println(i);
            }
        }
    }

    /**
     * Asks the user what filter will be used when displaying the cases, can be:
     * within a date range, within a city, or both.
     */
    private void showAnalytics() {
        int opt;
        Predicate<Case> filter = null;

        do {
            opt = Menu.display("Analytics", ANALYTICS_MENU);

            switch (opt) {
                case 1 -> {
                    Calendar[] dates = obtainDateRange();
                    String city = obtainValidCity();

                    filter = (case1) -> {
                        Citizen temp = UserSystem.getUser(case1.getUsername());

                        if (temp != null) {
                            return case1.getReportDate().after(dates[0]) && case1.getReportDate().before(dates[1])
                                    && temp.getHomeAddress().contains(city);
                        } else {
                            return false;
                        }
                    };
                }
                case 2 -> {
                    Calendar[] dates = obtainDateRange();

                    filter = (case1) -> case1.getReportDate().after(dates[0]) && case1.getReportDate().before(dates[1]);
                }
                case 3 -> {
                    String city = obtainValidCity();

                    filter = (case1) -> {
                        Citizen temp = UserSystem.getUser(case1.getUsername());

                        if (temp != null) {
                            return temp.getHomeAddress().contains(city);
                        } else {
                            return false;
                        }
                    };
                }
            }

            if (opt != 4) {
                countCases(filter);
            }
        } while (opt != ANALYTICS_MENU.length);
    }

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
     * Obtains the start and end date from the user. The first element in the
     * returned Calendar array is the starting date while the second is the end date.
     * @return the start and end date by means of a Calendar array.
     */
    private Calendar[] obtainDateRange() {
        Calendar start, end;

        System.out.println("Starting date:");
        start = getDate();
        System.out.println("\nEnd date:");
        end = getDate();
        while (start.after(end)) { // check if the input date is before the start date
            System.out.println("Invalid end date!");
            System.out.println("\nEnd date:");
            end = getDate();
        }

        return new Calendar[] {start, end};
    }

    /**
     * Obtains an input from the user and check if it is a valid input for a city
     * or not (empty or contains a number and other special characters).<br>
     * Precondition: There are no cities which names have numbers and special symbols in them except
     * apostrophes, periods, and hyphens.
     * @return the valid String representing the chosen city.
     */
    private static String obtainValidCity() {
        Scanner input = new Scanner(System.in);
        System.out.print("Input city: ");
        String city = input.nextLine();

        // checks if the input is valid, loops if not
        while (city.isEmpty() || !city.replaceAll("[^-.'\\s\\w]+", "1").matches("\\D+")) {
            System.out.println("Invalid input for city!\n");
            System.out.print("Input city: ");
            city = input.nextLine();
        }
        return city;
    }

    /**
     * Handles the role modification of an existing account.
     * @param role the new role to be assigned to an account.
     */
    private void modifyRole(String role) {
        Scanner input = new Scanner(System.in);
        System.out.print("Account username to be modified: ");
        String username = input.nextLine();
        int index = UserSystem.getIndexOf(username);

        if (username.equals(this.getUsername())) {
            System.out.println("You cannot modify your own role!");
        } else if (index != -1) {
            if (UserSystem.getRoleOf(index).equals(role)) {
                switch (role) {
                    case "official" -> System.out.println("Account is already a government official!");
                    case "tracer" -> System.out.println("Account is already a contact tracer!");
                    default -> System.out.println("Account is already a citizen!");
                }
            } else {
                UserSystem.setRoleOf(index, role);
            }
        } else if (!role.equals("citizen")) {  // if not terminating account to citizen
            UserSystem.register(username);
            UserSystem.setRoleOf(UserSystem.getNumUsers() - 1, role);
        } else {
            System.out.println("Account does not exist!");
        }
    }
}