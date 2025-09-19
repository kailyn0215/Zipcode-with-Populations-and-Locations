// Driver01.java
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Driver for HW01. Usage:
 *   java Driver01 uszipcodes.csv ziplocs.csv
 *
 * Prompts user for zipcodes until "00000" is entered (then prints "Good Bye!" and exits).
 *
 * NOTE: Do not wrap main in a try/catch (per autograder instructions). This method will throw
 * an exception if files are missing.
 */
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
            int idx = places.indexOfZip(z);
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
