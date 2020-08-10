//import java.io.File;
//import java.io.FileWriter;

import java.util.ArrayList;
import java.util.Calendar;
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
    /** The password of the user */
    private String password;
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
     * @param password the password of the user
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
     * Creates a copy from an object of the same class
     * @param other the object to be copied
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

    protected String getUsername() {
        return USERNAME;
    }

    public String getPassword() {
        return password;
    }

    public void showMenu() {
        int opt;

        do {
            prompt();
            opt = Menu.display("User", menuOptions);
            chooseMenu(opt);
        } while (opt != 4);

        logOut();
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
        Scanner input = new Scanner(System.in);
        Calendar.Builder builder = new Calendar.Builder();
        int time;
        builder.setLenient(false);

        System.out.print("Establishment Code: ");
        String estCode = input.nextLine();

        Calendar date = getDate();
        Calendar temp = null;
        do {
            try {
                System.out.println("-TIME-");
                System.out.print("Military Time: ");
                time = Integer.parseInt(input.nextLine());

                builder.setTimeOfDay(time / 100, time % 100, 0);
                builder.setDate(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
                temp = builder.build();
            } catch (Exception e) {
                System.out.println("Invalid time input!\n");
            }
        } while (temp == null);

        visitRec.add(new Visit(estCode, date));
    }

    private void reportPositive() {
        isPositive = true;
        isChanged = true;
        UserSystem.addCase(new Case(this.USERNAME, getDate()));
    }

    private void updateInfo() {
        int opt, max = UPDATE_OPTIONS.length;

        do {
            opt = Menu.display("Update Information", UPDATE_OPTIONS);
            if (opt != max) {
                if (opt == 1) {
                    name.changeName();
                } else if (opt == max - 1) {
//                    UserSystem.setPassword(this.USERNAME);
                    this.password = UserSystem.setPassword();
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

//    /**
//     * Updates the text file with changes made while the user was logged in
//     */

    /**
     * Replaces the object in the System
     */
    protected void logOut() {
        if (isChanged) {
//            String pass = null;
//
//            try (Scanner input = new Scanner(new File(USERNAME + ".act"))) {
//                pass = input.nextLine();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try (FileWriter writer = new FileWriter(USERNAME + ".act", false)) {
//                writer.write(pass + "\n");
//                writer.write(name.toString() + "\n");
//                writer.write("HOME:" + homeAddress + "\n");
//                writer.write("OFFICE:" + officeAddress + "\n");
//                writer.write("PHONE:" + phoneNumber + "\n");
//                writer.write("EMAIL:" + email + "\n");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            UserSystem.updateUser(this);
            this.isChanged = false;
        }
    }

    protected static Calendar getDate() {
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
}