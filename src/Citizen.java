import java.util.ArrayList;

/**
 * This class holds the information of a citizen and methods available for them.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 1.0
 * @see GovOfficial
 */
public class Citizen {
    private Name name;
    private String homeAddress;
    private String officeAddress;
    private String phoneNumber;
    private String email;
    private String username;
    private ArrayList<Visit> visitRec;
    private boolean isPositive;
    private boolean maybePositive;
    protected static String[] menuOptions = {"Check in", "Report positive", "Update profile information", "Logout"};

    /**
     *
     * @param name
     * @param homeAddress
     * @param officeAddress
     * @param phoneNumber
     * @param email
     * @param username
     */
    public Citizen(Name name, String homeAddress, String officeAddress, String phoneNumber,
                   String email, String username) {
        this.name = name;
        this.homeAddress = homeAddress;
        this.officeAddress = officeAddress;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        visitRec = new ArrayList<>();
        isPositive = false;
        maybePositive = false;
    }

    protected String getUsername() {
        return username;
    }

    public void showMenu() {
        int opt;

        do {
            prompt();
            opt = Menu.display("User", menuOptions);
            chooseMenu(opt);
        } while (opt != 4);

        logOut();
    }

    protected void chooseMenu(int opt) {
        switch (opt) {
            case 1 -> checkIn();
            case 2 -> reportPositive();
            case 3 -> updateInfo();
        }
    }

    protected void prompt() {
        if (!isPositive && maybePositive) {
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

    protected void logOut() {

    }
}