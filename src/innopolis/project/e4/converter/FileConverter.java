package innopolis.project.e4.converter;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Iterator;

/**
 * Created by Xomak on 19.07.2016.
 */
public class FileConverter {
    private BufferedReader reader = null;
    private HashMap<String, BufferedWriter> writers = new HashMap<>();
    private BufferedWriter writer = null;
    private String outputPath;
    private String inputPath;
    private int neededObjects;
    private Random rand = new Random();

    public FileConverter(String inputPath, String outputPath, int neededObjects) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.neededObjects = neededObjects;
    }

    private Date constructDate(String yearString, String monthString, String dayOfMonthString, String timeString) {
        int year = Integer.parseInt(yearString);
        int month = Integer.parseInt(monthString);
        int dayOfMonth = Integer.parseInt(dayOfMonthString);
        int hours;
        int minutes;
        if(timeString.length() < 4) {
            return null;
        }
        else {
            hours = Integer.parseInt(timeString.substring(0, 2));
            minutes = Integer.parseInt(timeString.substring(2, 4));
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth, hours, minutes);
        return calendar.getTime();
    }

    private void closeWriters() {
        Iterator<BufferedWriter> writersIterator = writers.values().iterator();
        while(writersIterator.hasNext()) {
            try {
                writersIterator.next().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            writersIterator.remove();
        }
    }


    private BufferedWriter getWriter(String airline) {
        if (writers.containsKey(airline)) {
            return writers.get(airline);
        } else {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(String.format("%s/%s.csv", outputPath, airline)));
                writers.put(airline, writer);
                return writer;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void writeToFile(int flightNumber, String from, String to, Date depTime, Date arrTime, float cost, int numberOfFreePlaces, String airline) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String output = String.format("\"%s\";\"%s\";\"%s\";\"%s\";\"%s\";\"%s\";\"%s\"", flightNumber, from, to, dateFormat.format(depTime), dateFormat.format(arrTime), cost, numberOfFreePlaces);
        try {
            BufferedWriter writer = this.getWriter(airline);
            if(writer != null) {
                writer.write(output);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void performReading() {
        try {
            reader = new BufferedReader(new FileReader(this.inputPath));
            String line;
            for(int i = 0; (line = reader.readLine()) != null && (i<this.neededObjects || this.neededObjects == -1); i++) {
                if(i == 0) continue;
                //System.out.println(line);
                String[] components = line.split(",");
                Date departureDate = constructDate(components[0], components[1], components[2], components[5]);
                Date arrivalDate = constructDate(components[0], components[1], components[2], components[7]);
                if(arrivalDate == null || departureDate == null) {
                    continue;
                }
                String from = components[16];
                String to = components[17];
                String airline = components[8];
                int freePlaces = rand.nextInt(400);
                float cost = Math.abs(rand.nextFloat())*1000;
                String flightNumber = components[9];
                if(arrivalDate.compareTo(departureDate) < 0) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(arrivalDate);
                    cal.add(Calendar.DATE, 1);
                    arrivalDate = cal.getTime();
                }
                writeToFile(Integer.parseInt(flightNumber), from, to, departureDate, arrivalDate, cost, freePlaces, airline);
            }
            closeWriters();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FileConverter conv = new FileConverter("2008.csv", "output", -1);
        conv.performReading();
    }
}
