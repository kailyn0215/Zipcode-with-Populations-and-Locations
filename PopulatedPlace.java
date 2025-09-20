/**
 * File name: PopulatedPlace.java
 * Author: Kailyn Brown
 * Date: 9/19/2025
 * Purpose: extends LocatedPlace by adding population data.
 *          used when geographic + population info
 *          is known for a zipcode.
 */

public class PopulatedPlace extends LocatedPlace {
    protected int population;

    public PopulatedPlace(String zipcode, String town, String state, double latitude, double longitude, int population) {
        super(zipcode, town, state, latitude, longitude);
        this.population = population;
    }

    public int getPopulation() {
        return population;
    }

    @Override // overrides locatedplace
    public String toString() {
        if (population > 0) {
            String base = super.toString();
            return base + " " + population;
        }
        return null;
        
    }
}
