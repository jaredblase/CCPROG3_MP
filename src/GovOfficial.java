/**
 * This class is used
 *
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 0.1
 * @see Citizen
 */
public class GovOfficial extends Citizen {

    private GovOfficial(Name name, String homeAddress, String officeAddress, String phoneNumber, String email) {
        super(name, homeAddress, officeAddress, phoneNumber, email);
        String[] temp = new String[menuOptions.length + 6];
        System.arraycopy(menuOptions, 0, temp, 0, menuOptions.length);
        temp[3] = "Show Unassigned Cases";
        temp[4] = "Show Contact Tracing Updates";
        temp[5] = "Analytics";
        temp[6] = "Create Government Official Account";
        temp[7] = "Create Contact Tracer Account";
        temp[8] = "Terminate Account";
        menuOptions = temp;
    }

    @Override
    public void showMenu() {
        int opt;

        do {
            opt = Menu.display("User", menuOptions);
            switch (opt) {
                case 1 -> showUnassigned();
                case 2 -> showUpdates();
                case 3 -> showAnalytics();
                case 4 -> createGov();
                case 5 -> createTrace();
                case 6 -> terminateAcc();
            }
        } while (opt != 7);
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
