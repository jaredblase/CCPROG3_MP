import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class handles everything that deals with any actions of a user outside of his account
 * such as registering, login, loading and saving of users to the master list, and changing of
 * passwords since they are not loaded into the memory during the session.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 1.1
 */
public class UserSystem {
    /** All the registered usernames */
    private static ArrayList<String> usernames;
    /** The roles of the registered users */
    private static ArrayList<String> roles;
    /** The data of each user */
    private static ArrayList<Citizen> users;
    /** The visit records of each user */
    private static ArrayList<ArrayList<Visit>> records;
    /** The list of positive cases */
    private static ArrayList<Case> cases;
    /** The number of registered tracers */
    private static int nTracers;

    /**
     * Checks if the username received is in the master list.
     * @param username the name to be checked in the master list.
     * @return -1 if not found, greater than or equal to 0 otherwise.
     */
    public static int getIndexOf(String username) {
        return usernames.indexOf(username);
    }

    /**
     * Returns the username of a specific user in the system given his index.
     * @param index the index of a specific user in the system.
     * @return the username of the user.
     */
    public static String getUsername(int index) {
        return usernames.get(index);
    }

    /**
     * Returns the role of a specific user in the system given his index.
     * @param index the index of a specific user in the system.
     * @return the role of the user.
     */
    public static String getRoleOf(int index) {
        return roles.get(index);
    }

    /**
     * Returns the number of registered users.
     * @return the number of registered users in the system.
     */
    public static int getNumUsers() {
        return usernames.size();
    }

    /**
     * Returns the user given a specified username. If the username
     * is not found in the master list, it returns null.
     * @param username the name to be checked in the master list.
     * @return the user with the specified username if found, null otherwise.
     */
    public static Citizen getUser(String username) {
        int index = getIndexOf(username);
        if (index != -1)
            return users.get(index);
        else
            return null;
    }

    /**
     * Returns the visit records of each user.
     * @return the visit records of each user.
     */
    public static ArrayList<ArrayList<Visit>> getRecords() {
        return records;
    }

    /**
     * Returns the list of cases.
     * @return the list of cases.
     */
    public static ArrayList<Case> getCases() {
        return cases;
    }

    /**
     * Returns the number of registered tracers.
     * @return the number of registered tracers in the system.
     */
    public static int getNumTracers() {
        return nTracers;
    }

    /**
     * Obtains the input username then calls register(username) to handle
     * the rest of the registration process.
     */
    public static void register() {
        Scanner input = new Scanner(System.in);

        System.out.println("ACCOUNT CREATION\n");
        System.out.print("Username: ");
        register(input.nextLine());
    }

    /**
     * Handles the registration of a new user given a username.
     * @param username the chosen username of the account to created.
     */
    public static void register(String username) {
        Scanner input = new Scanner(System.in);

        // Checks if the username is already taken.
        // If it is, this block loops and asks for a new input.
        while (getIndexOf(username) != -1 || username.isBlank()) {
            if (getIndexOf(username) != -1) // taken username
                System.out.println("Username has already been taken!\n");
            else // username is blank
                System.out.println("Invalid username!\n");
            System.out.print("Username: ");
            username = input.nextLine();
        }
        String password = checkPassword();
        System.out.println("\nPersonal Information:");

        // get name
        String firstName = getValidString("First name");
        System.out.print("Middle name: ");
        String secondName = input.nextLine().trim();
        String lastName = getValidString("Last name");

        // other information
        String homeAdd = getValidString("Home address");
        String officeAdd = getValidString("Office address");
        String phoneNumber = getValidString("Phone number");
        String email = getValidString("Email address");

        // add username, role, and information to ArrayList
        usernames.add(username);
        roles.add("citizen");
        users.add(new Citizen(new Name(firstName, secondName, lastName), homeAdd, officeAdd, phoneNumber,
                email, username, password));

        System.out.println("----YOU MAY NOW LOGIN WITH YOUR NEW ACCOUNT----");
    }

    /**
     * Prints the question and receives a String input. If the input is invalid, it loops.
     * @param question the question to be printed
     * @return a non empty String
     */
    private static String getValidString(String question) {
        Scanner input = new Scanner(System.in);
        question += ": ";

        System.out.print(question);
        String str = input.nextLine().trim();
        while (str.isEmpty()) {
            System.out.println("Invalid input!\n");
            System.out.print(question);
            str = input.nextLine();
        }

        return str;
    }

    /**
     * Handles the logging in of an existing user. It asks the user to input his username
     * and password. Once valid, loads everything from the user's information in the system.
     * @return a constructed Citizen object with the user's details.
     */
    public static Citizen login() {
        Scanner input = new Scanner(System.in);

        System.out.print("Username: ");
        int index = getIndexOf(input.nextLine());
        System.out.print("Password: ");
        String password = input.nextLine();

        if (index != -1 && users.get(index).getPassword().equals(password)) {
            String role = getRoleOf(index);
            switch (role) {
                case "citizen":
                    return new Citizen(users.get(index));

                case "official":
                    return new GovOfficial(users.get(index));

                case "tracer":
                    return new Tracer(users.get(index));
            }
        } else {
            System.out.println("Invalid username/password!");
        }

        return null;
    }

    /**
     * Asks the user to enter a new password and check it for validity.<br>
     * Once valid, returns the chosen password.
     * @return valid password entered by the user.
     */
    public static String checkPassword() {
        Scanner input = new Scanner(System.in);
        String regex = "[\\w\\s]*[\\W\\d][\\w\\s]*"; // looks for at least one special character

        System.out.print("New Password: ");
        String pass = input.nextLine();
        // Loops and asks for another password while the password is invalid.
        while(pass.length() < 6 || !pass.replaceAll("\\s+", "").matches(regex)) {
            System.out.println("Password must contain at least 6 characters including 1 digit or special " +
                    "character that is not a space!\n");
            System.out.print("New Password: ");
            pass = input.nextLine();
        }

        return pass;
    }

    /**
     * Sets a new role for a specific user in the system given his index.
     * @param index the index of a specific user in the system.
     * @param role the new role to be assigned to the user.
     */
    public static void setRoleOf(int index, String role) {
        if (roles.get(index).equals("tracer")) { // previous role is tracer
            nTracers--;
        } else if (role.equals("tracer")) { // new role is tracer
            nTracers++;
        }

        roles.set(index, role);
        System.out.println("Modification success!");
    }

    /**
     * Replaces the object in the system with the new and update one.
     * @param citizen the object to replace the one in the system.
     */
    public static void updateUser(Citizen citizen) {
        users.set(usernames.indexOf(citizen.getUsername()), citizen);
    }

    /**
     * Adds a visit record to the list of visit records in the system.
     * @param record the visit record to be added.
     * @param username the name to be checked in the master list.
     */
    public static void addRecord(Visit record, String username) {
        records.get(getIndexOf(username)).add(record);
    }

    /**
     * Adds a case to the list of cases in the system.
     * @param positive the case to be added.
     */
    public static void addCase(Case positive) {
        cases.add(positive);
    }

    /**
     * Initializes all the ArrayLists and creates the admin account.
     */
    public static void loadUsers() {
        usernames = new ArrayList<>();
        roles = new ArrayList<>();
        users = new ArrayList<>();
        records= new ArrayList<>();
        cases = new ArrayList<>();
        nTracers = 0;

        usernames.add("Admin2020");
        roles.add("official");
        users.add(new GovOfficial(new Citizen(new Name("Admin", "A", "Gov"), "Malacañang",
                "City Hall", "09328287114", "admin@gov.ph", "Admin2020",
                "@Dm1n0202")));
        records.add(new ArrayList<>());
    }

    /**
     * Called when terminating the program to free all the memory used.
     */
    public static void exitSystem() {
        usernames = null;
        roles = null;
        users = null;
        records = null;
        cases = null;

        System.gc();
    }
}
