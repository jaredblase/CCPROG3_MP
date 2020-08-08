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
    /**
     *
     * @param name the Name of object containing the name of the user
     * @param homeAddress the home address of the user
     * @param officeAddress the office address of the user
     * @param phoneNumber the phone number of the user
     * @param email the email address of the user
     * @param username the username of the user
     */
    public GovOfficial(Name name, String homeAddress, String officeAddress, String phoneNumber,
                        String email, String username, String password) {
        super(name, homeAddress, officeAddress, phoneNumber, email, username, password);
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

    public <E extends Citizen> GovOfficial(E citizen) {
        super(citizen);
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

    }

    private void showUpdates() {

    }

    private void showAnalytics() {

    }

    /**
     * Handles the promotion and demotion of an account.
     * @param role the new role to be assigned to an account.
     */
    private void modifyRole(String role) {
        Scanner input = new Scanner(System.in);
        System.out.print("Account username to be modified: ");
        int index = User.getIndexOf(input.nextLine());

        if (index != -1) {
            if (User.getRoleOf(index).equals(role)) {
                User.setRoleOf(index, role);
            } else {
                System.out.println("Account is already a " + role + "!");
            }
        } else {
            System.out.println("Account does not exist!");
        }
    }
}
