package innopolis.project.e4;

import innopolis.project.e4.models.Airport;
import innopolis.project.e4.models.Flight;
import innopolis.project.e4.providers.CsvDataProvider;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        CsvDataProvider dataProvider = new CsvDataProvider("C:\\Users\\Master\\AppData\\Local\\Temp\\data_provider_test6614774960699843900");
        //System.out.println(dataProvider.getAllAirports());
        HashMap<Airport, HashMap<Airport, HashSet<Flight>>> testTmp = dataProvider.getAirportsMates();
        Set<Airport> airp = dataProvider.getAllAirports();
        for (int i = 0; i < testTmp.values().size(); i++) {
            Flight f = (Flight) dataProvider.getFlight(i);
            System.out.println(f.getFrom().getName() + ":");
            for (Airport a :
                    dataProvider.getAirportsAchievableFrom(f.getFrom())) {
                System.out.println("\t" + a.getName());

            }
        }
        for (Iterator<Airport> iter = airp.iterator(); iter.hasNext(); ) {
            Airport air = iter.next();
            ArrayList<Flight> achFl = new ArrayList<>();
            if (iter.hasNext()) {
                System.out.println(air.getName() + ":");
                Set<Flight> flights = dataProvider.getFlightsBetween(air, iter.next());
                if (null != flights) {
                    for (Iterator<Flight> flIter = flights.iterator(); flIter.hasNext(); ) {
                        achFl.add(flIter.next());
                        System.out.println("\t" + achFl.get(achFl.size() - 1));
                    }
                    System.out.println("\t" + flights.size());
                }else System.out.println("\t" + "null");
            }
        }
    }
}
