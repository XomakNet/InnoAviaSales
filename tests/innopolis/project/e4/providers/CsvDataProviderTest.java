package innopolis.project.e4.providers;

import innopolis.project.e4.models.Airport;
import innopolis.project.e4.models.Flight;
import innopolis.project.e4.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

/**
 * CsvDataProvider test class
 */
public class CsvDataProviderTest {
    private CsvDataProvider dp;
    private List<Airport> airports = new LinkedList<>();
    private HashMap<Airport, HashMap<Airport, Set<Flight>>> flightsByAirports;
    private Random rand = new Random();

    private String generateRandomString(int number) {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            sb.append(chars[random.nextInt(chars.length)]);
        }
        return sb.toString();
    }

    private Path createDirectory() throws IOException {
        Path rootDir = Files.createTempDirectory("data_provider_test");
        Path flightsDir = Files.createDirectory(Paths.get(rootDir.toString() + "/flights"));
        return rootDir;
    }

    private void generateAirports(final int number) {
        HashSet<Airport> airportsSet = new HashSet<>();
        for(int i=0; i < number; i++) {
            airportsSet.add(new Airport(generateRandomString(3)));
        }
        airports.addAll(airportsSet);
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

    private Set<Flight> generateFlights(final int number, final String airline) {
        Date currentDate = new Date();
        Set<Flight> generatedFlights = new HashSet<>();
        for(int i=0; i < number; i++) {
            int flightNumber = i;
            int airportFromIndex;
            int airportToIndex;

            do {
                airportFromIndex = rand.nextInt(airports.size());
                airportToIndex = rand.nextInt(airports.size());
            }
            while(airportFromIndex != airportToIndex);
            int freePlaces = rand.nextInt(700);
            float cost = Math.abs(rand.nextFloat()) * 1000;
            Date departureDate = getRandomIncreasedDate(currentDate, 10, 20, 59);
            Date arrivalDate = getRandomIncreasedDate(departureDate, 1, 23, 59);

            generatedFlights.add(new Flight(i, departureDate, arrivalDate, cost, freePlaces, airline, airports.get(airportFromIndex), airports.get(airportToIndex)));


        }

        return generatedFlights;
    }

    private void generateFlightsFile(final String path, final int number, final String airline) throws FileNotFoundException, UnsupportedEncodingException {
        Set<Flight> generatedFlights = generateFlights(number, airline);
        Iterator<Flight> iterator = generatedFlights.iterator();
        PrintWriter writer = new PrintWriter(path+"/"+airline+".csv", "UTF-8");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        while (iterator.hasNext()) {
            Flight curr = iterator.next();
            writer.println(String.format("\"%s\";\"%s\";\"%s\";\"%s\";\"%s\";\"%s\";\"%s\"",
                    curr.getFlightNumber(), curr.getFrom().getName(), curr.getTo().getName(), dateFormat.format(curr.getDepartureDateTime()),
                    dateFormat.format(curr.getArrivalDateTime()), curr.getCost(), curr.getFreePlaces()));
            if (flightsByAirports.containsKey(curr.getFrom())) {
                HashMap<Airport, Set<Flight>> flightsToAirport = flightsByAirports.get(curr.getFrom());
                if (flightsToAirport.containsKey(curr.getTo())) {
                    Set<Flight> flights = flightsToAirport.get(curr.getTo());
                    flights.add(curr);
                }
                else {
                    Set<Flight> flights = new HashSet<>();
                    flights.add(curr);
                    flightsToAirport.put(curr.getTo(), flights);
                }
            }
            else {
                Set<Flight> flights = new HashSet<>();
                flights.add(curr);
                HashMap<Airport, Set<Flight>> flightsToAirport = new HashMap<>();
                flightsToAirport.put(curr.getTo(), flights);
                flightsByAirports.put(curr.getFrom(), flightsToAirport);
            }
        }
        writer.close();

    }

    private Path generateTestData() throws Exception {
        Path tmpDirectory = createDirectory();
        generateAirports(50);
        int airlinesNumber = rand.nextInt(10);
        for(int i=0; i < airlinesNumber; i++) {
            int flightsNumber = rand.nextInt(500);
            String airline = "A"+i;
            generateFlightsFile(tmpDirectory.toString()+"/flights", flightsNumber, airline);
        }
        return tmpDirectory;
    }

    @Before
    public void init() throws Exception {
        System.out.println("Setting up ...: " + generateTestData().toString());
        System.out.println(airports);
        dp = new CsvDataProvider(generateTestData().toString());
    }

    @After
    public void destroy() throws Exception {
        System.out.println("Tearing down ...");
        dp = null;
    }

    @Test
    public void testGetAirportsAchievableFrom() {
        Iterator<Airport> fromIterator = flightsByAirports.keySet().iterator();
        while(fromIterator.hasNext()) {
            Airport currentFrom = fromIterator.next();
            Set<Airport> resultFromTested = dp.getAirportsAchievableFrom(currentFrom);
            Set<Airport> resultFromOriginal = flightsByAirports.get(currentFrom).keySet();
            assertEquals(resultFromOriginal, resultFromTested);
        }

    }

    @Test
    public void testGetFlightsBetween() {
        Iterator<Airport> airportsIteratorFirst = airports.iterator();
        while(airportsIteratorFirst.hasNext()) {
            Iterator<Airport> airportsIteratorSecond = airports.iterator();
            Airport fromAirport = airportsIteratorFirst.next();
            while(airportsIteratorSecond.hasNext()) {
                Airport toAirport = airportsIteratorSecond.next();
                Set<Flight> testResult = dp.getFlightsBetween(fromAirport, toAirport);
                if(flightsByAirports.containsKey(fromAirport)) {
                    HashMap<Airport, Set<Flight>> flightsFromAirport = flightsByAirports.get(fromAirport);
                    if(flightsFromAirport.containsKey(toAirport)) {
                        Set<Flight> flightsBetween = flightsFromAirport.get(toAirport);
                        assertEquals(testResult, flightsBetween);
                    }
                    else {
                        assertNull(testResult);
                    }
                }
                else {
                    assertNull(testResult);
                }
            }
        }
    }

    @Test
    public void testPutUser() {
        int id = rand.nextInt(10000);
        assertNull(dp.getUserById(id));
        User userAdded = new User(id, "Test user", "Test data", new Date());
        dp.putUser(userAdded);
        assertEquals(dp.getUserById(id), userAdded);
    }

}