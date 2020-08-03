import java.util.Scanner;

/**
 * This class holds the information of a name
 * and methods available for them.
 * @author Gabriel Pua
 * @author Jared Sy
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

    public void changeName() {
        Scanner input = new Scanner(System.in);
        int opt;

        do {
            opt = Menu.display("Name Change", "First Name", "Middle Name", "Last Name", "Back");
            if(opt != 4) {
                System.out.print("New Name: ");
                switch (opt) {
                    case 1 -> setFirst(input.nextLine());
                    case 2 -> setMiddle(input.nextLine());
                    case 3 -> setLast(input.nextLine());
                }
                System.out.println("New name:");
                System.out.println(this);
            }
        } while (opt != 4);

        input.close();
    }

    private void setFirst(String first) {
        this.first = first;
    }

    private void setMiddle(String middle) {
        this.middle = middle;
    }

    private void setLast(String last) {
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