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
 */
public class Citizen {
    private Name name;
    private String homeAddress;
    private String officeAddress;
    private String phoneNumber;
    private String email;
    private String username;
    private ArrayList<Visit> visitRec;
    private boolean isPositive;
    private boolean maybePositive;
    private boolean isChanged;
    protected static String[] menuOptions = {"Check in", "Report positive", "Update profile information", "Logout"};
    private static final String[] updateOptions = {"Name", "Home Address", "Office Address", "Phone Number",
            "E-Mail", "Password", "Back to User Menu"};

    /**
     *
     * @param name the Name of object containing the name of the user
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
        this.username = username;
        visitRec = new ArrayList<>();
        isPositive = false;
        maybePositive = false;
        isChanged = false;
    }

    protected String getUsername() {
        return username;
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
    }

    private void updateInfo() {
        int opt, max = updateOptions.length;

        do {
            opt = Menu.display("Update Information", updateOptions);
            if (opt != max) {
                if (opt == 1) {
                    name.changeName();
                } else if (opt == max - 1) {
                    User.setPassword(this.username);
                } else {
                    Scanner input = new Scanner(System.in);
                    System.out.print("New " + updateOptions[opt - 1] + ": ");
                    String str = input.nextLine();

                    switch (opt) {
                        case 2 -> this.homeAddress = str;
                        case 3 -> this.officeAddress = str;
                        case 4 -> this.phoneNumber = str;
                        case 5 -> this.email = str;
                    }
                }
                System.out.println(updateOptions[opt - 1] + " has been updated!\n");
                isChanged = true;
            }
        } while (opt != max);
    }

    protected void logOut() {
        String pass = null;

        try (Scanner input = new Scanner(new File(username + ".act"))) {
            pass = input.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(username + ".act", false)) {
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