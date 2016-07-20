package innopolis.project.e4.providers;


import innopolis.project.e4.models.Airport;
import innopolis.project.e4.models.Flight;
import innopolis.project.e4.models.User;


import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static java.lang.System.in;

/***
 * CSV-files based data-provider
 */
public class CsvDataProvider implements DataProvider {
    private HashMap<Airport, HashMap<Airport, HashSet<Flight>>> flightsByAirports;
    private HashMap<Integer, User> users;
    private File fileSource;
    private String fileSoursesPath;
    private LinkedList<File> companyCSV;
    private File usersFile;
    private HashMap<String, Airport> airports;
    private HashSet<Flight> flights;
    private String extentionOfDataSourceFile = ".csv";
    private String separator = ",";
    private String border = "\"";
    private int numOfFieldsInCSV = 7;

    public CsvDataProvider(String path){
        users = new HashMap<>();
        fileSoursesPath = path;
        fileSource = new File(fileSoursesPath);

        if(fileSource.isDirectory()){
            for (File f : fileSource.listFiles()) {
                if(f.isDirectory()){
                    for(File flight_f : f.listFiles())
                        companyCSV.add(flight_f);
                }
                else if(!f.isDirectory() && f.getName().contains(extentionOfDataSourceFile))
                    usersFile = f;
            }
        }
        parseFlightsCSV();
        parseUserCSV();
    }

    private boolean parseFlightsCSV() {
        BufferedReader bufReader;
        for (File f : companyCSV) {
            try {
                bufReader = new BufferedReader(new FileReader(f));
                String readingLine = "";
                while ((readingLine = bufReader.readLine()) != null) {
                    String[] line = readingLine.split(separator);
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date dateD = df.parse(line[1]);
                    Date dateA = df.parse(line[2]);
                    airports.put(line[5], new Airport(line[5]));
                    airports.put(line[6], new Airport(line[6]));
                    Flight fTmp = new Flight(
                            Integer.parseInt(line[0]),
                            dateD,
                            dateA,
                            Float.parseFloat(line[3]),
                            Integer.parseInt(line[4]),
                            f.getName().substring(0, f.getName().length() - 4), // "-4" cause '.CSV' so, company name,
                            // like "CompanyName.CSV" after substring
                            // will be like "CompanyName"
                            airports.get(line[5]),
                            airports.get(line[6]));
                    flights.add(fTmp);
                    if(flightsByAirports.containsKey(airports.get(fTmp.getFrom()))){
                        if(flightsByAirports.get(fTmp.getFrom()).containsKey(fTmp.getTo())){
                            flightsByAirports.get(fTmp.getFrom()).get(fTmp.getTo()).add(fTmp);
                        }else{
                            flightsByAirports.get(fTmp.getFrom()).put(fTmp.getTo(), new HashSet<Flight>());
                            flightsByAirports.get(fTmp.getFrom()).get(fTmp.getTo()).add(fTmp);
                        }
                    }else{
                        flightsByAirports.put(fTmp.getFrom(), new HashMap<Airport, HashSet<Flight>>());
                        flightsByAirports.get(fTmp.getFrom()).put(fTmp.getTo(),new HashSet<Flight>());
                        flightsByAirports.get(fTmp.getFrom()).get(fTmp.getTo()).add(fTmp);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }


    public Set<Airport> getAllAirports() {
        if(flightsByAirports!=null){
            return  flightsByAirports.keySet();
        }
        return null;
    }

    @Override
    public Set<Airport> getAirportsAchievableFrom(final Airport from) {
        HashMap<Airport, HashSet<Flight>> fromAirport = flightsByAirports.get(from);
        if(fromAirport != null) {
            return fromAirport.keySet();
        }
        return null;
    }

    public Set<Flight> getFlightsBetween(Airport from, Airport to) {
        if(flightsByAirports.containsKey(from)){
            if(flightsByAirports.get(from).containsKey(to))
                return flightsByAirports.get(from).get(to);
        }
        return null;
    }

    private void parseUserCSV(){
        BufferedReader bufReader;
        File f = usersFile;
        try {
            bufReader = new BufferedReader(new FileReader(f));
            String readingLine = "";
            while ((readingLine = bufReader.readLine()) != null) {
                String[] line = readingLine.split(separator);
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date dateD = df.parse(line[4]);
                users.put(Integer.parseInt(line[0]), new User(Integer.parseInt(line[0]), line[1] + " " + line[2], line[3], dateD));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(int id) {
        if(users.containsKey(id))
            return users.get(id);
        return null;
    }

    public Set<User> getAllUsers() {
        if(users!=null){
            Set<User> tmpUsers = new HashSet<>();
            for (User user: users.values()) {
                tmpUsers.add(user);
            }
            return tmpUsers;
        }
        return null;
    }

    public boolean putUser(final User user) {
        if(users.containsKey(user.getID()))
            return false;
        else
            users.put(user.getID(), user);
        return true;
    }

    public boolean putFlight(final Flight flight) {
        if(flightsByAirports.containsKey(flight.getFrom())){
            if(flightsByAirports.get(flight.getFrom()).containsKey(flight.getTo())) {
                if (flightsByAirports.get(flight.getFrom()).get(flight.getTo()).contains(flight)){
                    return false;
                }else{
                    flightsByAirports.get(flight.getFrom()).get(flight.getTo()).add(flight);
                }
            }else{
                flightsByAirports.get(flight.getFrom()).put(flight.getTo(), new HashSet<Flight>());
                flightsByAirports.get(flight.getFrom()).get(flight.getTo()).add(flight);
            }
        }else{
            HashMap<Airport, HashSet<Flight>> tmpHM = new HashMap<>();
            tmpHM.put(flight.getTo(), new HashSet<Flight>());
            tmpHM.get(flight.getTo()).add(flight);
            Airport airport = flight.getFrom();
            flightsByAirports.put(airport, tmpHM);
        }
        return true;
    }

    public HashMap<Airport, HashMap<Airport, HashSet<Flight>>> getAirportsMates(){
        return flightsByAirports;
    }
}

