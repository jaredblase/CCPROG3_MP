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
            UserSystem.updateUser(user);
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

    /**
     * Calls the appropriate function based on the user's input.
     * @param opt integer representing the chosen menu option.
     */
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
            case 3 -> updateInfo(user); // Update profile info
        }
    }

    /**
     * The facility that handles the updating of personal information.
     */
    private static void updateInfo(Citizen user) {
        Scanner input = new Scanner(System.in);
        Menu menu = Citizen.UPDATE_OPTIONS;
        int opt, max = menu.getLength();

        do {
            boolean isChanged = false;
            menu.display();
            opt = getMenuAnswer(max);
            if (opt != max) {
                if (opt == 1) {
                    //isChanged = name.changeName();
                } else if (opt == max - 1) {
                    try {
                        System.out.print("New Password: ");
                        user.setPassword(input.nextLine());
                        isChanged = true;
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                } else {
                    System.out.print("New " + menu.getOption(opt - 1) + ": ");
                    String str = input.nextLine();

                    try {
                        switch (opt) {
                            case 2 -> user.setHomeAddress(str);
                            case 3 -> user.setOfficeAddress(str);
                            case 4 -> user.setPhoneNumber(str);
                            case 5 -> user.setEmail(str);
                        }
                        isChanged = true;
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                }

                if (isChanged) {
                    UserSystem.updateUser(user);
                    System.out.println(menu.getOption(opt - 1) + " has been updated!\n");
                }
            }
        } while (opt != max);
    }

    private static void governmentActions(int opt, GovOfficial user) {
        Scanner input = new Scanner(System.in);

        switch (opt) {
            case 4 -> {
                int[] caseNums = user.showUnassigned();

                if (caseNums != null) {
                    if (UserSystem.getNumTracers() == 0) // no tracers
                        System.out.println("Create contact tracer accounts to assign cases.\n");
                    else {
                        int caseNum = getCaseNum(caseNums); // get case
                        assignCase(user, caseNum); // get tracer and assign case to tracer
                    }
                }
            }
            case 5 -> user.showUpdates(obtainDateRange());
//            case 6 -> showAnalytics(user);
//            case 7 -> modifyRole("official");
//            case 8 -> modifyRole("tracer");
//            case 9 -> modifyRole("citizen");
        }
    }

    /**
     * Gets a case number input from the user that corresponds to the case to be assigned.
     * If the case number input is found in the given array of case numbers, it is
     * valid. Once valid, the case number is returned.
     * @param caseNums the array of case numbers of unassigned cases.
     * @return the valid case number.
     */
    private static int getCaseNum(int[] caseNums) {
        Scanner input = new Scanner(System.in);
        int caseNum = 0;

        // get case number
        do {
            try {
                System.out.print("Assign case number: ");
                caseNum = Integer.parseInt(input.nextLine());

                for (int i: caseNums)
                    if (caseNum == i) {
                        return caseNum;
                    }

                throw new Exception();
            } catch (Exception e) {
                System.out.println("Invalid input!\n");
            }
        } while (true);
    }

    /**
     * Gets a username input from the user that corresponds to the contact tracer that
     * the case referenced by the given case number is being assigned to.
     * @param user the government official assigning the case.
     * @param caseNum the case number corresponding to the case to be assigned.
     */
    private static void assignCase(GovOfficial user, int caseNum) {
        Scanner input = new Scanner(System.in);
        boolean status = false;

        // get tracer username
        do {
            System.out.print("Enter username of tracer: ");
            String tracer = input.nextLine();

            int index = UserSystem.getIndexOf(tracer);
            if (index == -1) { // user not found
                System.out.println("No user with username \"" + tracer + "\" found.\n");
            } else if (UserSystem.getRoleOf(index).equals("tracer")) {
                status = true;

                // assign case to tracer
                user.assignCase(caseNum, tracer);
            } else // user is found but is not a tracer
                System.out.println("User " + tracer + " is not a contact tracer.\n");
        } while (!status);
    }

    /**
     * Obtains the start and end date from the user. The first element in the
     * returned Calendar array is the starting date while the second is the end date.
     * @return the start and end date by means of a Calendar array.
     */
    private static Calendar[] obtainDateRange() {
        Calendar start, end;

        System.out.println("Starting date:");
        start = getDate();
        System.out.println("\nEnd date:");
        end = getDate();
        while (start.after(end)) { // check if the input date is before the start date
            System.out.println("Invalid end date!");
            System.out.println("\nEnd date:");
            end = getDate();
        }
        // sets end date to 12 am the next day
        end.add(Calendar.DATE, 1);

        return new Calendar[] {start, end};
    }

    private static void showAnalytics(GovOfficial user) {
        Scanner input = new Scanner(System.in);
        Menu menu = GovOfficial.ANALYTICS_MENU;
        int opt, max = menu.getLength();

        //unfinished
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
