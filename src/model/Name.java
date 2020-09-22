package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * Initializes a Name object and sets the fields with the given parameters:
     * first, middle, and last name.
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
     * Sets the first, middle, or last name (indicated by opt), and replaces it
     * with str. Returns whether the String input is valid and accepted.
     * @param opt indicates which part of the name to change.
     * @param str the String to replace the name.
     * @return whether the String input is valid and accepted.
     * @since 1.0
     */
    public boolean setName(int opt, String str) {
        str = str.trim();   // removes excess whitespace before and after the String
        Matcher matcher = Pattern.compile("\\d").matcher(str);

        // prevents names with numbers and allows middle name to be empty
        if ((!str.isEmpty() || opt == 2) && !matcher.find()) {
            switch (opt) {
                case 1 -> this.first = str;
                case 2 -> this.middle = str;
                case 3 -> this.last = str;
            }
            return true;
        }

        return false;
    }

    /**
     * Returns the first name.
     * @return the first name.
     */
    public String getFirst() {
        return first;
    }

    /**
     * Returns the middle name.
     * @return the middle name.
     */
    public String getMiddle() {
        return middle;
    }

    /**
     * Returns the last name.
     * @return the last name.
     */
    public String getLast() {
        return last;
    }

    /**
     * Returns the full name of a Name object in String format.
     * @return the full name separated by spaces.
     * @since 1.2
     */
    public String getFullName() {
        return first + " " + (middle.isEmpty()? "" : (middle + " ")) + last;
    }

    /**
     * Returns the full name of a Name object in String format.
     * @return the full name separated by commas.
     * @since 1.2
     */
    @Override
    public String toString() {
        return first + "," + middle + "," + last;
    }
}