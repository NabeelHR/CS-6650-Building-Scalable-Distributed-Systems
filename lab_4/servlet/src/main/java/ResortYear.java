/**
 * Class corresponding to a resort year in the POST resorts/resortID/seasons api call.
 */
public class ResortYear {
    private String year;

    public ResortYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Resort{" +
                "year='" + year + '\'' +
                '}';
    }
}
