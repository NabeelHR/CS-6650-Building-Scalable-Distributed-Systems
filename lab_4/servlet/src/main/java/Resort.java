/**
 * Class that stores resort information.
 */
public class Resort {

    String resortName;
    int resortID;
    public Resort(String resortName, int resortID) {
        this.resortName = resortName;
        this.resortID = resortID;
    }

    @Override
    public String toString() {
        return "Resort{" +
                "resortName='" + resortName + '\'' +
                ", resortID=" + resortID +
                '}';
    }
}
