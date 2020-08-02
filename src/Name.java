/**
 * This class holds the information of a name
 * and methods available for them.
 * @author Pua, Gabriel
 * @author Sy, Jared
 * @version 1.0
 */
public class Name {
    /**
     * This constructor initializes a Name object with a first, 
     * middle, and last name.
     * @param first is the first name
     * @param middle is the middle name
     * @param last is the last name
     */
    public Name(String first, String middle, String last) {
        this.first = first;
        this.middle = middle;
        this.last = last;
    }

    /**
     * This method returns the full name of a Name object in String 
     * format. 
     * @return the full name
     */
    @Override
    public String toString() {
        return first + " " + middle + " " + last;
    }

    /** first is the first name */
    private String first;
    /** middle is the middle name */
    private String middle;
    /** last is the last name */
    private String last;
}