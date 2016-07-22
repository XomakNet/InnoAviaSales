package innopolis.project.e4;

import innopolis.project.e4.providers.CsvDataProvider;

public class Main {

    public static void main(String[] args) {
        CsvDataProvider dataProvider = new CsvDataProvider("C:\\Users\\regis\\AppData\\Local\\Temp\\data_provider_test1604909111510793203");
        System.out.println(dataProvider.getAllAirports());
        System.out.println(dataProvider.getAirportsMates());
    }
}
