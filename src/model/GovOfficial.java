package model;

import java.time.LocalDate;
import java.util.Calendar;

/**
 * This GovOfficial class extends the Citizen class and includes administrator
 * facilities such as viewing analytics, assigning cases, and promoting or terminating accounts.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 2.0
 * @see Citizen
 */
public class GovOfficial extends Citizen {
    /**
     * Receives a Citizen class and makes an exact copy of its attributes.
     * @param citizen the object used to construct the new object.
     */
    public GovOfficial(Citizen citizen) {
        super(citizen);
    }

    /**
     * Assigns the given case to a contact tracer based on the given username
     * of the contact tracer.
     * @param positive the case number of the case to be assigned.
     * @param tracer the username of the contact tracer the case is to be assigned to.
     */
    public void assignCase(Case positive, String tracer) {
        positive.setTracer(tracer);
    }

    /**
     * Checks whether the given case fits the criteria (parameter).
     * @param c the specific case to check.
     * @param city the name of the city.
     * @param status the status of the case.
     * @param start the start of date range.
     * @param end the end of date range.
     * @return true if the case fits the criteria, false otherwise.
     */
    public boolean filter(Case c, String city, char status, LocalDate start, LocalDate end, boolean mustBeUnassigned) {
        if (mustBeUnassigned) {
            return c.getTracer().equals("000") && filter(c, status, start, end);
        }

        return filter(c, city, status, start, end);
    }

    /**
     * Checks whether the given case fits the criteria (parameter).
     * @param c the specific case to check.
     * @param city the name of the city.
     * @param status the status of the case.
     * @param start the start of date range.
     * @param end the end of date range.
     * @return true if the case fits the criteria, false otherwise.
     */
    public boolean filter(Case c, String city, char status, LocalDate start, LocalDate end) {
        if (city == null) {
            return filter(c, status, start, end);
        }

        Citizen temp = UserSystem.getUser(c.getUsername());
        return temp != null && temp.getHomeAddress().toUpperCase().contains(city.toUpperCase()) && filter(c, status, start, end);
    }

    /**
     * Checks whether the given case fits the criteria (parameter).
     * @param c the specific case to check.
     * @param status the status of the case.
     * @param start the start of date range.
     * @param end the end of date range.
     * @return true if the case fits the criteria, false otherwise.
     */
    public boolean filter(Case c, char status, LocalDate start, LocalDate end) {
        return (status == '\0')? filter(c, start, end) : filter(c, start, end) && status == c.getStatus();
    }

    /**
     * Checks whether the given case fits the criteria (parameter).
     * @param c the specific case to check.
     * @param start the start of date range.
     * @param end the end of date range.
     * @return true if the case fits the criteria, false otherwise.
     */
    public boolean filter(Case c, LocalDate start, LocalDate end) {
        Calendar dateStart, dateEnd;
        dateStart = Calendar.getInstance();
        dateEnd = Calendar.getInstance();

        // remove time values
        dateStart.clear();
        dateEnd.clear();

        if (start == null && end == null) {
            return true;
        } else if (start != null && end != null) {
            dateStart.set(start.getYear(), start.getMonthValue() - 1, start.getDayOfMonth());
            dateEnd.set(end.getYear(), end.getMonthValue() - 1, end.getDayOfMonth() + 1);

            return c.getReportDate().compareTo(dateStart) >= 0 && c.getReportDate().before(dateEnd);
        } else if (end != null) {
            dateEnd.set(end.getYear(), end.getMonthValue() - 1, end.getDayOfMonth() + 1);
            return c.getReportDate().before(dateEnd);
        }

        dateStart.set(start.getYear(), start.getMonthValue() - 1, start.getDayOfMonth());
        return c.getReportDate().compareTo(dateStart) >= 0;
    }

    /**
     * Creates a randomly generated valid password with a specific pattern to allow for
     * readability. The pattern is "cvcvcvs" where c stands for a random consonant, v stands
     * for a random vowel, and s stands for a randoms special character.
     * @return a randomly generated valid password.
     */
    public String generatePassword() {
        String vowels = "aeiou";
        String consonants = "bcdfghjklmnpqrstvwxyz";
        String special = "!@#$%^&*()_+1234567890";
        StringBuilder password = new StringBuilder();

        // alternating consonant and vowel to make it readable (easier to remember)
        for (int i = 0; i < 3; i++) {
            password.append(consonants.charAt((int) (consonants.length() * Math.random())));
            password.append(vowels.charAt((int) (vowels.length() * Math.random())));
        }
        password.append(special.charAt((int) (special.length() * Math.random())));

        return password.toString();
    }
}