import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Case {
    /** The case number */
    private final int CASE_NUM;
    /** The username of the positive case */
    private final String USERNAME;
    /** The date the case was reported */
    private final Calendar REPORT_DATE;
    /** The username of the contact tracer assigned to the case */
    private String tracer;
    /** The status whether the case is already traced or not */
    private char status;
    /** The total number of cases */
    private static int ctr = 0;
    /** The date format */
    private static final SimpleDateFormat format = new SimpleDateFormat(" MM,dd,yyyy ");

    public Case(String username, Calendar date) {
        this.CASE_NUM = ++ctr;
        this.USERNAME = username;
        this.REPORT_DATE = date;
        this.tracer = "000";
        this.status = 'P';
    }

    /**
     * Sets the username of the contact tracer assigned to the case
     * @param tracer the username of the contact tracer assigned to the case
     */
    public void setTracer(String tracer) {
        this.tracer = tracer;
    }

    /**
     * Sets the status whether the case is already traced or not
     * @param status the status whether the case is already traced or not
     */
    public void setStatus(char status) {
        this.status = status;
    }

    /**
     * Returns the username of the contact tracer assigned to the case
     * @return the username of the contact tracer assigned to the case
     */
    public String getTracer() {
        return tracer;
    }

    /**
     * Returns the date the case was reported
     * @return the date the case was reported
     */
    public Calendar getReportDate() {
        return REPORT_DATE;
    }

    /**
     * Returns the case number
     * @return the case number
     */
    public int getCaseNum() {
        return CASE_NUM;
    }

    /**
     * Returns the total number of cases
     * @return the total number of cases
     */
    public static int getCount() {
        return ctr;
    }

    /**
     * Returns the status whether the case is already traced or not
     * @return the status whether the case is already traced or not
     */
    public char getStatus() {
        return status;
    }

    /**
     * Returns the username of the positive case
     * @return the username of the positive case
     */
    public String getUsername() {
        return USERNAME;
    }

    @Override
    public String toString() {
        return CASE_NUM + " " + USERNAME + format.format(REPORT_DATE.getTime()) + tracer + " " + status;
    }
}
