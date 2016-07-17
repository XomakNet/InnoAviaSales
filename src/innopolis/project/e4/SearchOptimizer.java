package innopolis.project.e4;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.sun.org.apache.xpath.internal.operations.String;
import innopolis.project.e4.models.Airport;
import innopolis.project.e4.models.Flight;
import innopolis.project.e4.providers.DataProvider;

import java.awt.*;
import java.util.Date;
import java.util.List;
import  java.util.ArrayList;
import java.util.Set;

/**
 * Created by Xomak on 15.07.2016.
 * Implements some quick path search alghoritm
 */
public class SearchOptimizer {
    enum Criterion {COST, TIME}
    private DataProvider provider;
    enum Color {WIGHT, LITLEBLACK, BLACK};
    private static ArrayList<Integer> timeIn, timeOut;
    private static ArrayList<Integer> colorType;
    private ArrayList<String> minPath;
    private static int timerNow = 0;

    public SearchOptimizer(final DataProvider provider) {
        this.provider = provider;
    }

    public void rebuildPathes() {

    }
//curr посещенные вершины
    public List<Flight> getPathesBetween(final Airport src,
                                         final Airport dst,
                                         final Date date,
                                         final Criterion criterion) {
    //run func dfs;
    }

    private List<Flight> dfsFlighting(Set<Airport> dest) {
        timeIn.add(dst, this.timerNow);
        this.timerNow++;
        colorType.add(dst, Color.LITLEBLACK);
        if(this.timerNow == 4) {
            System.out.println("Sorry path was not yes");
        }
        //Search my dest
        for (int i; i < minPath.size(); ++i) {
            if (colorType[dst].equals(Color.WIGHT)) {
                timeIn.add(dst, this.timerNow);
                this.timerNow++;
            }
        }
        PageAttributes.ColorType.add(dst, Color.BLACK);
        timeOut.add(dst, this.timerNow);
        this.timerNow++;
    }
}
