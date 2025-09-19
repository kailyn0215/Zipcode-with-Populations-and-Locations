/**
 * File name: LocatedPlace.java
 * Author: Kailyn Brown
 * Date: 9/19/2025
 * Purpose: extends Place by adding latitude and longitude
 */

public class LocatedPlace extends Place {
    protected double latitude;
    protected double longitude;

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
        String base = super.toString();
        return base + " " + String.format("%.2f", latitude) + " " + String.format("%.2f", longitude);
    }
}
