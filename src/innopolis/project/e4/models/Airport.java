package innopolis.project.e4.models;

/**
 * Created by Xomak on 14.07.2016.
 * Describes the airport entity
 */
public class Airport {
    private String name;

    public Airport(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Airport)) return false;

        Airport airport = (Airport) o;

        return name.equals(airport.name);

    }

    public String toString() {
        return "Airport<"+this.getName()+">";
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
