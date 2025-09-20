/**
 * File name: Driver01.java
 * Author: Kailyn Brown
 * Date: 9/19/2025
 * Purpose: driver class that reads zipcode and location data
 *          using LookupZip, builds ExpandableArray of Places, 
 *          + allows user to search for zipcodes
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver01 {
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length < 2) {
            System.out.println("Usage: java Driver01 uszipcodes.csv ziplocs.csv");
            return;
        }
        String file1 = args[0];
        String file2 = args[1];

        ExpandableArray<Place> places = LookupZip.readZipCodes(file1, file2);

        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print("zipcode: ");
            if (!in.hasNextLine()) { // EOF
                System.out.println("Good Bye!");
                break;
            }
            String z = in.nextLine().trim();
            if (z.length() == 0) {
                // continue prompting
                continue;
            }
            if (z.equals("00000")) {
                System.out.println("Good Bye!");
                break;
            }
            int idx = LookupZip.indexOfZip(places, z);
            if (idx == -1) {
                System.out.println("No such zipcode");
            } else {
                Place p = places.get(idx);
                System.out.println(p.toString());
            }
            System.out.println();
        }
        in.close();
    }
}
