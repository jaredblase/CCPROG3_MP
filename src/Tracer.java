import java.util.ArrayList;

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

    /**
     * Receives a Citizen class and makes an exact copy of its attributes.
     * @param citizen the object used to construct the new object.
     */
    public Tracer(Citizen citizen) {
        super(citizen);

        assigned = new ArrayList<>();
        for (Case i: UserSystem.getCases()) {
            if (i.getTracer().equals(getUsername()) && i.getStatus() == 'P' && !assigned.contains(i))
                assigned.add(i);
        }
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