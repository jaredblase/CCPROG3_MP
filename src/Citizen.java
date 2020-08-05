import java.util.ArrayList;

/**
 * This class holds the information of a citizen and methods available for them.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 1.0
 * @see GovOfficial
 */
public class Citizen {
    protected Name name;
    protected String homeAddress;
    protected String officeAddress;
    protected String phoneNumber;
    protected String email;
    protected ArrayList<Visit> visitRec;
    protected static String[] menuOptions = {"Check in", "Report positive", "Update profile information", "Log out"};
    private boolean isPositive;
    private boolean maybePositive;

    public Citizen(Name name, String homeAddress, String officeAddress, String phoneNumber, String email) {
        this.name = name;
        this.homeAddress = homeAddress;
        this.officeAddress = officeAddress;
        this.phoneNumber = phoneNumber;
        this.email = email;
        visitRec = new ArrayList<>();
        isPositive = false;
        maybePositive = false;
    }

    public void showMenu() {
        int opt;

        do {
            prompt();
            opt = Menu.display("User", menuOptions);
            switch (opt) {
                case 1 -> checkIn();
                case 2 -> reportPositive();
                case 3 -> updateInfo();
            }
        } while (opt != 4);

        logOut();
    }

    private void prompt() {
        if(!isPositive && maybePositive) {
            System.out.println("You might be positive!");
        }
    }

    private void checkIn() {

    }

    private void reportPositive() {
        isPositive = true;
    }

    private void updateInfo() {
        Menu.display("Update Information Menu", "Name");
    }

    private void logOut() {

    }
}