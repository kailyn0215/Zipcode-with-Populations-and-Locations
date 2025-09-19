// PopulatedPlace.java
public class PopulatedPlace extends LocatedPlace {
    protected int population;

    /**
     * Constructor order required by autograder:
     * (String, String, String, double, double, int)
     * (zipcode, town, state, latitude, longitude, population)
     */
    public PopulatedPlace(String zipcode, String town, String state, double latitude, double longitude, int population) {
        super(zipcode, town, state, latitude, longitude);
        this.population = population;
    }

    public int getPopulation() {
        return population;
    }

    @Override // overrides locatedplace
    public String toString() {
        String base = super.toString();
        return base + " " + population;
    }
}
