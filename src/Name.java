/**
 * The Name class holds all the information of a name
 * and methods available for changing them.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 1.2
 */
public class Name {
    /** The first name. */
    private String first;
    /** The middle name. */
    private String middle;
    /** The last name. */
    private String last;
    /** Menu options for changing names. */
    public static final Menu CHANGE_NAME_MENU = new Menu ("Change Name","First Name",
            "Middle Name", "Last Name", "Back");

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
     * Sets the first, middle, or last name (indicated by opt), and replace it
     * with str.
     * @param opt indicates which part of the name to change.
     * @param str the String to replace the name.
     * @throws Exception if an empty String is received to replace the first or last name.
     * @since 1.0
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

    /**
     * Returns the full name of a Name object in String format.
     * @return the full name separated by spaces.
     * @since 1.2
     */
    public String getFullName() {
        return first + " " + (middle.isEmpty()? "" : (middle + " ")) + last;
    }
}