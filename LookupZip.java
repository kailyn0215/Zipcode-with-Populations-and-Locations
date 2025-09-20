/**
 * File name: LookupZip.java
 * Author: Kailyn Brown
 * Date: 9/19/2025
 * Purpose: has method to read data from files,
 *          creates Place objects, stores them in ExpandableArrays,
 *          and performs lookups.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LookupZip {

    /**
     * Reads two CSV files and returns an ExpandableArray of Places.
     * 
     * @param filename1 name of the uszipcodes file
     * @param filename2 name of the ziplocs file
     * @return ExpandableArray<Place> containing Place, LocatedPlace, or PopulatedPlace objects
     */
    public static ExpandableArray<Place> readZipCodes(String filename1, String filename2) throws FileNotFoundException {
        ExpandableArray<Place> places = new ExpandableArray<>();
        ExpandableArray<Integer> populations = new ExpandableArray<>();

        // --- Read uszipcodes.csv ---
        Scanner s1 = new Scanner(new File(filename1));
        while (s1.hasNextLine()) {
            String line = s1.nextLine().trim();
            if (line.isEmpty()) continue;
            String lcl = line.toLowerCase();
            if (lcl.contains("zipcode") || lcl.contains("zip")) continue; // skip header

            String[] parts = line.split(",");
            String zipcode = parts.length > 0 ? parts[0].replace("\"", "") : "";
            String town = parts.length > 1 ? parts[1].replace("\"", "") : "";
            String state = parts.length > 2 ? parts[2].replace("\"", "") : "";
            String popStr = parts.length > 3 ? parts[3].replace("\"", "") : "";
            int population = -1;

            try {
                population = Integer.parseInt(popStr);
            } catch (NumberFormatException e) {
                population = -1; // invalid or empty population
            }

            Place p;
            if (population > 0) {
                // Only create PopulatedPlace if population > 0
                p = new PopulatedPlace(zipcode, town, state, 0.0, 0.0, population);
            } else {
                // Otherwise create normal Place
                p = new Place(zipcode, town, state);
            }

            places.insert(p);

        }
        s1.close();

        // read ziplocs.csv 
        Scanner s2 = new Scanner(new File(filename2));
        while (s2.hasNextLine()) {
            String line = s2.nextLine().trim();
            if (line.isEmpty()) continue;
            String lcl = line.toLowerCase();
            if (lcl.contains("zipcode") || lcl.contains("zip")) continue; // skip header

            String[] parts = line.split(",");
            String zipcode = parts.length > 0 ? parts[0].replace("\"", "") : "";
            double lat = Double.NaN;
            double lon = Double.NaN;

            if (parts.length > 5) {
                String latStr = parts[5].replace("\"", "").trim();
                if (!latStr.isEmpty()) {
                    try { lat = Double.parseDouble(latStr); } catch (NumberFormatException e) { lat = Double.NaN; }
                }
            }
            if (parts.length > 6) {
                String lonStr = parts[6].replace("\"", "").trim();
                if (!lonStr.isEmpty()) {
                    try { lon = Double.parseDouble(lonStr); } catch (NumberFormatException e) { lon = Double.NaN; }
                }
            }

            int idx = indexOfZip(places, zipcode);
            if (idx >= 0) {
                Place old = places.get(idx);
                if (!Double.isNaN(lat) && !Double.isNaN(lon)) {
                    if (old instanceof PopulatedPlace) {
                        PopulatedPlace pp = (PopulatedPlace) old;
                        // Only upgrade to PopulatedPlace if population > 0
                        if (pp.getPopulation() > 0) {
                            PopulatedPlace pnew = new PopulatedPlace(pp.getZip(), pp.getTown(), pp.getState(), lat, lon, pp.getPopulation());
                            places.set(pnew, idx);
                        } else {
                            LocatedPlace lnew = new LocatedPlace(pp.getZip(), pp.getTown(), pp.getState(), lat, lon);
                            places.set(lnew, idx);
                        }
                    } else if (old instanceof Place) {
                        LocatedPlace lnew = new LocatedPlace(old.getZip(), old.getTown(), old.getState(), lat, lon);
                        places.set(lnew, idx);
                    }
                }
            }
        }
        s2.close();

        // keep getting [0] instead of [] in autograder so trying to fix :( NOT HELPINGsdjkhfslkfjsl
        for (int i = 0; i < places.size(); i++) {
        Place p = places.get(i);
        if (p instanceof PopulatedPlace) {
            PopulatedPlace pp = (PopulatedPlace) p;
            if (pp.getPopulation() < 0) {
                // Downgrade to LocatedPlace
                LocatedPlace lnew = new LocatedPlace(pp.getZip(), pp.getTown(), pp.getState(), pp.getLatitude(), pp.getLongitude());
                places.set(lnew, i);
            }
        }
    }
        return places;
    }

    public static int indexOfZip(ExpandableArray<Place> places, String zipcode) {
        for (int i = 0; i < places.size(); i++) {
            Place p = places.get(i);
            if (p.getZip().equals(zipcode)) return i;
        }
        return -1;
    }

    /** Lookup method using ExpandableArray */
    public static Place lookupZip(ExpandableArray<Place> places, String zip) {
        int idx = indexOfZip(places, zip);
        if (idx >= 0) return places.get(idx);
        return null;
    }
}
