package model;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * This Citizen class holds the information of a citizen and methods
 * available for handling them such as retrieving contact information and
 * visit records for tracing.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 1.0
 * @see GovOfficial
 * @see Tracer
 */
public class Citizen {
    /** The name contained in a Name object. */
    private final Name name;
    /** The home address. */
    private String homeAddress;
    /** The office address. */
    private String officeAddress;
    /** The phone number. */
    private String phoneNumber;
    /** The email address. */
    private String email;
    /** The permanent username in the system. */
    private final String USERNAME;
    /** The password of the user. */
    private String password;
    /** The list of visit records. */
    private ArrayList<Visit> visitRec;
    /** Indicator if the user is infected. */
    private boolean isPositive;
    /** Indicator if the information of the user is changed. */
    private boolean isChanged;
    /** The latest record when and where the user may be infected. */
    private Visit contactPlace;

    /**
     * Receives the personal information of the user, along with the username and password
     * and initializes the object from them. Used when a user registers.
     * @param name the Name object containing the name of the user.
     * @param homeAddress the home address of the user.
     * @param officeAddress the office address of the user.
     * @param phoneNumber the phone number of the user.
     * @param email the email address of the user.
     * @param username the username of the user.
     * @param password the password of the user.
     * @param isChanged the indicator if the information of the user is changed.
     */
    public Citizen(Name name, String homeAddress, String officeAddress, String phoneNumber,
                   String email, String username, String password, boolean isChanged) {
        this.name = name;
        this.homeAddress = homeAddress;
        this.officeAddress = officeAddress;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.USERNAME = username;
        this.password = password;
        this.isChanged = isChanged;
        visitRec = new ArrayList<>();
        isPositive = false;
        contactPlace = null;
    }

    /**
     * Creates a copy from an object of the same class. Used when user logs in.
     * @param other the object to be copied.
     */
    public Citizen(Citizen other) {
        this(other.name, other.homeAddress, other.officeAddress, other.phoneNumber, other.email,
                other.USERNAME, other.password, other.isChanged);
        this.visitRec = other.visitRec;
        this.isPositive = other.isPositive;
        this.contactPlace = other.contactPlace;
    }

    /**
     * Returns the Name object containing the name of the user.
     * @return the Name object of the user.
     */
    public Name getName() {
        return name;
    }

    /**
     * Returns the home address of the user.
     * @return the home address of the user.
     */
    public String getHomeAddress() {
        return homeAddress;
    }

    /**
     * Returns the office address of the user.
     * @return the office address of the user.
     */
    public String getOfficeAddress() {
        return officeAddress;
    }

    /**
     * Returns the phone number of the user.
     * @return the phone number of the user.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns the email of the user.
     * @return the email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the username of the user.
     * @return the username of the user.
     */
    public String getUsername() {
        return USERNAME;
    }

    /**
     * Returns the password of the user.
     * @return the password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the records of visits of the user.
     * @return the records of the user.
     */
    public ArrayList<Visit> getVisitRec() {
        return UserSystem.getRecords().get(UserSystem.getIndexOf(USERNAME));
    }

    /**
     * Returns the status whether the information of the user is changed.
     * @return the status whether the information of the user is changed.
     */
    public boolean getIsChanged() {
        return isChanged;
    }

    /**
     * Returns the status whether the user is reported positive or not.
     * @return the status whether the user is reported positive or not.
     */
    public boolean getIsPositive() {
        return isPositive;
    }

    /**
     * Returns the latest record when and where the user may be infected.
     * @return the latest record when and where the user may be infected.
     */
    public Visit getContactPlace() {
        return contactPlace;
    }

    /**
     * Sets the status whether the information of the user is changed.
     * @param status the status whether the information of the user is changed.
     */
    public void setIsChanged(boolean status) {
        isChanged = status;
    }

    /**
     * Sets the personal details fields of the object (indicated by opt) with updated information.
     * @param opt indicates which field to replace.
     * @param info the new String to replace the current personal details.
     * @return the status whether the String input is valid and accepted.
     */
    public boolean setPersonalDetails(int opt, String info) {
        info = info.trim();

        if (!info.isBlank()) {
            switch (opt) {
                case 1 -> homeAddress = info;
                case 2 -> officeAddress = info;
                case 3 -> {
                    if (info.matches("\\d+")) {
                        phoneNumber = info;
                    } else {
                        return false;
                    }
                }
                case 4 -> email = info;
            }
            return true;
        }

        return false;
    }

    /**
     * Sets the password of the user if the given password is valid. Returns
     * whether the password of the user is accepted.
     * @param password the new password to replace the old one.
     * @return whether the password of the user is accepted.
     */
    public boolean setPassword(String password){
        if (UserSystem.isValidPassword(password)) {
            this.password = password;
            isChanged = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds a new visit record to the list of visit records based on the given
     * visitation information such as the establishment code and date.
     * @param estCode the establishment code of the visit record.
     * @param date the date when the visit was made.
     */
    public void checkIn(String estCode, Calendar date) {
        Visit temp = new Visit(estCode, date);
        visitRec.add(temp);
        UserSystem.addRecord(estCode, date, USERNAME);
    }

    /**
     * Changes the isPositive field to true and automatically adds
     * this record to the list of cases in the system only if the user
     * has not reported positive before.
     * @param date the date when the user reported positive.
     */
    public void reportPositive(Calendar date) {
        isPositive = true;
        UserSystem.addCase(this.USERNAME, date);
    }

    /**
     * Compares the current record when and where the user may be infected with the given record.
     * If there is no previous record (current record is null) or the current record has an older
     * date than the given record, the current is replaced with the given record. Otherwise, the
     * current record is kept.
     * @param contactPlace the record that indicates when and where the user may be infected.
     */
    public void addContactInfo(Visit contactPlace) {
        if (this.contactPlace == null || this.contactPlace.getCheckIn().before(contactPlace.getCheckIn()))
            this.contactPlace = contactPlace;
    }

    @Override
    public String toString() {
        return password + '\n' + name.toString() +
                "\nHOME:" + homeAddress +
                "\nOFFICE:" + officeAddress +
                "\nPHONE:" + phoneNumber +
                "\nEMAIL:" + email + "\n";
    }
}