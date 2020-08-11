//import java.io.*;

import java.io.*;
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
    /** The visit records of each user */
    private static ArrayList<ArrayList<Visit>> records;
    /** The list of positive cases */
    private static ArrayList<Case> cases;
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
        String path = username + ".act";

        // attempt to create a file
        try {
            File file = new File(path);
            if (file.createNewFile()) {
                System.out.println("Welcome " + username);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // attempt to write to the file
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(setPassword() + "\n");
            String password = setPassword();
            System.out.println("Personal Information:");

            // get name
            System.out.print("First name: ");
            writer.write(input.nextLine() + ",");
            System.out.print("Middle name: ");
            writer.write(input.nextLine() + ",");
            System.out.print("Last name: ");
            writer.write(input.nextLine() + "\n");

            // other information
            System.out.print("Home address: ");
            writer.write("HOME:" + input.nextLine() + "\n");
            System.out.print("Office address: ");
            writer.write("OFFICE:" + input.nextLine() + "\n");
            System.out.print("Phone number: ");
            writer.write("PHONE:" + input.nextLine() + "\n");
            System.out.print("Email address: ");
            writer.write("EMAIL:" + input.nextLine() + "\n");

            // add username, role, and information to ArrayList
            usernames.add(username);
            roles.add("citizen");

            System.out.println("----YOU MAY NOW LOGIN WITH YOUR NEW ACCOUNT----");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called to load the users from Master_List.txt to static ArrayList usernames and roles
     */
    public static void loadSystem() {
        usernames = new ArrayList<>();
        roles = new ArrayList<>();
        records= new ArrayList<>();
        cases = new ArrayList<>();
        nTracers = 0;

        String[] info;

        try (Scanner input = new Scanner(new File("Master_List.txt"))) {
            do {
                info = input.nextLine().split(" ");
                usernames.add(info[0]);
                roles.add(info[1]);
            } while (input.hasNextLine());
        } catch (FileNotFoundException e) {
            System.out.println("Error! Master list not found.\nNo admin currently.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // load visit records here
    }

    /**
     * Called to save the users from static ArrayList usernames and roles to Master_List.txt
     */
    public static void saveSystem() {
        try (FileWriter masterList = new FileWriter("Master_List.txt", false)) {
            for (int i = 0; i < usernames.size(); i++) {
                masterList.write(usernames.get(i) + " " + roles.get(i) + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        roles.set(index, role);
        if (role.equals("tracer"))
            nTracers++;
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
     * Only called by existing users wanting to change their passwords. Calls setPassword()
     * to validate and updates the user's data in the text file.
     * @param username the user's username
     */
    public static void setPassword(String username) {
        String pass = setPassword();
        File file = new File(username + ".act");
        char[] info = new char[(int) file.length()];

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine();
            if (reader.read(info, 0, info.length) == -1)
                throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(pass + "\n");
            writer.write(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the logging in of an existing user. It asks the user to input his username
     * and password. Once valid, loads everything from the user's text file.
     * @return a constructed Citizen object with the user's details.
     */
    public static Citizen login() {
        Scanner input = new Scanner(System.in);

        System.out.print("Username: ");
        String username = input.nextLine();
        int index = usernames.indexOf(username);

        System.out.print("Password: ");
        String password = input.nextLine();

        try (Scanner reader = new Scanner(new File(username + ".act"))) {
            if (!password.equals(reader.nextLine()) || index == -1) {
                throw new FileNotFoundException();
            }

            String role = roles.get(index);
            String[] name = reader.nextLine().split(",");
            String homeAdd = reader.nextLine().substring(5);
            String officeAdd = reader.nextLine().substring(7);
            String phoneNumber = reader.nextLine().substring(6);
            String email = reader.nextLine().substring(6);

            switch (role) {
                case "citizen":
                    return new Citizen(new Name(name[0], name[1], name[2]), homeAdd,
                            officeAdd, phoneNumber, email, username, records.get(index));

                case "official":
                    return new GovOfficial(new Name(name[0], name[1], name[2]), homeAdd,
                            officeAdd, phoneNumber, email, username, records.get(index));

                case "tracer":
                    return new Tracer(new Name(name[0], name[1], name[2]), homeAdd,
                            officeAdd, phoneNumber, email, username, records.get(index));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Invalid username/password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void addCase(Case positive) {
        cases.add(positive);
    }

    public static ArrayList<Case> getCases() {
        return cases;
    }

    public static int getNumTracers() {
        return nTracers;
    }
}
