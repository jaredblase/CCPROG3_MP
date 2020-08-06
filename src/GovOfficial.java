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
     * @param name
     * @param homeAddress
     * @param officeAddress
     * @param phoneNumber
     * @param email
     * @param username
     */
    public GovOfficial(Name name, String homeAddress, String officeAddress, String phoneNumber,
                        String email, String username) {
        super(name, homeAddress, officeAddress, phoneNumber, email, username);
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

    @Override
    public void showMenu() {
        int opt;

        do {
            super.prompt();
            opt = Menu.display("User", menuOptions);
            chooseMenu(opt);
        } while (opt != 10);

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
                case 7 -> createGov();
                case 8 -> createTrace();
                case 9 -> terminateAcc();
            }
        }
    }

    private void showUnassigned() {

    }

    private void showUpdates() {

    }

    private void showAnalytics() {

    }

    private void createGov() {
        //get user name
    }

    private void createTrace() {

    }

    private void terminateAcc() {

    }
}
