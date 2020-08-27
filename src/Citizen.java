import java.text.SimpleDateFormat;
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
    /** Records that indicate when and where the user may be infected. */
    private ArrayList<Visit> contactPlaces;
    /** The Menu class for the update details options of the user. */
    public static final Menu UPDATE_OPTIONS = new Menu("Update", "Name", "Home Address",
            "Office Address", "Phone Number", "E-Mail", "Password", "Back to User Menu");
    /** The Menu class for the menu options of the user. */
    protected static Menu userMenu = new Menu("User", "Check in", "Report positive",
            "Update profile information", "Logout");

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
     */
    public Citizen(Name name, String homeAddress, String officeAddress, String phoneNumber,
                   String email, String username, String password) {
        this.name = name;
        this.homeAddress = homeAddress;
        this.officeAddress = officeAddress;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.USERNAME = username;
        this.password = password;
        visitRec = new ArrayList<>();
        isPositive = false;
        contactPlaces = new ArrayList<>();
    }

    /**
     * Creates a copy from an object of the same class. Used when user logs in.
     * @param other the object to be copied.
     */
    public Citizen(Citizen other) {
        this(other.name, other.homeAddress, other.officeAddress, other.phoneNumber, other.email,
                other.USERNAME, other.password);
        this.visitRec = other.visitRec;
        this.isPositive = other.isPositive;
        this.contactPlaces = other.contactPlaces;
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
     * Returns the username of the user.
     * @return the username of the user.
     */
    protected String getUsername() {
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
        return visitRec;
    }

    /**
     * Returns the Menu object for the Citizen class.
     * @return the Menu object for the Citizen class.
     */
    public Menu getUserMenu() {
        return userMenu;
    }

    /**
     * Returns the status whether the user is reported positive or not.
     * @return the status whether the user is reported positive or not.
     */
    public boolean getIsPositive() {
        return isPositive;
    }

    public void setPersonalDetails(int opt, String info) throws Exception {
        info = info.trim();

        if (!info.isBlank()) {
            switch (opt) {
                case 2 -> homeAddress = info;
                case 3 -> officeAddress = info;
                case 4 -> {
                    if (info.matches("\\d+")) {
                        phoneNumber = info;
                    } else {
                        throw new Exception("Phone number must only contain digits!");
                    }
                }
                case 5 -> email = info;
            }
        } else {
            throw new Exception(UPDATE_OPTIONS.getOption(opt - 1) + " cannot be left blank!");
        }
    }

    public void setPassword(String password) throws Exception {
        if (UserSystem.isValidPassword(password)) {
            this.password = password;
        } else {
            throw new Exception("Password must contain at least 6 characters including 1 digit or special " +
                    "character that is not a space!\n");
        }
    }

    /**
     * Adds a new visit records to the list of visit records based on the given
     * visitation information such as the establishment code and date.
     * @param estCode the establishment code of the visit record.
     * @param date the date when the visit was made.
     */
    public void checkIn(String estCode, Calendar date) {
        // get machine time
        Calendar time = Calendar.getInstance();
        date.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
        date.set(Calendar.MINUTE, time.get(Calendar.MINUTE));

        Visit temp = new Visit(estCode, date);
        visitRec.add(temp);
        UserSystem.addRecord(temp, USERNAME);
    }

    /**
     * Changes the isPositive field to true and automatically adds
     * this record to the list of cases in the system only if the user
     * has not reported positive before.
     */
    public void reportPositive(Calendar date) {
        isPositive = true;
        UserSystem.addCase(this.USERNAME, date);
        System.out.println("Case reported. Thank you.\n");
    }

    /**
     * Adds where and when a user may be infected.
     * @param contactPlace the record that indicates when and where the user may be infected.
     */
    public void addContactInfo(Visit contactPlace) {
        int i;
        for (i = 0; i < contactPlaces.size(); i++) {
            if (contactPlaces.get(i).getCheckIn().before(contactPlace.getCheckIn()))
                break;
        }

        if (i == contactPlaces.size()) {
            contactPlaces.add(contactPlace);
        } else {
            contactPlaces.add(i, contactPlace);
        }
    }

    /**
     * Displays a message if the user has possibly came in contact
     * with a positive case.
     */
    public void prompt() {
        if (!isPositive) {
            SimpleDateFormat format = new SimpleDateFormat("MM,dd,yyyy");
            Calendar temp = Calendar.getInstance();
            temp.add(Calendar.DAY_OF_YEAR, -14);
            for (int i = 0; i < contactPlaces.size(); i++) {
                if (contactPlaces.get(i).getCheckIn().before(temp)) { // not within prompting date range
                    contactPlaces.remove(i);
                }
            }

            if (contactPlaces.size() != 0) { // there are still possible contact times after removing
                System.out.println("You may have been in contact with a positive patient on: ");
                for (Visit contactPlace : contactPlaces)
                    System.out.println(format.format(contactPlace.getCheckIn()) + " in " +
                            contactPlace.getEstCode());
                System.out.println("You are advised to get tested and report via the app should the result be positive.\n");
            }
        }
    }
}