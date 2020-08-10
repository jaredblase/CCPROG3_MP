import java.util.Scanner;

/**
 * This class is used for generating menus and retrieving an answer from the user.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 1.1
 */
public class Menu {
    /**
     * Displays the header of the menu before showing all the menu options passed
     * to the method in a numbered order.
     * @param header the header of the menu to be printed i.e. Main Menu.
     * @param options a variable argument to store the menu options in an array.
     * @return the option number chosen
     */
    public static int display(String header, String...options) {
        int ctr = 1;

        //display menu
        System.out.println();
        System.out.println(header + " Menu");
        for (String i: options) {
            System.out.println(ctr++ + " - " + i);
        }
        System.out.println();

        return getAnswer(options.length); //return option input
    }

    /**
     * Displays yes or no question and returns a validated answer.
     *
     * @param question the question to be displayed.
     * @return Y for yes, N for no.
     */
    public static char YorN(String question) {
        String opt;
        Scanner input = new Scanner(System.in);

        do {
            System.out.print(question + "? (Y/N) ");
            try {
                opt = input.nextLine(); // get input
                opt = opt.toUpperCase(); // capitalize
                if (!opt.equals("Y") && !opt.equals("N")) { //invalid option input
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Invalid option!\n");
                opt = null;
            }
        } while (opt == null);

        return opt.charAt(0);
    }

    /**
     * Obtains an integer by means of the Scanner and Integer class. Also performs
     * exception handling in case an invalid option is input by the user.
     * @param max indicates the number of menu options available
     * @return a number representing the chosen option of the user
     */
    private static int getAnswer(int max) {
        Scanner input = new Scanner(System.in);
        int opt;

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

       // input.close();
        return opt;
    }
}