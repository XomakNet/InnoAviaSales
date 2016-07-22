package innopolis.project.e4;

import innopolis.project.e4.models.Airport;
import innopolis.project.e4.models.Flight;
import innopolis.project.e4.models.Path;
import innopolis.project.e4.providers.DataProvider;

import java.util.*;
import java.util.List;

/**
 * Created by Xomak on 15.07.2016.
 * Implements some quick path search alghoritm
 */
public class SearchOptimizer {
    enum Criterion {COST, TIME}

    private DataProvider provider;
    private Stack<Airport> minimalPathName = new Stack<>();
    Set<Stack<Airport>> resultFli = new HashSet<Stack<Airport>>();
    int MaxTrevelingCount = 5;

    public SearchOptimizer(final DataProvider provider) {
        this.provider = provider;
    }

    public void rebuildPaths() {

    }

    //curr посещенные вершины
    public List<Path> getPathesBetween(final Airport src,
                                       final Airport dst,
                                       final Date date,
                                       final Criterion criterion) {
        List<Path> result;
        minimalPathName.push(src);
        if (!src.equals(dst))
            dfsFlighting(src, dst);
        result = getFlight(date);
        return result;
    }

    private void dfsFlighting(final Airport src,
                              final Airport dst) {
        Set<Airport> neighbours = provider.getAirportsAchievableFrom(src);
        Iterator<Airport> it = neighbours.iterator();
        while (it.hasNext()) {
            Airport neibor = it.next();
            minimalPathName.push(neibor);
            if (neibor.equals(dst)) {
                minimalPathName.push(dst);
                resultFli.add(minimalPathName);
                minimalPathName.pop();
                break;
            }
            if (MaxTrevelingCount == minimalPathName.size()) {
                if (neighbours.contains(dst)) {
                    minimalPathName.pop();
                    minimalPathName.push(dst);
                    resultFli.add(minimalPathName);
                    minimalPathName.pop();
                    break;
                } else {
                    minimalPathName.pop();
                    break;
                }
            } else {
                dfsFlighting(neibor, dst);
            }
        }
    }

    public List<Path> getFlight(final Date date) {
        List<Path> resultFlight = new LinkedList<>();
        Path onceOfThePath = new Path();
        int flag = 0;
        Iterator<Stack<Airport>> it = resultFli.iterator();
        while (it.hasNext()) { // all Airports
            Stack<Airport> neibor = it.next();
            Airport [] someAirport = new Airport[neibor.size()];
            for(int i = neibor.size() - 1; i > -1; --i) { // stack = list
                someAirport[i] = neibor.pop();
            }
            onceOfThePath = new Path();
            for(int j = 0; j + 1 < someAirport.length && flag == 0; ++j) { //Stack
                Airport src = someAirport[j];
                Airport dst = someAirport[j + 1];
                Set<Flight> setFlight = new HashSet<>();

                setFlight = provider.getFlightsBetween(src, dst);
                if(setFlight != null) {
                    Iterator<Flight> itF = setFlight.iterator();
                    while (itF.hasNext()) {
                        Flight ownFli = itF.next();
                        if (onceOfThePath.getFlightsSequence().size() == 0) {
                            if (ownFli.getDepartureDateTime().compareTo(date) >= 0) {
                                onceOfThePath.addFlight(ownFli);
                            } else {
                                flag = 1;
                                break;
                            }
                        } else {
                            if (onceOfThePath.getFlightsSequence().get(onceOfThePath.getFlightsSequence().size() - 1).compareTo(ownFli) <= 0) {
                                onceOfThePath.addFlight(ownFli);
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
            if(flag == 0) {
                resultFlight.add(onceOfThePath);
            }
        }
        if(flag == 0)
            return resultFlight;
        else
            return null;
    }
}
