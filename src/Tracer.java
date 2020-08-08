/**
 * This class is used
 *
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 0.1
 * @see Citizen
 */
public class Tracer extends Citizen {
    public Tracer(Name name, String homeAddress, String officeAddress, String phoneNumber,
                       String email, String username) {
        super(name, homeAddress, officeAddress, phoneNumber, email, username);
        String[] temp = new String[menuOptions.length + 6];
        System.arraycopy(menuOptions, 0, temp, 0, menuOptions.length);
        temp[3] = "Show Cases";
        temp[4] = "Trace Specific Case";
        temp[5] = "Inform Citizens Possibly Exposed";
        temp[6] = "Logout";
        menuOptions = temp;
    }

    @Override
    public void showMenu() {
        int opt;

        do {
            super.prompt();
            opt = Menu.display("User", menuOptions);
            chooseMenu(opt);
        } while (opt != 7);

        super.logOut();
    }

    @Override
    protected void chooseMenu(int opt) {
        if (opt < 4) {
            super.chooseMenu(opt);
        } else {
            switch (opt) {
                case 4 -> showCases();
                case 5 -> trace();
                case 6 -> broadcast();
            }
        }
    }

    private void showCases() {

    }

    private void trace() {

    }

    private void broadcast() {

    }
}