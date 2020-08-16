import java.util.ArrayList;
import java.util.Calendar;
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
    private ArrayList<ArrayList<String>> estCodes;

    /**
     * Receives a Citizen class and makes an exact copy of its attributes.
     * @param citizen the object used to construct the new object.
     */
    public Tracer(Citizen citizen) {
        super(citizen);

        assigned = new ArrayList<>();
        contacts = new ArrayList<>();
        estCodes = new ArrayList<>();
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
        if (assigned.size() == 0) { //no cases assigned
            System.out.println("No assigned cases");
        } else { //at least 1 assigned case
            Scanner input = new Scanner(System.in);
            int caseNum;
            Case positive = null;

            do {
                //get case number
                try {
                    System.out.print("Enter case number to be traced: ");
                    caseNum = Integer.parseInt(input.nextLine());

                    for (Case i: assigned) {
                        if (i.getCaseNum() == caseNum) { //case number is assigned
                            positive = i;
                            break;
                        }
                    }

                    if (positive == null) //case number is not among assigned cases
                        throw new Exception();
                } catch (Exception e) {
                    System.out.println("Invalid input. Use the show cases option to view your assigned cases.\n");
                }
            } while (positive == null);

            if (contacts.get(assigned.indexOf(positive)) == null) { //never traced before
                Citizen patient = UserSystem.getUser(positive.getUsername());
                if (patient != null) {
                    ArrayList<Visit> records = new ArrayList<>();
                    for (Visit i: patient.getVisitRec()) {
                        if (i.getCheckIn().get(Calendar.DAY_OF_YEAR) ==
                                positive.getReportDate().get(Calendar.DAY_OF_YEAR) && //same day of year
                                i.getCheckIn().get(Calendar.YEAR) ==
                                        positive.getReportDate().get(Calendar.YEAR)) // same year
                            records.add(i);
                    }
                    //check each time frame from records
                    //iterate through all records of all users
                    //if check in has same date, check if estCode is same and if there is intersection in time
                    //if same estCode and there is intersection, add username and estCode to array lists
                    //after the whole process if index in array lists are empty,
                    //set them to contain the String "none"
                }
            }
        }
    }

    private void broadcast() {

    }
}