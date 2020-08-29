

/**
 * The Menu class is used for generating menus and retrieving an answer from the user.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 1.1
 */
public class Menu {
    /** The header of the menu. */
    private final String HEADER;
    /** The options that may be selected in the menu. */
    private final String[] OPTIONS;

    /**
     * Constructs a Menu object and assigns the header and the list of options.
     * @param header the header of the menu.
     * @param options the options that may be selected in the menu.
     */
    public Menu(String header, String...options) {
        this.HEADER = header + " Menu";
        this.OPTIONS = options;
    }

    /**
     * Returns the number of options of the menu.
     * @return the number of options of the menu.
     */
    public int length() {
        return OPTIONS.length;
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
        System.out.println(HEADER);
        for (String i: OPTIONS) {
            System.out.println(ctr++ + " - " + i);
        }
        System.out.println();
    }

    /**
     * Returns the option that is located in the given index of the list of options.
     * @param index the index of the option in the list of options.
     * @return the option that is located in the given index of the list of options.
     */
    public String getOption(int index) {
        try {
            return OPTIONS[index];
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}