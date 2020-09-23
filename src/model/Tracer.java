package model;

import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * This Tracer class extends the Citizen class and includes contact tracer
 * facilities such as viewing assigned cases, tracing contacts of positive cases,
 * and informing contacts of their possible exposure.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 2.0
 * @see Citizen
 */
public class Tracer extends Citizen {
    /** The list of cases assigned to the contact tracer. */
    private ArrayList<Case> assigned;

    /**
     * Receives a Citizen class object and makes an exact copy of its attributes, and
     * initializes the Tracer attributes. Checks for any assigned cases to the user and
     * adds it to its attributes.
     * @param other the object used to construct the new object.
     */
    public Tracer(Citizen other) {
        super(other);

        assigned = new ArrayList<>();
        for (Case i: UserSystem.getCases())
            if (i.getTracer().equals(getUsername()) && i.getStatus() != 'T')
                assigned.add(i);
    }

    /**
     * Adds a case to the list of assigned cases of the contact tracer.
     * @param positive the case to be added.
     */
    public void addCase(Case positive) {
        assigned.add(positive);
    }

    /**
     * Returns the list of case numbers of the cases assigned to the contact tracer.
     * @return the list of case numbers of the cases assigned to the contact tracer.
     */
    public ArrayList<Integer> getAssigned() {
        ArrayList<Integer> caseNums = new ArrayList<>();
        for (Case i: assigned)
            caseNums.add(i.getCaseNum());

        return caseNums;
    }

    /**
     * Traces the contacts of a case given the case number.
     * @param caseNum the case number of a case.
     */
    public void trace(int caseNum) {
        Case positive = null;
        for (Case i: assigned) {
            if (i.getCaseNum() == caseNum) { // case number is assigned
                positive = i;
                break;
            }
        }
        ArrayList<String> contacts = new ArrayList<>();
        ArrayList<Visit> contactPlaces = new ArrayList<>();

        if (positive != null) { // valid input
            checkContacts(positive, contacts, contactPlaces);

            for (int i = 0; i < contacts.size(); i++) {
                // add info to prompt user that he may be infected
                Citizen user = UserSystem.getUser(contacts.get(i));
                if (user != null)
                    user.addContactInfo(contactPlaces.get(i));
            }

            positive.setStatus('T');
            assigned.remove(positive);
        }
    }

    /**
     * Traces the contacts of a case given the case number.
     * @param caseNum the case number of a case.
     * @param list the list of contacts and contact places of a case.
     */
    public void trace(int caseNum, ObservableList<Pair<String, Visit>> list) {
        Case positive = null;
        for (Case i: assigned) {
            if (i.getCaseNum() == caseNum) { // case number is assigned
                positive = i;
                break;
            }
        }
        ArrayList<String> contacts = new ArrayList<>();
        ArrayList<Visit> contactPlaces = new ArrayList<>();

        if (positive != null) { // valid input
            checkContacts(positive, contacts, contactPlaces);

            for (int i = 0; i < contacts.size(); i++) {
                // add info to prompt user that he may be infected
                Citizen user = UserSystem.getUser(contacts.get(i));
                if (user != null)
                    user.addContactInfo(contactPlaces.get(i));

                System.out.println(contacts.get(i));
                System.out.println(contactPlaces.get(i).toString());
                list.add(new Pair<>(contacts.get(i), contactPlaces.get(i)));
            }

            // finished tracing
            positive.setStatus('T');
            assigned.remove(positive);
        }
    }

    /**
     * Searches through the visit records of all users to determine the
     * contacts of a given case and updates the list of contacts and list of
     * when and where the contacts were exposed.
     * @param positive the positive case.
     * @param contacts the list of contacts of the positive case.
     * @param contactPlaces the list of visit records when and where the contacts
     *                      may have been exposed.
     */
    private void checkContacts(Case positive, ArrayList<String> contacts, ArrayList<Visit> contactPlaces) {
        Citizen patient = UserSystem.getUser(positive.getUsername());
        if (patient != null) {
            ArrayList<Visit> posRecords = new ArrayList<>(); // visit records of positive case on report date
            Calendar temp = (Calendar) positive.getReportDate().clone();
            temp.add(Calendar.DAY_OF_YEAR, -8); // get date 8 days before positive report

            for (Visit i: patient.getVisitRec()) { // set records
                if (i.getCheckIn().compareTo(temp) >= 0) { // visit is within tracing range
                    posRecords.add(i);
                }
            }
            int posRecordLength = posRecords.size();

            ArrayList<ArrayList<Visit>> masterRecords = UserSystem.getRecords(); // all visit records
            Calendar startA, endA, startB, endB;

            for (int i = 0; i < masterRecords.size(); i++) {                // iterate through each user
                if (i != UserSystem.getIndexOf(positive.getUsername())) { // records to be checked not that of positive case
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
                                        contacts.add(UserSystem.getUsername(i));
                                        contactPlaces.add(userVisit);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Determines if there is an intersection between two time intervals of the same
     * day. Both end times are assumed to be after the start times. If the end time
     * and the start time are not on the same day or the end time is null, the end time
     * is by default set to 11:59PM of the same day as the start time. Returns true
     * if there is an intersection and false otherwise.
     * @param startA the starting time of the first interval.
     * @param endA the ending time of the first interval.
     * @param startB the starting time of the second interval.
     * @param endB the ending time of the second interval.
     * @return true if there is an intersection and false otherwise.
     */
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

    /**
     * Called when a contact tracer is demoted to a citizen. Sets the list of assigned
     * cases to null.
     */
    public void demote() {
        assigned = null;
        System.gc();
    }
}