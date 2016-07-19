package innopolis.project.e4.converter;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by Xomak on 19.07.2016.
 */
public class FileConverter {
    private BufferedReader reader = null;
    private BufferedWriter writer = null;
    Random rand = new Random();

    private Date constructDate(String yearString, String monthString, String dayOfMonthString, String timeString) {
        int year = Integer.parseInt(yearString);
        int month = Integer.parseInt(monthString);
        int dayOfMonth = Integer.parseInt(dayOfMonthString);
        int hours;
        int minutes;
        if(timeString.length() < 4) {
            //hours = Integer.parseInt(timeString.substring(0, 0));
            //System.out.println(hours);
            //minutes = Integer.parseInt(timeString.substring(1, 2));
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

    private void prepareWriting() {
        try {
            writer = new BufferedWriter(new FileWriter("output.csv"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void writeToFile(int flightNumber, String from, String to, Date depTime, Date arrTime, float cost, int numberOfFreePlaces) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String output = String.format("\"%s\";\"%s\";\"%s\";\"%s\";\"%s\";\"%s\";\"%s\"", flightNumber, from, to, dateFormat.format(depTime), dateFormat.format(arrTime), cost, numberOfFreePlaces);
        try {
            writer.write(output);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void performReading() {
        try {
            reader = new BufferedReader(new FileReader("2008.csv"));
            String line;
            prepareWriting();
            for(int i = 0; (line = reader.readLine()) != null && i<100000; i++) {
                if(i == 0) continue;
                String[] components = line.split(",");
                Date departureDate = constructDate(components[0], components[1], components[2], components[5]);
                Date arrivalDate = constructDate(components[0], components[1], components[2], components[7]);
                if(arrivalDate == null || departureDate == null) {
                    continue;
                }
                String from = components[16];
                String to = components[17];
                int freePlaces = rand.nextInt(400);
                float cost = Math.abs(rand.nextFloat())*1000;
                String flightNumber = components[9];
                if(arrivalDate.compareTo(departureDate) < 0) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(arrivalDate);
                    cal.add(Calendar.DATE, 1);
                    arrivalDate = cal.getTime();
                }
                writeToFile(Integer.parseInt(flightNumber), from, to, departureDate, arrivalDate, cost, freePlaces);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FileConverter conv = new FileConverter();
        conv.performReading();
    }
}
