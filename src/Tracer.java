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
        contacts.add(new ArrayList<>());
        estCodes = new ArrayList<>();
        estCodes.add(new ArrayList<>());
        for (Case i: UserSystem.getCases()) {
            // tracer is assigned to case and status is pending and case not yet in list of assigned cases
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
        if (assigned.size() == 0) { // no cases assigned
            System.out.println("No assigned cases");
        } else { // at least 1 assigned case
            Scanner input = new Scanner(System.in);
            int caseNum = -1;
            boolean status = false;

            // get case number
            try {
                System.out.print("Enter case number to be traced: ");
                caseNum = Integer.parseInt(input.nextLine());

                for (Case i: assigned) {
                    if (i.getCaseNum() == caseNum) { // case number is assigned
                        status = true;
                        break;
                    }
                }

                if (!status) // case number is not among assigned cases
                    throw new Exception();
            } catch (Exception e) {
                System.out.println("Invalid input. Use the show cases option to view your assigned cases.\n");
            }

            if (caseNum != -1) // valid case number input
                trace(caseNum);
        }
    }

    private void trace(int caseNum) {
        Case positive = null;
        for (Case i: assigned) {
            if (i.getCaseNum() == caseNum) { // case number is assigned
                positive = i;
                break;
            }
        }

        if (positive != null) { // valid input
            int posIndex = assigned.indexOf(positive);

            if (contacts.get(posIndex) == null) { // never traced before
                checkContacts(positive, posIndex);
            }
            displayContacts(contacts.get(posIndex));
        }
    }

    private void checkContacts(Case positive, int posIndex) {
        Citizen patient = UserSystem.getUser(positive.getUsername());
        if (patient != null) {
            ArrayList<Visit> records = new ArrayList<>(); // visit records of positive case on report date
            Calendar temp = Calendar.getInstance();
            temp.add(Calendar.DAY_OF_YEAR, -14); // get date 14 days before tracing
            for (Visit i: patient.getVisitRec()) { // set records
                if (i.getCheckIn().after(temp)) { // visit is within tracing range
                    records.add(i);
                }
            }

            ArrayList<ArrayList<Visit>> masterRecords = UserSystem.getRecords(); // all visit records
            Calendar startA, endA, startB, endB;
            for (int i = 0; i < masterRecords.size(); i++) { // iterate through each user
                for (int j = 0; j < masterRecords.get(i).size(); j++) { // iterate through each user record
                    // user record within tracing period
                    if (masterRecords.get(i).get(j).getCheckIn().after(temp)) {
                        // iterate through each positive case record
                        for (int k = 0; k < records.size(); k++) {
                            // same est_code
                            if (masterRecords.get(i).get(j).getEstCode().equals(records.get(k).getEstCode())) {
                                startA = masterRecords.get(i).get(j).getCheckIn();
                                if (j == masterRecords.get(i).size() - 1)
                                    endA = null;
                                else
                                    endA = masterRecords.get(i).get(j + 1).getCheckIn();
                                startB = records.get(k).getCheckIn();
                                if (k == records.size() - 1)
                                    endB = null;
                                else
                                    endB = records.get(k + 1).getCheckIn();

                                if (hasIntersection(startA, endA, startB, endB)) {
                                    contacts.get(posIndex).add(UserSystem.getUsername(i));
                                    estCodes.get(posIndex).add(records.get(k).getEstCode());
                                }
                            }
                        }
                    }
                }
            }

            if (contacts.get(posIndex).get(0) == null) // no contacts
                contacts.get(posIndex).add(""); // add null string to mark cone checking but no contacts
        }
    }

    private boolean hasIntersection(Calendar startA, Calendar endA, Calendar startB, Calendar endB) {
        int startOne, endOne, startTwo, endTwo;

        // convert time to military time
        startOne = startA.get(Calendar.HOUR_OF_DAY) * 100 + startA.get(Calendar.MINUTE);
        // check if same day
        if (endA == null || startA.get(Calendar.DAY_OF_YEAR) != endA.get(Calendar.DAY_OF_YEAR))
            endOne = 2359;
        else
            endOne = endA.get(Calendar.HOUR_OF_DAY) * 100 + endA.get(Calendar.MINUTE);
        startTwo = startB.get(Calendar.HOUR_OF_DAY) * 100 + startB.get(Calendar.MINUTE);
        // check if same day
        if (endB == null || startB.get(Calendar.DAY_OF_YEAR) != endB.get(Calendar.DAY_OF_YEAR))
            endTwo = 2359;
        else
            endTwo = endB.get(Calendar.HOUR_OF_DAY) * 100 + endB.get(Calendar.MINUTE);

        return (startOne >= startTwo && startOne <= endTwo) || (startTwo >= startOne && startTwo <= endOne);
    }

    private void displayContacts(ArrayList<String> contacts) {
        if (contacts.get(0).equals("")) { // no contacts
            System.out.println("Positive case did not come into contact with anyone.");
        } else {
            System.out.println("Persons at risk of possible exposure:");
            for (String i: contacts)
                System.out.println(i);
        }
    }

    private void broadcast() {

    }
}