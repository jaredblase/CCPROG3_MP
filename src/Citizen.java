import java.util.ArrayList;

/**
 * This class holds the information of a citizen and methods available for them.
 * @author Pua, Gabriel
 * @author Sy, Jared
 * @version 1.0
 */
public class Citizen {
    protected Name name;
    protected String homeAddress;
    protected String officeAddress;
    protected String email;
    protected ArrayList<Visit> visitRec;
    private boolean isPositive;
    private boolean maybePositive;

    public Citizen(Name name, String homeAddress, String officeAddress, String email) {
        this.name = name;
        this.homeAddress = homeAddress;
        this.officeAddress = officeAddress;
        this.email = email;
        visitRec = new ArrayList<>();
        isPositive = false;
        maybePositive = false;
    }

     protected <E extends Citizen> Citizen(E citizen) {
        this(citizen.name, citizen.homeAddress, citizen.officeAddress, citizen.email);
     }

    public void showMenu() {
        prompt();
        Menu.display("Main Menu", "Check in", "Report positive", "Update profile information", "Log out");
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