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
    Stack<Airport> minimalPathName = new Stack<>();
    LinkedList<Stack<Airport>> resultFli = new LinkedList<Stack<Airport>>();
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
        minimalPathName.push(src);
        dfsFlighting(src, dst);
        //run func dfs;
        return null;
    }

    private void dfsFlighting(final Airport src,
                                        final Airport dst) {
        List<Airport> neighbours = null;//getAirportsMates(src);

        for(int i = 0; i < neighbours.size(); i++) {
            minimalPathName.push(neighbours.get(i));
            if(neighbours.get(i).equals(dst)) {
                resultFli.push(minimalPathName);
                minimalPathName.pop();
                break;
            }
            if(MaxTrevelingCount == minimalPathName.size()) {
                if(neighbours.contains(dst)) {
                    minimalPathName.pop();
                    minimalPathName.push(dst);
                    resultFli.push(minimalPathName);
                    minimalPathName.pop();
                    break;
                }
                else {
                    minimalPathName.pop();
                    break;
                }
            }
            else{
                dfsFlighting(neighbours.get(i), dst);
            }
        }
    }

    public void getFlight(final Date date,
                          final Criterion criterion) {
        for (int i = 0; i < resultFli.size(); i++) {
        }
    }
}
