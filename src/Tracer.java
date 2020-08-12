import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is used
 *
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 0.1
 * @see Citizen
 */
public class Tracer extends Citizen {
    protected static String[] menuOptions = {"Check in", "Report positive", "Update profile information",
            "Show Cases", "Trace Specific Case", "Inform Citizens Possibly Exposed", "Logout"};
    private ArrayList<Case> assigned;
    private ArrayList<ArrayList<String>> contacts;

    /**
     * Receives a Citizen class and makes an exact copy of its attributes.
     * @param citizen the object used to construct the new object.
     */
    public Tracer(Citizen citizen) {
        super(citizen);

        assigned = new ArrayList<>();
        contacts = new ArrayList<>();
        for (Case i: UserSystem.getCases()) {
            //tracer is assigned to case and status is pending and case not yet in list of assigned cases
            if (i.getTracer().equals(getUsername()) && i.getStatus() == 'P' && !assigned.contains(i))
                assigned.add(i);
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
        } while (opt != 7);

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
                case 4 -> showCases();
                case 5 -> trace();
                case 6 -> broadcast();
            }
        }
    }

    /**
     * Displays the case numbers of the assigned cases to the contact tracer.
     */
    private void showCases() {
        System.out.println("Cases Assigned:");
        for (Case i: assigned) {
            System.out.println(i.getCaseNum());
        }
    }

    private void trace() {

    }

    private void broadcast() {

    }
}