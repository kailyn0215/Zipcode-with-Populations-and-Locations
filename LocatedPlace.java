// LocatedPlace.java
public class LocatedPlace extends Place {
    protected double latitude;
    protected double longitude;

    /**
     * Constructor order required by autograder:
     * (String, String, String, double, double)
     * I interpret this as (zipcode, town, state, latitude, longitude)
     */
    public LocatedPlace(String zipcode, String town, String state, double latitude, double longitude) {
        super(zipcode, town, state);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override // overrides place
    public String toString() {
        // Example: "Bryn Mawr, PA 40.02 -75.31"
        String base = super.toString();
        return base + " " + String.format("%.2f", latitude) + " " + String.format("%.2f", longitude);
    }
}
