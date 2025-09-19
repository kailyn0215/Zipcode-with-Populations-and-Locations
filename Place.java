// Place.java
public class Place {
    protected String zipcode;
    protected String town;
    protected String state;

    public Place(String zipcode, String town, String state) {
        this.zipcode = (zipcode == null) ? "" : zipcode;
        this.town = (town == null) ? "" : town;
        this.state = (state == null) ? "" : state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getTown() {
        return town;
    }

    public String getState() {
        return state;
    }

    //@Override
    public String toString() {
        /*  If town and state are empty, show zipcode (fallback)
        if ((town == null || town.length() == 0) && (state == null || state.length() == 0)) {
            return zipcode;
        }
        if (state == null || state.length() == 0) {
            return town;
        }
        return town + ", " + state; */
        return town + ", " + state;
    }
}
