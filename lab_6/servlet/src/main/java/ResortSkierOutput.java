/**
 * Class for this api link output.
 * /resorts/{resortID}/seasons/{seasonID}/day/{dayID}/skiers
 */
public class ResortSkierOutput {
    String time;
    int numSkiers;

    public ResortSkierOutput(String time, int numSkiers) {
        this.time = time;
        this.numSkiers = numSkiers;
    }
}
