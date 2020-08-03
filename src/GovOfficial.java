/**
 * This class is used
 *
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 0.1
 * @see Citizen
 */
public class GovOfficial extends Citizen {

    private GovOfficial(Citizen citizen) {
        super(citizen);
    }

    @Override
    public void showMenu() {
        int opt;

        do {
            opt = Menu.display("User", "Show Unassigned Cases", "Show Contact Tracing Updates", "Analytics",
                    "Create Government Official Account", "Create Contact Tracer Account", "Terminate Account");
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
