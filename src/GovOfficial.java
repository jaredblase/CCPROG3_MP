import java.util.Calendar;
import java.util.Scanner;

/**
 * This class is used
 *
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 0.1
 * @see Citizen
 */
public class GovOfficial extends Citizen {
    private static final String[] analyticsMenu = {"Number of positive cases in a city within a duration",
            "Number of positive cases within a duration", "Number of positive cases in a city", "Back"};
//    /**
//     *
//     * @param name the Name object containing the name of the user
//     * @param homeAddress the home address of the user
//     * @param officeAddress the office address of the user
//     * @param phoneNumber the phone number of the user
//     * @param email the email address of the user
//     * @param username the username of the user
//     */
//    public GovOfficial(Name name, String homeAddress, String officeAddress, String phoneNumber,
//                        String email, String username, String password) {
//        super(name, homeAddress, officeAddress, phoneNumber, email, username, password);
//        String[] temp = new String[menuOptions.length + 6];
//        System.arraycopy(menuOptions, 0, temp, 0, menuOptions.length);
//        temp[3] = "Show Unassigned Cases";
//        temp[4] = "Show Contact Tracing Updates";
//        temp[5] = "Analytics";
//        temp[6] = "Create Government Official Account";
//        temp[7] = "Create Contact Tracer Account";
//        temp[8] = "Terminate Account";
//        temp[9] = "Logout";
//        menuOptions = temp;
//    }

    public <E extends Citizen> GovOfficial(E citizen) {
        super(citizen);
        if (menuOptions.length <= 4) {
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

    private void showUnassigned() {
//        for (Case i: ) {
//            if(i.getTracer().equals("000"))
//                System.out.println(i);
//        }
    }

    private void showUpdates() {

    }

    private void showAnalytics() {
        int opt;

        do {
            opt = Menu.display("Analytics", analyticsMenu);

            switch (opt) {
                case 1 -> {
                    Scanner input = new Scanner(System.in);
                    Calendar[] dates = obtainDateRange();
                    System.out.print("City: ");
                    String city = input.nextLine();
                }
                case 2 -> {
                    Calendar[] dates = obtainDateRange();
                }
                case 3 -> {
                    Scanner input = new Scanner(System.in);
                    String city = input.nextLine();
                }
            }
        } while (opt != analyticsMenu.length);
    }

    private Calendar[] obtainDateRange() {
        Calendar start, end;

        System.out.println("Starting date:");
        start = getDate();
        do {
            System.out.println("End date");
            end = getDate();
        } while (start.after(end));

        return new Calendar[] {start, end};
    }

    /**
     * Handles the promotion and demotion of an account.
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
