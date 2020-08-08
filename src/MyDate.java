import java.util.Scanner;

public class MyDate {
    private final int MONTH;
    private final int DAY;
    private final int YEAR;

    public MyDate(int month, int day, int year) {
        MONTH = month;
        DAY = day;
        YEAR = year;
    }

    public static MyDate getDate() {
        int month, day, year, status;
        Scanner input = new Scanner(System.in);

        do {
            System.out.print("Enter month: ");
            try {
                month = Integer.parseInt(input.nextLine());
                System.out.print("Enter day: ");
                try {
                    day = Integer.parseInt(input.nextLine());
                    System.out.print("Enter year: ");
                    try {
                        year = Integer.parseInt(input.nextLine());
                    } catch (Exception e) {
                        year = -1;
                    }
                } catch (Exception e) {
                    day = -1;
                    year = -1;
                }
            } catch (Exception e) {
                month = -1;
                day = -1;
                year = -1;
            }

            if (!isValid(month, day, year)) {
                status = 0;
                System.out.println("Invalid date input\n");
            } else {
                status = 1;
            }

        } while (status == 0);

        return new MyDate(month, day, year);
    }

    private static boolean isValid(int month, int day, int year) {
        if (year > 999 && year < 2100) {
            return switch (month) {
                case 1, 3, 5, 7, 8, 10, 12 -> day >= 1 && day <= 31;
                case 4, 6, 9, 11 -> day >= 1 && day <= 30;
                case 2 -> (day >= 1 && day <= 28) || (day == 29 && (year % 4 == 0 &&
                        (year % 100 != 0 || year % 400 == 0)));
                default -> false;
            };
        } else return false;
    }

    @Override
    public String toString() {
        return String.format("%02d", MONTH) + "," + String.format("%02d", DAY) + "," + YEAR;
    }
}