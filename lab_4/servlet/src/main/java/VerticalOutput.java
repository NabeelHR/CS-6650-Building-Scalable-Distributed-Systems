/**
 * Class that is used by Skier servlet post method.
 */
public class VerticalOutput {

    String seasonID;
    int totalVert;
    public VerticalOutput(String seasonID, int totalVert) {
        this.seasonID = seasonID;
        this.totalVert = totalVert;
    }

    public VerticalOutput(){
    }

    public void setSeasonID(String seasonID) {
        this.seasonID = seasonID;
    }
    public void setTotalVert(int totalVert) {
        this.totalVert = totalVert;
    }

    public int getTotalVert() {
        return totalVert;
    }

    @Override
    public String toString() {
        return "VerticalOutput{" +
                "seasonID='" + seasonID + '\'' +
                ", totalVert=" + totalVert +
                '}';
    }
}
