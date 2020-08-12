import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class handles everything that deals with any actions of a user outside of his account
 * such as registering, login, loading and saving of users to the master list, and changing of
 * passwords-since they are not loaded into the memory during the session.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 1.0
 */
public abstract class UserSystem {
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
     * Handles the registration of a new user
     */
    public static void register() {
        Scanner input = new Scanner(System.in);

        System.out.println("ACCOUNT CREATION\n");
        System.out.print("Username: ");
        String username = input.nextLine();
        while (getIndexOf(username) != -1) {
            System.out.println("Username has already been taken!");
            System.out.print("Username: ");
            username = input.nextLine();
        }
        String password = setPassword();
        System.out.println("\nPersonal Information:");

        // get name
        System.out.print("First name: ");
        String firstName = input.nextLine();
        System.out.print("Middle name: ");
        String secondName = input.nextLine();
        System.out.print("Last name: ");
        String lastName = input.nextLine();

        // other information
        System.out.print("Home address: ");
        String homeAdd = input.nextLine();

        System.out.print("Office address: ");
        String officeAdd = input.nextLine();

        System.out.print("Phone number: ");
        String phoneNumber = input.nextLine();

        System.out.print("Email address: ");
        String email = input.nextLine();

        // add username, role, and information to ArrayList
        usernames.add(username);
        roles.add("citizen");
        users.add(new Citizen(new Name(firstName, secondName, lastName), homeAdd, officeAdd, phoneNumber,
                email, username, password));

        System.out.println("----YOU MAY NOW LOGIN WITH YOUR NEW ACCOUNT----");

    }


    /**
     * Initializes all the ArrayLists and creates the admin account
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
     * Checks if the username received is not in the master list.
     *
     * @param username the name to be checked in the master list.
     * @return -1 if not found, greater than or equal to 0 otherwise.
     */
    public static int getIndexOf(String username) {
        return usernames.indexOf(username);
    }

    /**
     * Returns the role of specific user in the system given his index
     * @param index the index of a specific user in the system
     * @return the role of a user
     */
    public static String getRoleOf(int index) {
        return roles.get(index);
    }

    /**
     * Sets a new role for a specific user in the system given his index
     * @param index the index of a specific user in the system
     * @param role the new role to be assigned to the user
     */
    public static void setRoleOf(int index, String role) {
        if (roles.get(index).equals("tracer")) { //previous role is tracer
            nTracers--;
        }

        roles.set(index, role);

        if (role.equals("tracer")) { //new role is tracer
            nTracers++;
        }

        System.out.println("Modification success!");
    }

    /**
     * Asks the user to enter a new password and check it for validity. Once valid
     * return the chosen password.
     * @return valid password entered by the user.
     */
    public static String setPassword() {
        Scanner input = new Scanner(System.in);
        String regex = "[\\w\\s]*[\\W\\d][\\w\\s]*";
        String pass;

        System.out.print("New Password: ");
        pass = input.nextLine();
        while(pass.length() < 6 || !pass.replaceAll("\\s+", "").matches(regex)) {
            System.out.println("Password must contain at least 6 characters including 1 special " +
                    "character that is not a space!\n");
            System.out.print("New Password: ");
            pass = input.nextLine();
        }

        return pass;
    }

    /**
     * Handles the logging in of an existing user. It asks the user to input his username
     * and password. Once valid, loads everything from the user's text file.
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
     * Replaces the object in the system with the new and update one.
     * @param citizen the object to replace the one in the system.
     */
    public static void updateUser(Citizen citizen) {
        users.set(usernames.indexOf(citizen.getUsername()), citizen);
    }

    /**
     * Adds a case to the list of cases in the system
     * @param positive the case to be added
     */
    public static void addCase(Case positive) {
        cases.add(positive);
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
     * Returns the number of registered users.
     * @return the number of registered users in the system.
     */
    public static int getNumUsers() {
        return usernames.size();
    }
}
