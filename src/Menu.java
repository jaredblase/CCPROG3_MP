import java.util.Scanner;

public class Menu {
    public static int display(String header, String...options) {
        int ctr = 1;

        //display menu
        System.out.println(header);
        for (String i: options) {
            System.out.println(ctr++ + " - " + i);
        }
        System.out.println();

        return getAnswer(options.length); //return option input
    }

    private static int getAnswer(int max) {
        int opt;
        Scanner input = new Scanner(System.in);

        do {
            System.out.print("Option: ");
            try {
                opt = input.nextInt(); // get input
                if (opt < 1 || opt > max) //invalid option input
                    throw new Exception();
            } catch (Exception e) {
                System.out.println("Invalid option!\n");
                opt = max + 1; //set opt to loop again
            } finally {
                input.nextLine();
            }
        } while (opt > max);

        input.close();
        return opt;
    }
}