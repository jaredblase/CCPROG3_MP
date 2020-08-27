import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        UserSystem.loadUsers();
        Menu mainMenu = new Menu("Main", "Register", "Login", "Exit");
        int opt;

        do {
            mainMenu.display();
            opt = getMenuAnswer(mainMenu.getLength());
            switch (opt) {
                case 1 -> register();
                case 2 -> login();
            }
            System.out.println();
        } while (opt != 3);

        UserSystem.exitSystem();
        System.out.println("Terminating program...");
    }

    /**
     * Obtains an integer by means of the Scanner and Integer class. Also performs
     * exception handling in case an invalid option is input by the user.
     * @param max indicates the number of menu options available.
     * @return a number representing the chosen option of the user.
     */
    private static int getMenuAnswer(int max) {
        int opt;
        Scanner input = new Scanner(System.in);

        do {
            System.out.print("Option: ");
            try {
                opt = Integer.parseInt(input.nextLine()); // get input
                if (opt < 1 || opt > max) {//invalid option input
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Invalid option!\n");
                opt = max + 1; //set opt to loop again
            }
        } while (opt > max);

        return opt;
    }

    private static void register() {
        Scanner input = new Scanner(System.in);

        System.out.println("Username: ");
        String username = input.nextLine().toUpperCase();
        while (!UserSystem.isValidNewUsername(username)) {
            System.out.println("Taken/Invalid username.\n");
            System.out.println("Username: ");
            username = input.nextLine().toUpperCase();
        }

        System.out.print("New Password: ");
        String password = input.nextLine();
        while (!UserSystem.isValidPassword(password)){
            System.out.println("Password must contain at least 6 characters including 1 digit or special " +
                    "character that is not a space!\n");
            System.out.print("New Password: ");
            password = input.nextLine();
        }

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

        UserSystem.register(new Citizen(new Name(firstName, secondName, lastName), homeAdd, officeAdd, phoneNumber,
                email, username, password));

        System.out.println("----YOU MAY NOW LOGIN WITH YOUR NEW ACCOUNT----");
    }

    private static void login() {
        Scanner input = new Scanner(System.in);

        System.out.print("Username: ");
        String username = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();

        Citizen user = UserSystem.login(username, password);
        if (user != null) {
            int opt, max = user.getUserMenu().getLength();
            do {
                user.prompt();
                user.getUserMenu().display();
                opt = getMenuAnswer(max);

                if (opt <= 3) {         // citizen account options
                    citizenActions(opt, user);
                } else if (opt == max) {    // logout
                    break;
                } else if (user instanceof GovOfficial) {
                    governmentActions(opt, (GovOfficial) user);
                } else {
                    tracerActions(opt, (Tracer) user);
                }
            } while (true);
                System.out.println("Logged out.");
        } else {
            System.out.println("Invalid username/password!");
        }
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
     * Retrieves a date input from the user and attempts to build a Calendar object
     * from it.
     * @return a valid date.
     */
    private static Calendar getDate() {
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

    private static void citizenActions(int opt, Citizen user) {
        Scanner input = new Scanner(System.in);

        switch (opt) {
            case 1 -> {     // Check In
                System.out.print("Establishment Code: ");
                String estCode = input.nextLine();
                Calendar date = getDate();
                user.checkIn(estCode, date);
            }
            case 2 -> {     // Report Positive
                if (user.getIsPositive()) {
                    user.reportPositive(getDate());
                    System.out.println("Case reported. Thank you.\n");
                } else {
                    System.out.println("You are already reported positive!");
                }
            }
            case 3 -> {     // Update profile info

            }
        }
    }

    private static void governmentActions(int opt, GovOfficial user) {

    }

    private static void tracerActions(int opt, Tracer user) {
        Scanner input = new Scanner(System.in);

        switch (opt) {
            case 4 -> user.showCases();
            case 5 -> {
                ArrayList<Case> assigned = user.getAssigned();
                if (assigned.size() == 0) { // no cases assigned
                    System.out.println("No assigned cases");
                } else { // at least 1 assigned case
                    int caseNum = -1;
                    boolean status = false;

                    // get case number
                    try {
                        System.out.print("Enter case number to be traced: ");
                        caseNum = Integer.parseInt(input.nextLine());

                        for (Case i: assigned) {
                            if (i.getCaseNum() == caseNum) { // case number is assigned
                                status = true;
                                break;
                            }
                        }

                        if (!status) // case number is not among assigned cases
                            throw new Exception();
                    } catch (Exception e) {
                        System.out.println("Invalid input. Use the show cases option to view your assigned cases.\n");
                    }

                    if (status) // valid case number input
                        user.trace(caseNum);
                }
            }
            case 6 -> user.broadcast();
        }
    }
}
