import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 * This class is used
 *
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 0.1
 * @see Citizen
 */
public class GovOfficial extends Citizen {
    /** */
    private static final String[] analyticsMenu = {"Number of positive cases in a city within a duration",
            "Number of positive cases within a duration", "Number of positive cases in a city", "Back"};

    /**
     *
     * @param name the Name object containing the name of the user
     * @param homeAddress the home address of the user
     * @param officeAddress the office address of the user
     * @param phoneNumber the phone number of the user
     * @param email the email address of the user
     * @param username the username of the user
     */
    public GovOfficial(Name name, String homeAddress, String officeAddress, String phoneNumber,
                       String email, String username, ArrayList<Visit> visit) {
        super(name, homeAddress, officeAddress, phoneNumber, email, username, visit);
        String[] temp = new String[menuOptions.length + 6];
        System.arraycopy(menuOptions, 0, temp, 0, menuOptions.length);
        temp[3] = "Show Unassigned Cases";
        temp[4] = "Show Contact Tracing Updates";
        temp[5] = "Analytics";
        temp[6] = "Create Government Official Account";
        temp[7] = "Create Contact Tracer Account";
        temp[8] = "Terminate Account";
        temp[9] = "Logout";
        menuOptions = temp;
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
                case 7 -> promoteAccount("official");
                case 8 -> promoteAccount("tracer");
                case 9 -> modifyRole("citizen");
            }
        }
    }

    private void showUnassigned() {
        int[] caseNums = new int[Case.getCount()];
        int ctr = 0;
        System.out.println("Unassigned Cases:");
        for (Case i: UserSystem.getCases()) {
            if (i.getTracer().equals("000")) {
                System.out.println(i.getCaseNum());
                caseNums[ctr++] = i.getCaseNum();
            }
        }


        if (UserSystem.getNumTracers() == 0)
            System.out.println("Create contact tracer accounts to assign cases.\n");
        else {
            Scanner input = new Scanner(System.in);
            boolean status = false;
            int posCase = 0;

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

            do {
                System.out.print("Enter username of tracer: ");
                String tracer = input.nextLine();

                int index = UserSystem.getIndexOf(tracer);
                if (index == -1) {
                    System.out.println("No user with username \"" + tracer + "\" found.\n");
                } else if (UserSystem.getRoleOf(index).equals("tracer")) {
                    status = true;

                    for (Case i: UserSystem.getCases()) {
                        if (i.getCaseNum() == posCase) {
                            i.setTracer(tracer);
                            break;
                        }
                    }
                } else
                    System.out.println("User " + tracer + " is not a contact tracer.\n");
            } while (!status);
        }
    }

    private void showUpdates() {

    }

    /**
     * Asks the user what filter will be used when displaying the cases, can be:
     * within a date range, within a city, or both.
     */
    private void showAnalytics() {
        int opt;
        Predicate<Case> filter = null;

        do {
            opt = Menu.display("Analytics", analyticsMenu);

            switch (opt) {
                case 1 -> {
                    Scanner input = new Scanner(System.in);
                    Calendar[] dates = obtainDateRange();
                    System.out.print("City: ");
                    String city = input.nextLine();

                    filter = (case1) -> case1.getReportDate().after(dates[0]) && case1.getReportDate().before(dates[1]);
                }
                case 2 -> {
                    Calendar[] dates = obtainDateRange();

                    filter = (case1) -> case1.getReportDate().after(dates[0]) && case1.getReportDate().before(dates[1]);
                }
                case 3 -> {
                    Scanner input = new Scanner(System.in);
                    String city = input.nextLine();

//                    filter = (case1) -> {
//                        return case1.get
//                    };
                }
            }

            if (opt != 4) {
                displayCases(filter);
            }
        } while (opt != analyticsMenu.length);
    }

    /**
     * Iterates through all the cases and displays only what passes
     * the test (filter).
     * @param filter is the test to be done on each entry.
     */
    private void displayCases(Predicate<Case> filter) {
        boolean found = false;

        System.out.println();
        for (Case i: UserSystem.getCases()) {
            if (filter.test(i)) {
                System.out.println(i);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No cases match the criteria specified.\n");
        }
    }

    /**
     * Obtains the start and end date from the user. The first element in the
     * returned Calendar array is the starting date while the second is the end date.
     * @return the start and end date through a Calendar array
     */
    private Calendar[] obtainDateRange() {
        Calendar start, end;

        System.out.println("Starting date:");
        start = getDate();
        do {
            System.out.println("End date");
            end = getDate();
        } while (start.after(end)); // check if the input date is after the start date

        return new Calendar[] {start, end};
    }

    /**
     * Handles the promotion of an account
     * @param role the role to be promoted to
     */
    private void promoteAccount(String role) {
        if (Menu.YorN("Does the user already have a registered account") == 'Y') {
            modifyRole(role);
        } else {
            UserSystem.register();
            UserSystem.setRoleOf(UserSystem.getNumUsers() - 1, role);
        }
    }

    /**
     * Handles the role modification of an existing account.
     * @param role the new role to be assigned to an account.
     */
    private void modifyRole(String role) {
        Scanner input = new Scanner(System.in);
        System.out.print("Account username to be modified: ");
        int index = UserSystem.getIndexOf(input.nextLine());

        if (index != -1) {
            if (UserSystem.getRoleOf(index).equals(role)) {
                System.out.println("Account is already a " + role + "!");
            } else {
                UserSystem.setRoleOf(index, role);
            }
        } else {
            System.out.println("Account does not exist!");
        }
    }
}
