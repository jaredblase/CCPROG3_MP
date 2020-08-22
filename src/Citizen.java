import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

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
    /** The name contained in a Name object */
    private final Name name;
    /** The home address */
    private String homeAddress;
    /** The office address */
    private String officeAddress;
    /** The phone number */
    private String phoneNumber;
    /** The email address */
    private String email;
    /** The permanent username in the system */
    private final String USERNAME;
    /** The password of the user */
    private String password;
    /** The list of visit records */
    private ArrayList<Visit> visitRec;
    /** Indicator if the user is infected */
    private boolean isPositive;
    /** Indicator if the user may be infected */
    private boolean maybePositive;
    /** Indicator if any detail was changed during the session */
    private boolean isChanged;
    /** The String array containing the update details options of the user */
    private static final String[] UPDATE_OPTIONS = {"Name", "Home Address", "Office Address", "Phone Number",
            "E-Mail", "Password", "Back to User Menu"};
    /** The String array containing the menu options of the user */
    protected static String[] menuOptions = {"Check in", "Report positive", "Update profile information", "Logout"};

    /**
     * Receives the personal information of the user, along with the username and password
     * and initializes the object from them.
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
        maybePositive = false;
        isChanged = false;
    }

    /**
     * Creates a copy from an object of the same class.
     * @param other the object to be copied.
     */
    public Citizen(Citizen other) {
        this.name = other.name;
        this.homeAddress = other.homeAddress;
        this.officeAddress = other.officeAddress;
        this.phoneNumber = other.phoneNumber;
        this.email = other.email;
        this.USERNAME = other.USERNAME;
        this.password = other.password;
        this.visitRec = other.visitRec;
        this.isPositive = other.isPositive;
        this.maybePositive = other.maybePositive;
        this.isChanged = other.isChanged;
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
     * Main entry point of the user after logging in.
     */
    public void showMenu() {
        int opt;

        do {
            prompt();
            opt = Menu.display("User", menuOptions);
            chooseMenu(opt);
        } while (opt != 4);

        logOut();
    }

    /**
     * Retrieves the visitation information from the user such as the establishment
     * code and date and adds this to his visit records.
     */
    private void checkIn() {
        Scanner input = new Scanner(System.in);

        System.out.print("Establishment Code: ");
        String estCode = input.nextLine();

        Calendar date = getDate();
        Calendar time = Calendar.getInstance();
        date.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
        date.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
        visitRec.add(new Visit(estCode, date));
    }

    /**
     * Changes the isPositive field to true and automatically adds
     * this record to the list of cases in the system only if the user
     * has not reported positive before.
     */
    private void reportPositive() {
        if (!isPositive) {
            isPositive = true;
            isChanged = true;
            UserSystem.addCase(new Case(this.USERNAME, getDate()));
        } else {
            System.out.println("You are already reported positive!");
        }
    }

    /**
     * The facility that handles the updating of personal information.
     */
    private void updateInfo() {
        int opt, max = UPDATE_OPTIONS.length;

        do {
            opt = Menu.display("Update Information", UPDATE_OPTIONS);
            if (opt != max) {
                if (opt == 1) {
                    isChanged = name.changeName();
                } else if (opt == max - 1) {
                    this.password = UserSystem.checkPassword();
                    isChanged = true;
                } else {
                    Scanner input = new Scanner(System.in);
                    System.out.print("New " + UPDATE_OPTIONS[opt - 1] + ": ");
                    String str = input.nextLine();

                    if (!str.isBlank()) {
                        switch (opt) {
                            case 2 -> this.homeAddress = str;
                            case 3 -> this.officeAddress = str;
                            case 4 -> this.phoneNumber = str;
                            case 5 -> this.email = str;
                        }
                        isChanged = true;
                    } else {
                        System.out.println("Invalid input!");
                    }
                }

                if (isChanged) {
                    System.out.println(UPDATE_OPTIONS[opt - 1] + " has been updated!\n");
                }
            }
        } while (opt != max);
    }

    /**
     * Calls the appropriate function based on the user's input.
     * @param opt integer representing the chosen menu option.
     */
    protected void chooseMenu(int opt) {
        switch (opt) {
            case 1 -> checkIn();
            case 2 -> reportPositive();
            case 3 -> updateInfo();
        }
    }

    /**
     * Display a message if the user has possibly came in contact
     * with
     */
    protected void prompt() {
        if (!isPositive && maybePositive) {
            System.out.println("You may have been in contact with a positive patient on <date> in <establishment>.");
            System.out.println("You are advised to get tested and report via the app should the result be positive.");
        }
    }

    /**
     * Retrieves a date input from the user and attempts to build a Calendar object
     * from it.
     * @return a valid date.
     */
    protected Calendar getDate() {
        Scanner input = new Scanner(System.in);
        Calendar date = null;
        Calendar.Builder builder = new Calendar.Builder();
        builder.setLenient(false);
        int year, month, day;

        do {
            try {
                System.out.println("-DATE-");
                System.out.print("Year: ");
                year = Integer.parseInt(input.nextLine());

                System.out.print("Month: ");
                month = Integer.parseInt(input.nextLine());

                System.out.print("Day: ");
                day = Integer.parseInt(input.nextLine());

                builder.setDate(year, month - 1, day);
                date = builder.build();
            } catch (Exception e) {
                System.out.println("Invalid date input!\n");
            }
        } while (date == null);

        return date;
    }

    /**
     * Replaces the object in the system if there modification done during the session.
     */
    protected void logOut() {
        if (isChanged) {
            UserSystem.updateUser(this);
            this.isChanged = false;
        }
    }
}