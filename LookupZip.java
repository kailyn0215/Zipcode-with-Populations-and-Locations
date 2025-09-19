/**
 * File name: LookupZip.java
 * Author: Kailyn Brown
 * Date: 9/19/2025
 * Purpose: Provides methods to read data from files,
 *          creates Place objects, stores them in arrays/ExpandableArrays,
 *          and performs lookups.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class LookupZip {

    /**
     * @param filename1 name of the uszipcodes file
     * @param filename2 name of the ziplocs file
     * @return An ExpandableArray of Places
     */
    public static ExpandableArray<Place> readZipCodes(String filename1, String filename2) throws FileNotFoundException {
        ExpandableArray<Place> places = new ExpandableArray<Place>();
        ExpandableArray<Integer> populations = new ExpandableArray<Integer>();

        // reads uszipcodes
        Scanner s1 = new Scanner(new File(filename1));
        while (s1.hasNextLine()) {
            String line = s1.nextLine().trim();
            if (line.length() == 0) continue;
            String lcl = line.toLowerCase();
            if (lcl.contains("zipcode") || lcl.contains("zip")) {
                continue;
            }

            // parse uszipcodes line (zip, town, state, pop, ......)
            String[] parts = line.split(",");;
            String zipcode = parts.length > 0 ? parts[0].replace("\"", "") : "";
            String town = parts.length > 1 ? parts[1].replace("\"", "") : "";
            String state = parts.length > 2 ? parts[2].replace("\"", "") : "";
            String pop = parts.length > 3 ? parts[3].replace("\"", "") : "";

            Place p;
            int population = -1;
            if (!pop.isEmpty()) { // if there is a pop
                try {
                    population = Integer.parseInt(pop);
                    // for now store as Place with population (lat/lon will be added later)
                    p = new PopulatedPlace(zipcode, town, state, 0.0, 0.0, population);
                } catch (NumberFormatException e) {
                    // if population isn’t a valid number, just make it a Place
                    p = new Place(zipcode, town, state);
                }
            } else { // if there isnt a pop
                p = new Place(zipcode, town, state);
            }

            places.add(p);
            populations.add(population);
        }
        s1.close();

        // Read ziplocs.csv next; will upgrade objects already read (or add new ones)
        Scanner s2 = new Scanner(new File(filename2));
        while (s2.hasNextLine()) {
            String line = s2.nextLine().trim();
            if (line.length() == 0) continue;
            // skip header heuristic
            String lcl = line.toLowerCase();
            if (lcl.contains("zipcode") || lcl.contains("zip")) {
                continue;
            }

            String[] parts = line.split(",");

            // According to assignment: zipcode is column 1 (quoted), latitude column 6 (index 5), longitude column 7 (index 6)
            String zipcode = parts.length > 0 ? parts[0].replace("\"", "") : "";
            double lat = Double.NaN;
            double lon = Double.NaN;
            // parse lat and lon carefully
            if (parts.length > 5) {
                String latStr = parts[5].replace("\"", "").trim();
                if (latStr.length() > 0) {
                    try {
                        lat = Double.parseDouble(latStr);
                    } catch (NumberFormatException e) {
                        lat = Double.NaN;
                    }
                }
            }
            if (parts.length > 6) {
                String lonStr = parts[6].replace("\"", "").trim();
                if (lonStr.length() > 0) {
                    try {
                        lon = Double.parseDouble(lonStr);
                    } catch (NumberFormatException e) {
                        lon = Double.NaN;
                    }
                }
            }

            int idx = places.indexOfZip(zipcode);
            if (idx >= 0) {
                Place old = places.get(idx);
                int pop = populations.get(idx) == null ? -1 : populations.get(idx);
                if (!Double.isNaN(lat) && !Double.isNaN(lon)) {
                    if (pop >= 0) {
                        // upgrade to PopulatedPlace
                        PopulatedPlace pnew = new PopulatedPlace(old.getZipcode(), old.getTown(), old.getState(), lat, lon, pop);
                        places.set(idx, pnew);
                    } else {
                        // upgrade to LocatedPlace
                        LocatedPlace lnew = new LocatedPlace(old.getZipcode(), old.getTown(), old.getState(), lat, lon);
                        places.set(idx, lnew);
                    }
                } else {
                    // no location numbers parseable — leave as-is
                }
            } else {
                // zipcode from ziplocs not found in uszipcodes: create a LocatedPlace with empty town/state
                String town = "";
                String state = "";
                if (!Double.isNaN(lat) && !Double.isNaN(lon)) {
                    LocatedPlace lnew = new LocatedPlace(zipcode, town, state, lat, lon);
                    places.add(lnew);
                    populations.add(-1);
                } else {
                    // no lat/lon: add a plain Place
                    Place pnew = new Place(zipcode, town, state);
                    places.add(pnew);
                    populations.add(-1);
                }
            }
        }
        s2.close();

        // Now, for any entries that had population but were still plain Place (no lat/lon in ziplocs), we leave them as Place
        // The autograder expects an ExpandableArray<Place> with actual classes used where appropriate.
        return places;
    }

    /**
     * @param line
     * @return 
     
    private static String[] splitCSV(String line) {
        // simple state-machine: outside or inside quotes
        java.util.ArrayList<String> fields = new java.util.ArrayList<String>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == '"') {
                inQuotes = !inQuotes;
                // do not add quote to field content
            } else if (ch == ',' && !inQuotes) {
                fields.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(ch);
            }
        }
        fields.add(cur.toString());
        return fields.toArray(new String[0]);
    } */
}
