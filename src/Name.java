

/**
 * The Name class holds all the information of a name
 * and methods available for changing them.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 1.1
 */
public class Name {
    /** The first name. */
    private String first;
    /** The middle name. */
    private String middle;
    /** The last name. */
    private String last;
    /** Menu options for changing names. */
    public static final Menu CHANGE_NAME_MENU = new Menu ("First Name", "Middle Name",
            "Last Name", "Back");

    /**
     * Initializes a Name object and sets the fields with the given parameters:
     * first, middle, and last name.<br>
     * Preconditions: The parameters are valid names and not empty or blank Strings.
     * @param first the first name.
     * @param middle the middle name.
     * @param last the last name.
     * @since 1.0
     */
    public Name(String first, String middle, String last) {
        this.first = first;
        this.middle = middle;
        this.last = last;
    }

    /**
     * Main entry point of the user when opting to change his name.<br>
     * Will display the menu, get the input, and replace the chosen field.<br><br>
     * Preconditions: The input is a valid name and not an empty or a blank String.
     * @since 1.0
     * @see Menu
     */
    public void setName(int opt, String str) throws Exception {
        str = str.trim();   // removes excess whitespace before and after the String

        if (!str.isEmpty() || opt == 2) {       // allows middle name to be empty
            switch (opt) {
                case 1 -> this.first = str;
                case 2 -> this.middle = str;
                case 3 -> this.last = str;
            }
        } else {
            throw new Exception(CHANGE_NAME_MENU.getOption(opt - 1) + " cannot be left blank!");
        }
    }

    public String displayName() {
        return first + " " + (middle.isEmpty()? "" : (middle + " ")) + last;
    }

    /**
     * Returns the full name of a Name object in String format.
     * @return the full name separated by commas.
     * @since 1.0
     */
    @Override
    public String toString() {
        return first + "," + middle + "," + last;
    }
}