/**
 * File name: Place.java
 * Author: Kailyn Brown
 * Date: 9/19/2025
 * Purpose: represents a basic place identified by
 *          zipcode, town, and state. superclass
 *          for LocatedPlace and PopulatedPlace.
 */

public class Place {
    protected String zipcode;
    protected String town;
    protected String state;

    public Place(String zipcode, String town, String state) {
        this.zipcode = (zipcode == null) ? "" : zipcode;
        this.town = (town == null) ? "" : town;
        this.state = (state == null) ? "" : state;
    }

    public String getZip() {
        return zipcode;
    }

    public String getTown() {
        return town;
    }

    public String getState() {
        return state;
    }

    public String toString() {
        return town + ", " + state;
    }
}
