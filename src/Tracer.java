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
    private ArrayList<ArrayList<Calendar>> dates;
    private ArrayList<ArrayList<String>> estCodes;

    /**
     * Receives a Citizen class and makes an exact copy of its attributes.
     * @param citizen the object used to construct the new object.
     */
    public Tracer(Citizen citizen) {
        super(citizen);

        assigned = new ArrayList<>();
        contacts = new ArrayList<>();
        dates = new ArrayList<>();
        estCodes = new ArrayList<>();

        for (Case i: UserSystem.getCases()) {
            // tracer is assigned to case and status is pending and case not yet in list of assigned cases
            if (i.getTracer().equals(getUsername()) && i.getStatus() == 'P' && !assigned.contains(i)) {
                assigned.add(i);
                contacts.add(new ArrayList<>());
                dates.add(new ArrayList<>());
                estCodes.add(new ArrayList<>());
            }
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

            if (status) // valid case number input
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
            ArrayList<Visit> posRecords = new ArrayList<>(); // visit records of positive case on report date
            Calendar temp = (Calendar) positive.getReportDate().clone();
            temp.add(Calendar.DAY_OF_YEAR, -14); // get date 14 days before positive report

            for (Visit i: patient.getVisitRec()) { // set records
                if (i.getCheckIn().after(temp)) { // visit is within tracing range
                    posRecords.add(i);
                }
            }
            int posRecordLength = posRecords.size();

            ArrayList<ArrayList<Visit>> masterRecords = UserSystem.getRecords(); // all visit records
            Calendar startA, endA, startB, endB;

            for (int i = 0; i < masterRecords.size(); i++) {                // iterate through each user
                ArrayList<Visit> userRec = masterRecords.get(i);

                for (int j = 0; j < userRec.size(); j++) {                  // iterate through each user record
                    Visit userVisit = userRec.get(j);

                    if (userVisit.getCheckIn().after(temp)) {                   // user record within tracing period
                        for (int k = 0; k < posRecordLength; k++) {         // iterate through each positive case record
                            Visit posRec = posRecords.get(k);

                            if (userVisit.getEstCode().equals(posRec.getEstCode())) {   // same est_code
                                startA = userVisit.getCheckIn();
                                try {
                                    endA = userRec.get(j + 1).getCheckIn();
                                } catch (Exception e) {
                                    endA = null;
                                }

                                startB = posRec.getCheckIn();
                                try {
                                    endB = posRecords.get(k + 1).getCheckIn();
                                } catch (Exception e) {
                                    endB = null;
                                }

                                if (hasIntersection(startA, endA, startB, endB)) {
                                    contacts.get(posIndex).add(UserSystem.getUsername(i));
                                    dates.get(posIndex).add(posRec.getCheckIn());
                                    estCodes.get(posIndex).add(posRec.getEstCode());
                                }
                            }
                        }
                    }
                }
            }

            if (contacts.get(posIndex).isEmpty()) // no contacts
                contacts.get(posIndex).add(""); // add empty string to mark done checking but no contacts
        }
    }

    private boolean hasIntersection(Calendar startA, Calendar endA, Calendar startB, Calendar endB) {
        if (startA.get(Calendar.DAY_OF_YEAR) != startB.get(Calendar.DAY_OF_YEAR)) // not the same day
            return false;

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
        if (contacts.get(0).isEmpty()) { // no contacts
            System.out.println("Positive case did not come into contact with anyone.");
        } else {
            System.out.println("Persons at risk of possible exposure:");
            for (String i: contacts)
                System.out.println(i);
        }
    }

    private void broadcast() {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i) == null)
                continue;
            if (!contacts.get(i).get(0).isEmpty()) { // there are people possibly exposed
                for (int j = 0; j < contacts.get(i).size(); j++) {
                    Citizen user = UserSystem.getUser(contacts.get(i).get(j));
                    if (user != null) {
                        // add info to prompt user that he may be infected
                        user.addContactInfo(dates.get(i).get(j), estCodes.get(i).get(j));
                    }
                }
            }

            // tracing is complete
            Case remove = assigned.remove(i);
            remove.setStatus('T');
            contacts.remove(i);
            dates.remove(i);
            estCodes.remove(i);
        }
    }
}