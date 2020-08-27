import javax.imageio.stream.IIOByteBuffer;
import java.util.Scanner;

/**
 * The Menu class is used for generating menus and retrieving an answer from the user.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 1.1
 */
public class Menu {
    /** The header of the menu. */
    private final String header;
    /** The options that may be selected in the menu. */
    private final String[] options;

    /**
     * Constructs a Menu object and assigns the header and the list of options.
     * @param header the header of the menu.
     * @param options the options that may be selected in the menu.
     */
    public Menu(String header, String...options) {
        this.header = header + " Menu";
        this.options = options;
    }

    /**
     * Returns the number of options of the menu.
     * @return the number of options of the menu.
     */
    public int length() {
        return options.length;
    }

    /**
     * Displays the header of the menu before showing all the options
     * in a numbered order.
     * @since 1.0
     */
    public void display() {
        int ctr = 1;

        //display menu
        System.out.println();
        System.out.println(header);
        for (String i: options) {
            System.out.println(ctr++ + " - " + i);
        }
        System.out.println();
    }

    /**
     * Displays yes or no question and returns a validated answer.
     * @param question the question to be displayed.
     * @return Y for yes, N for no.
     * @since 1.1
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
     * Returns the option that is located in the given index of the list of options.
     * @param index the index of the option in the list of options.
     * @return the option that is located in the given index of the list of options.
     */
    public String getOption(int index) {
        try {
            return options[index];
        } catch (Exception e) {
            return e.toString();
        }
    }
}