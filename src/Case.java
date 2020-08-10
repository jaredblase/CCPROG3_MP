import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Case {
    private final int CASE_NUM;
    private final String USERNAME;
    private final Calendar REPORT_DATE;
    private String tracer;
    private char status;
    private static int ctr = 0;
    private static final SimpleDateFormat format = new SimpleDateFormat(" MM,dd,yyyy ");

    public Case(String username, Calendar date) {
        this.CASE_NUM = ++ctr;
        this.USERNAME = username;
        this.REPORT_DATE = date;
        this.tracer = "000";
        this.status = 'P';
    }

    public void setTracer(String tracer) {
        this.tracer = tracer;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public String getTracer() {
        return tracer;
    }

    public Calendar getReportDate() {
        return REPORT_DATE;
    }

    @Override
    public String toString() {
        return CASE_NUM + " " + USERNAME + format.format(REPORT_DATE.getTime()) + tracer + " " + status;
    }
}
