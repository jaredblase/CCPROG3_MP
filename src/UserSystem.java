import java.util.ArrayList;
import java.util.Calendar;

/**
 * This class handles everything that deals with any actions of a user outside of his account
 * such as registering, login, and loading and saving of users to the master list.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 1.1
 */
public class UserSystem {
    /** All the registered usernames. */
    private static ArrayList<String> usernames;
    /** The roles of the registered users. */
    private static ArrayList<String> roles;
    /** The data of each user. */
    private static ArrayList<Citizen> users;
    /** The visit records of each user. */
    private static ArrayList<ArrayList<Visit>> records;
    /** The list of positive cases. */
    private static ArrayList<Case> cases;
    /** The number of registered tracers. */
    private static int nTracers;

    /**
     * Checks if the username received is in the master list.
     * @param username the name to be checked in the master list.
     * @return the index of the username in the list, and -1 if it is not in the list.
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
     * Checks if the username has not been taken.
     * @param username the username.
     * @return true if the username is still available, false otherwise.
     */
    public static boolean isValidNewUsername(String username) {
        return getIndexOf(username) == -1 && !username.isBlank();
    }

    /**
     * Adds the citizen object to the database.
     * @param citizen the new user to add to the database.
     */
    public static void register(Citizen citizen) {
        // add username, role, and information to ArrayList and add new ArrayList for visit records
        usernames.add(citizen.getUsername());
        roles.add("citizen");
        users.add(citizen);
        records.add(new ArrayList<>());
    }

    /**
     * Handles the logging in of an existing user. Based on the username and password,
     * determines if they are valid. Once valid, loads everything from the user's
     * information in the system.
     * @param username the username of the user logging in.
     * @param password the password of the user logging in.
     * @return a constructed Citizen object with the user's details.
     */
    public static Citizen login(String username, String password) {
        int index = getIndexOf(username);

        if (index != -1 && users.get(index).getPassword().equals(password)) {
            String role = getRoleOf(index);
            switch (role) {
                case "citizen":
                    return new Citizen(users.get(index));

                case "official":
                    return new GovOfficial(users.get(index));

                case "tracer":
                    return new Tracer((Tracer) users.get(index));
            }
        }

        return null;
    }

    /**
     * Asks the user to enter a new password and checks it for validity. Returns true if valid
     * and false otherwise.
     * @param pass the String to be checked.
     * @return true if it is valid, false otherwise.
     */
    public static boolean isValidPassword(String pass) {
        String regex = "[\\w\\s]*[\\W\\d][\\w\\s]*"; // looks for at least one special character

        return pass.length() >= 6 && pass.replaceAll("\\s+", "").matches(regex);
    }

    /**
     * Sets a new role for a specific user in the system given his index.
     * @param index the index of a specific user in the system.
     * @param role the new role to be assigned to the user.
     */
    public static void setRoleOf(int index, String role) {
        if (roles.get(index).equals("tracer")) { // previous role is tracer
            Tracer temp = (Tracer) users.get(index);
            for (Case i: cases) {
                if (i.getTracer().equals(users.get(index).getUsername()))
                    i.setTracer("000");
            }
            temp.demote();
            nTracers--;
        } else if (role.equals("tracer")) { // new role is tracer
            nTracers++;
            updateUser(new Tracer(users.get(index)));
        }

        roles.set(index, role);
    }

    /**
     * Replaces the object in the system with the new and updated one.
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
     * @param username the username of the user who reported positive.
     * @param date the date when the case is reported.
     */
    public static void addCase(String username, Calendar date) {
        cases.add(new Case(username, date));
    }

    /**
     * Initializes all the ArrayLists and creates the admin account.
     */
    public static void loadSystem() {
        usernames = new ArrayList<>();
        roles = new ArrayList<>();
        users = new ArrayList<>();
        records= new ArrayList<>();
        cases = new ArrayList<>();
        nTracers = 0;

        usernames.add("ADMIN2020");
        roles.add("official");
        users.add(new GovOfficial(new Citizen(new Name("Admin", "A", "Gov"), "Malacañang",
                "City Hall", "09328287114", "admin@gov.ph", "ADMIN2020",
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
