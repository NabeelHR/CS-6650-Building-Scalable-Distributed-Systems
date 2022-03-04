public class Lift {


    private int time = -1;
    private int liftID = -1;
    private int waitTime = -1;
    private int skierID;

    public Lift(int time, int liftID, int waitTime) {
        this.time = time;
        this.liftID = liftID;
        this.waitTime = waitTime;
    }

    /**
     * Empty class in case variables are missing from JSON.
     */
    public Lift(){}

    @Override
    public String toString() {
        return "Lift{" +
                "time=" + time +
                ", liftID=" + liftID +
                ", waitTime=" + waitTime +
                '}';
    }

    public String sendJson() {
        return "{" +
                "time=" + time +
                ", liftID=" + liftID +
                ", waitTime=" + waitTime +
                ", skierID=" + skierID +
                '}';
    }

    /**
     *
     * @param skierID
     */
    public void setSkierID(int skierID){
        this.skierID = skierID;
    }

    public boolean isValidLift(){
        if (this.liftID < 0 || this.time < 0 || this.waitTime < 0) {
            return false;
        } else {
            return true;
        }
    }
}