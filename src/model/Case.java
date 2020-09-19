package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * The Case class holds all the information for a positive case. It also
 * provides methods to retrieve data from it, and set some of the other data.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 1.1
 */
public class Case {
    /** The case number. */
    private final int CASE_NUM;
    /** The username of the positive case. */
    private final String USERNAME;
    /** The date the case was reported. */
    private final Calendar REPORT_DATE;
    /** The username of the contact tracer assigned to the case. */
    private String tracer;
    /** The status whether the case is already traced or not. */
    private char status;
    /** The total number of cases. */
    private static int ctr = 0;

    /**
     * Constructs a Case object and assigns the username and date from the
     * parameters. Case number is automatically assigned and default values are
     * initialized to the rest of the fields.
     * @param username the username of the citizen who reported positive.
     * @param date the date when the user reported positive.
     */
    public Case(String username, Calendar date) {
        this.CASE_NUM = ++ctr;
        this.USERNAME = username;
        this.REPORT_DATE = date;
        this.tracer = "000";
        this.status = 'P';
    }

    /**
     * Returns the case number.
     * @return the case number.
     */
    public int getCaseNum() {
        return CASE_NUM;
    }

    /**
     * Returns the username of the positive case.
     * @return the username of the positive case.
     */
    public String getUsername() {
        return USERNAME;
    }

    /**
     * Returns the date the case was reported.
     * @return the date the case was reported.
     */
    public Calendar getReportDate() {
        return REPORT_DATE;
    }

    /**
     * Returns the username of the contact tracer assigned to the case.
     * @return the username of the contact tracer assigned to the case.
     */
    public String getTracer() {
        return tracer;
    }

    /**
     * Returns the status whether the case is already traced or not.
     * @return the status whether the case is already traced or not.
     */
    public char getStatus() {
        return status;
    }

    /**
     * Sets the username of the contact tracer assigned to the case.
     * @param tracer the username of the contact tracer assigned to the case.
     */
    public void setTracer(String tracer) {
        this.tracer = tracer;
    }

    /**
     * Sets the status whether the case is already traced or not.
     * @param status the status whether the case is already traced or not.
     */
    public void setStatus(char status) {
        this.status = status;
    }

    /**
     * Returns a String representation of a case.
     * @return a String that represents a case.
     */
    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat(" MM,dd,yyyy ");
        return CASE_NUM + " " + USERNAME + format.format(REPORT_DATE.getTime()) + tracer + " " + status;
    }
}
