import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class User {
    private static ArrayList<User> usernames;
    private final String username;
    private String role;

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public static void register() {
        Scanner input = new Scanner(System.in);
        String str;

        System.out.println("ACCOUNT CREATION\n");
        System.out.println("Username: ");
        str = input.nextLine();
        while(!isUnique(str)) {
            System.out.println("Username has already been taken!");
        }
        setPassword();
    }

    public static boolean isUnique(String username) {
        for (User user: usernames)
            if (user.getUsername().equals(username))
                return false;
        return true;
    }

    private String getUsername() {
        return username;
    }

    private String getRole() {
        return role;
    }

    public static void loadUsers() {
        usernames = new ArrayList<>();
        File masterList = new File("Master_List.txt");
        try (Scanner input = new Scanner(masterList)) {

        } catch (Exception e) {

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

        input.close();
        return pass;
    }

    public static void setPassword(String username) {
        String pass = setPassword();
    }

    public static void login() {

    }

    @Override
    public String toString() {
        return username + " " + role;
    }
}
