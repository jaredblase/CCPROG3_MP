import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Visit {
    /** The establishment code */
    private String estCode;
    /** The check in time and date */
    private Calendar checkIn;
    /** The date and time format */
    private static final SimpleDateFormat format = new SimpleDateFormat("MM,dd,yyyy HHmm");

    public Visit(String estCode, Calendar checkIn) {
        this.estCode = estCode;
        this.checkIn = checkIn;
    }

    @Override
    public String toString() {
        return estCode + " " + format.format(checkIn.getTime());
    }
}
