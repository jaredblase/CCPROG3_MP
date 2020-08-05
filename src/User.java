import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class User {
    private static ArrayList<String> usernames;
    private static ArrayList<String> roles;

    /**
     * Handles the registration of a new user
     */
    public static void register() {
        Scanner input = new Scanner(System.in);

        System.out.println("ACCOUNT CREATION\n");
        System.out.print("Username: ");
        String username = input.nextLine();
        while (!isUnique(username)) {
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

            // add username and role to ArrayList
            usernames.add(username);
            roles.add("citizen");

            System.out.println("----YOU MAY NOW LOGIN WITH YOUR NEW ACCOUNT----");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the username received is not in the master list.
     *
     * @param username the name to be checked in the master list.
     * @return true if the username is not in the master list.
     */
    public static boolean isUnique(String username) {
        return !usernames.contains(username);
    }

    public static void loadUsers() {
        usernames = new ArrayList<>();
        roles = new ArrayList<>();
        String[] info;

        File masterList = new File("Master_List.txt");
        try (Scanner input = new Scanner(masterList)) {
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

    }

    public static void saveUsers() {
        try (FileWriter masterList = new FileWriter("Master_List.txt", false)) {
            for (int i = 0; i < usernames.size(); i++) {
                masterList.write(usernames.get(i) + " " + roles.get(i) + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String setPassword() {
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

    public static void setPassword(String username) {
        String pass = setPassword();
    }

    public static Citizen login() {
        Scanner input = new Scanner(System.in);

        System.out.print("Username: ");
        String username = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();
//        input.close();

        File file = new File(username + ".act");
        try (Scanner reader = new Scanner(file)) {
            if(!password.equals(reader.nextLine())) {
                throw new FileNotFoundException();
            }

            String role = roles.get(usernames.indexOf(username));
            String[] name = reader.nextLine().split(",");
            String homeAdd = reader.nextLine().substring(5);
            String officeAdd = reader.nextLine().substring(8);
            String phoneNumber = reader.nextLine().substring(7);
            String email = reader.nextLine().substring(7);

            switch (role) {
                case "citizen":
                    return new Citizen(new Name(name[0], name[1], name[2]), homeAdd,
                        officeAdd, phoneNumber, email, username);

                case "official":
                    return new GovOfficial(new Name(name[0], name[1], name[2]), homeAdd,
                            officeAdd, phoneNumber, email, username);

                case "tracer":
                    System.out.println("Feature under maintenance...");
//                    return new Tracer(new Name(name[0], name[1], name[2]), homeAdd,
//                            officeAdd, phoneNumber, email, username);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Invalid username/password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
