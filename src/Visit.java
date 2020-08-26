import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * The Visit class holds all the information for a citizen's visit
 * to a an establishment. It also provides methods to retrieve data from it,
 * but once constructed, the fields cannot be reset.
 * @author Gabriel Pua
 * @author Jared
 * @version 1.1
 */
public class Visit {
    /** The establishment code. */
    private final String EST_CODE;
    /** The check in time and date. */
    private final Calendar CHECK_IN;

    /**
     * Constructs a Visit object and sets the fields from the
     * received parameters.
     * @param estCode the establishment code.
     * @param checkIn the check in time and date.
     * @since 1.0
     */
    public Visit(String estCode, Calendar checkIn) {
        this.EST_CODE = estCode;
        this.CHECK_IN = checkIn;
    }

    /**
     * Returns the check in time and date of the visit.
     * @return the check in time and date.
     * @since 1.0
     */
    public Calendar getCheckIn() {
        return CHECK_IN;
    }

    /**
     * Returns the establishment code of the visit.
     * @return the establishment code.
     * @since 1.1
     */
    public String getEstCode() {
        return EST_CODE;
    }

    /**
     * Return the string formatted as follows:<br>
     * &lt;establishment code&gt;&lt;space&gt;&lt;month&gt;,&lt;day&gt;,&lt;year&gt;&lt;space&gt;&lt;24-hour
     * format&gt;
     * @return the formatted String.
     * @since 1.0
     */
    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("MM,dd,yyyy HHmm");
        return EST_CODE + " " + format.format(CHECK_IN.getTime());
    }
}
