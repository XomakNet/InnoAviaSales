package innopolis.project.e4.models;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by Xomak on 14.07.2016.
 * Stores user's related information
 */
public class User {

    private int id;
    private String name;
    private String passportData;
    private LocalDateTime dateOfBirth;
    private String separator = ";";

    public User(final int id, final String name, final String passportData, final LocalDateTime dateOfBirth) {
        this.id = id;
        this.name = name;
        this.passportData = passportData;
        this.dateOfBirth = dateOfBirth;
    }

    public int getID(){
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return id == user.id;

    }

    @Override
    public String toString(){
        String sResult = "";
        sResult += id + separator;
        sResult += name + separator;
        sResult += passportData.toString() + separator;
        sResult += dateOfBirth.toString();
        return  sResult;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
