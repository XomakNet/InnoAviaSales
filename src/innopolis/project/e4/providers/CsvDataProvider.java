package innopolis.project.e4.providers;


import innopolis.project.e4.models.Airport;
import innopolis.project.e4.models.Flight;
import innopolis.project.e4.models.User;


import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import static java.lang.System.in;
import static java.time.LocalDate.*;

/***
 * CSV-files based data-provider
 */
public class CsvDataProvider implements DataProvider {
    private final String FlightsDirectoryName = "flights";
    private final String UsersSourceFile = "Users.csv";
    private HashMap<Airport, HashMap<Airport, HashSet<Flight>>> flightsByAirports;
    private HashMap<Integer, User> users;
    private File fileSource;
    private String fileSoursesPath;
    private LinkedList<File> companyCSV;
    private File usersFile;
    private HashMap<String, Airport> airports;
    private HashSet<Flight> flights;
    private String extentionOfDataSourceFile = ".csv";
    private String separator = ";";
    private String border = "\"";
    private int numOfFieldsInCSV = 7;
    private String datePattern = "dd/MM/yyyy HH:mm:ss";

    public CsvDataProvider(String path) {

        users = new HashMap<>();
        fileSoursesPath = path;
        fileSource = new File(fileSoursesPath);
        airports = new HashMap<>();
        companyCSV = new LinkedList<>();
        flights = new HashSet();
        flightsByAirports = new HashMap<>();
        generateFilesLists();
        if(companyCSV!=null && companyCSV.size() > 0){
            for (File f : companyCSV) {
                parseFlightsCSV(f);
            }
            System.out.println("Flights list has been created");
        }else System.out.println("Folder Flights is empty");
        if(usersFile != null) {
            parseUserCSV();
            System.out.println("File Users has been created");
        }else System.out.println("File Users has not been added ");
    }
    private  void generateFilesLists(){
        if (fileSource.isDirectory()) {
            for (File f : fileSource.listFiles()) {
                if (f.isDirectory() && f.getName().contains((FlightsDirectoryName))) {
                    for (File flight_f : f.listFiles())
                        companyCSV.add(flight_f);
                } else if (!f.isDirectory() && f.getName().contains(UsersSourceFile))
                    usersFile = f;
            }
        } /*else if (fileSource.getName().compareTo("output.csv") == 0) {
            parseFlightsCSV(new File("D:\\�����\\Innopolis SummerSchool\\InnoAviaSales\\output.csv"));
            //companyCSV.add(fileSource);
        }*/

    }

    private boolean parseFlightsCSV(File f) {
        BufferedReader bufReader;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        {
            try {
                bufReader = new BufferedReader(new FileReader(f));
                String readingLine = "";
                while ((readingLine = bufReader.readLine()) != null) {
                    String[] line = readingLine.replaceAll("\"", "").split(separator);
                    //DateFormat df = new SimpleDateFormat(datePattern);
                    LocalDateTime ldtF = LocalDateTime.parse(line[3], dateFormat);
                    LocalDateTime ldtT = LocalDateTime.parse(line[4], dateFormat);
                    //Date dateD = df.parse(line[3]);
                    //Date dateA = df.parse(line[4]);
                    airports.put(line[1], new Airport(line[1]));
                    airports.put(line[2], new Airport(line[2]));
                    Flight fTmp = new Flight(
                            Integer.parseInt(line[0]),
                            ldtF,
                            ldtT,
                            Float.parseFloat(line[5]),
                            Integer.parseInt(line[6]),
                            f.getName().substring(0, f.getName().length() - 4), // "-4" cause '.CSV' so, company name,
                                                                                // like "CompanyName.CSV" after substring
                                                                                // will be like "CompanyName"
                            airports.get(line[1]),
                            airports.get(line[2]));
                    flights.add(fTmp);
                    if (null != airports.get(fTmp) && flightsByAirports.containsKey(airports.get(fTmp.getFrom()))) {
                        if (flightsByAirports.get(fTmp.getFrom()).containsKey(fTmp.getTo())) {
                            flightsByAirports.get(fTmp.getFrom()).get(fTmp.getTo()).add(fTmp);
                        } else {
                            flightsByAirports.get(fTmp.getFrom()).put(fTmp.getTo(), new HashSet<Flight>());
                            flightsByAirports.get(fTmp.getFrom()).get(fTmp.getTo()).add(fTmp);
                        }
                    } else {
                        flightsByAirports.put(
                                fTmp.getFrom(),
                                new HashMap<Airport, HashSet<Flight>>());
                        flightsByAirports.get(fTmp.getFrom()).put(fTmp.getTo(), new HashSet<Flight>());
                        flightsByAirports.get(fTmp.getFrom()).get(fTmp.getTo()).add(fTmp);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }


    public Set<Airport> getAllAirports() {
        if (flightsByAirports != null) {
            return flightsByAirports.keySet();
        }
        return null;
    }

    public Set<Flight> getFlightsBetween(Airport from, Airport to) {
        if (flightsByAirports.containsKey(from)) {
            if (flightsByAirports.get(from).containsKey(to))
                return flightsByAirports.get(from).get(to);
        }
        return null;
    }

    private void parseUserCSV() {
        BufferedReader bufReader;
        if (usersFile == null) return;
        File f = usersFile;
        try {
            bufReader = new BufferedReader(new FileReader(f));
            String readingLine = "";
            while ((readingLine = bufReader.readLine()) != null) {
                String[] line = readingLine.split(separator);
                //DateFormat df = new SimpleDateFormat(datePattern);
                LocalDateTime dateD = LocalDateTime.parse(line[4]);
                users.put(Integer.parseInt(line[0]), new User(Integer.parseInt(line[0]), line[1] + " " + line[2], line[3], dateD));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(int id) {
        if (users.containsKey(id))
            return users.get(id);
        return null;
    }

    public Set<User> getAllUsers() {
        if (users != null) {
            Set<User> tmpUsers = new HashSet<>();
            for (User user : users.values()) {
                tmpUsers.add(user);
            }
            return tmpUsers;
        }
        return null;
    }

    public boolean putUser(final User user) {
        if (users.containsKey(user.getID()))
            return false;
        else
        {
            users.put(user.getID(), user);
            WriteIt(user);
        }

        return true;
    }

    private boolean WriteIt(User user) {
        try{
            PrintWriter pW = new PrintWriter(usersFile);
            pW.write(user.toString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean WriteIt(Flight flight, File companyNameFile) {
        try{
            PrintWriter pW = new PrintWriter(companyNameFile);
            pW.write(flight.toString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Set<Airport> getAirportsAchievableFrom(final Airport from) {
        HashMap<Airport, HashSet<Flight>> fromAirport = flightsByAirports.get(from);
        if (fromAirport != null) {
            return fromAirport.keySet();
        }
        return null;
    }

    public boolean putFlight(final Flight flight) {
        if (flightsByAirports.containsKey(flight.getFrom())) {
            if (flightsByAirports.get(flight.getFrom()).containsKey(flight.getTo())) {
                if (flightsByAirports.get(flight.getFrom()).get(flight.getTo()).contains(flight)) {
                    return false;
                } else {
                    flightsByAirports.get(flight.getFrom()).get(flight.getTo()).add(flight);
                }
            } else {
                flightsByAirports.get(flight.getFrom()).put(flight.getTo(), new HashSet<Flight>());
                flightsByAirports.get(flight.getFrom()).get(flight.getTo()).add(flight);
            }
        } else {
            HashMap<Airport, HashSet<Flight>> tmpHM = new HashMap<>();
            tmpHM.put(flight.getTo(), new HashSet<Flight>());
            tmpHM.get(flight.getTo()).add(flight);
            Airport airport = flight.getFrom();
            flightsByAirports.put(airport, tmpHM);
        }
        File newF = findCompany(flight.getAirline());
        if(null != newF) {
            WriteIt(flight, newF);
            return true;
        }
        System.out.println("Flight has not been added");
        return false;
    }

    private File findCompany(String companyName){
        for(File file : companyCSV){
            if(file.getName().contains(companyName)) return file;
        }
        return null;
    }
    public HashMap<Airport, HashMap<Airport, HashSet<Flight>>> getAirportsMates() {
        return flightsByAirports;
    }
}


