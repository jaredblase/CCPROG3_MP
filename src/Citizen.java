import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class holds the information of a citizen and methods available for them.
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
    /** The list of visit records */
    private ArrayList<Visit> visitRec;
    private boolean isPositive;
    private boolean maybePositive;
    private boolean isChanged;
    private static final String[] UPDATE_OPTIONS = {"Name", "Home Address", "Office Address", "Phone Number",
            "E-Mail", "Password", "Back to User Menu"};
    protected static String[] menuOptions = {"Check in", "Report positive", "Update profile information", "Logout"};

    /**
     *
     * @param name the Name object containing the name of the user
     * @param homeAddress the home address of the user
     * @param officeAddress the office address of the user
     * @param phoneNumber the phone number of the user
     * @param email the email address of the user
     * @param username the username of the user
     */
    public Citizen(Name name, String homeAddress, String officeAddress, String phoneNumber,
                   String email, String username) {
        this.name = name;
        this.homeAddress = homeAddress;
        this.officeAddress = officeAddress;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.USERNAME = username;
        visitRec = new ArrayList<>();
        isPositive = false;
        maybePositive = false;
        isChanged = false;
    }

    protected String getUsername() {
        return USERNAME;
    }

    public void showMenu() {
        int opt;

        do {
            prompt();
            opt = Menu.display("User", menuOptions);
            chooseMenu(opt);
        } while (opt != 4);

        if(isChanged) {
            logOut();
        }
    }

    protected void chooseMenu(int opt) {
        switch (opt) {
            case 1 -> checkIn();
            case 2 -> reportPositive();
            case 3 -> updateInfo();
        }
    }

    protected void prompt() {
        if (!isPositive && maybePositive) {
            System.out.println("You might be positive!");
        }
    }

    private void checkIn() {

    }

    private void reportPositive() {
        isPositive = true;

        String temp = MyDate.getDate().toString();
    }

    private void updateInfo() {
        int opt, max = UPDATE_OPTIONS.length;

        do {
            opt = Menu.display("Update Information", UPDATE_OPTIONS);
            if (opt != max) {
                if (opt == 1) {
                    name.changeName();
                } else if (opt == max - 1) {
                    User.setPassword(this.USERNAME);
                } else {
                    Scanner input = new Scanner(System.in);
                    System.out.print("New " + UPDATE_OPTIONS[opt - 1] + ": ");
                    String str = input.nextLine();

                    switch (opt) {
                        case 2 -> this.homeAddress = str;
                        case 3 -> this.officeAddress = str;
                        case 4 -> this.phoneNumber = str;
                        case 5 -> this.email = str;
                    }
                }
                System.out.println(UPDATE_OPTIONS[opt - 1] + " has been updated!\n");
                isChanged = true;
            }
        } while (opt != max);
    }

    /**
     * Updates the text file with changes made while the user was logged in
     */
    protected void logOut() {
        String pass = null;

        try (Scanner input = new Scanner(new File(USERNAME + ".act"))) {
            pass = input.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(USERNAME + ".act", false)) {
            writer.write(pass + "\n");
            writer.write(name.toString() + "\n");
            writer.write("HOME:" + homeAddress + "\n");
            writer.write("OFFICE:" + officeAddress + "\n");
            writer.write("PHONE:" + phoneNumber + "\n");
            writer.write("EMAIL:" + email + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}