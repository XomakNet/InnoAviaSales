package innopolis.project.e4;

import innopolis.project.e4.models.Airport;
import innopolis.project.e4.models.Flight;
import innopolis.project.e4.providers.TestDataProvider;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Test class for search optimizer
 */
public class SearchOptimizerTest {
    private SearchOptimizer so;
    private TestDataProvider dp;
    private Random rand = new Random();

    private int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

    private String generateRandomString(int number) {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < number; i++) {
            sb.append(chars[rand.nextInt(chars.length)]);
        }
        return sb.toString();
    }

    @Before
    public void init() {
        dp = new TestDataProvider();
        so = new SearchOptimizer(dp);
    }

    private Date getRandomIncreasedDate(final Date basedOnDate, int days, int hours, int minutes) {
        int daysPlus = rand.nextInt(days);
        int hoursPlus = rand.nextInt(hours);
        int minutesPlus = rand.nextInt(minutes);
        Calendar cal = Calendar.getInstance();
        cal.setTime(basedOnDate);
        cal.add(Calendar.DATE, daysPlus);
        cal.add(Calendar.HOUR, hoursPlus);
        cal.add(Calendar.MINUTE, minutesPlus);
        return cal.getTime();
    }

    @Test
    public void testGetPathesBetween() throws Exception {
        Airport previous = null;
        GregorianCalendar gc = new GregorianCalendar();
        List<Flight> result = new LinkedList<>();
        int year = randBetween(1900, 2010);
        int hour = randBetween(0, 24);
        int minutes = randBetween(0, 59);
        int dayOfYear = randBetween(1, gc.getActualMaximum(GregorianCalendar.DAY_OF_YEAR));
        gc.set(GregorianCalendar.YEAR, year);
        gc.set(GregorianCalendar.DAY_OF_YEAR, dayOfYear);
        gc.set(GregorianCalendar.HOUR_OF_DAY, hour);
        gc.set(GregorianCalendar.MINUTE, minutes);
        Date startDate = gc.getTime();
        Date date = startDate;
        Airport from = null;
        Airport to = null;
        for(int i = 0; i < randBetween(1, 5); i++) {
            Airport current = new Airport(generateRandomString(3));
            to = current;
            if(previous != null) {
                Date departureDate = getRandomIncreasedDate(date, 2, 24, 59);
                Date arrivalDate = getRandomIncreasedDate(departureDate, 1, 24, 59);
                Flight flight = new Flight(rand.nextInt(), departureDate, arrivalDate, rand.nextFloat()*100,
                        rand.nextInt(150), generateRandomString(5), previous, current);
                dp.addFlight(flight);
                result.add(flight);
                date = arrivalDate;
            }
            else {
                from = current;
            }
            previous = current;
        }
        List<Flight> testResult = so.getPathesBetween(from, to, startDate, SearchOptimizer.Criterion.COST);
        assertEquals(testResult, result);
        testResult = so.getPathesBetween(from, to, startDate, SearchOptimizer.Criterion.COST);
        assertNull(testResult);
    }
}