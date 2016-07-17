package innopolis.project.e4.models;

import java.util.Date;

/**
 * Created by Xomak on 14.07.2016.
 * Stores user's related information
 */
public class User {

    private int id;
    private String name;
    private String passportData;
    private Date dateOfBirth;

    public User(final int id, final String name, final String passportData, final Date dateOfBirth) {
        this.id = id;
        this.name = name;
        this.passportData = passportData;
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return id == user.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
