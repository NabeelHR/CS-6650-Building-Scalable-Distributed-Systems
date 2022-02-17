public class Lift {


    private int time;
    private int liftID;
    private int waitTime;

    public Lift(int time, int liftID, int waitTime) {
        this.time = time;
        this.liftID = liftID;
        this.waitTime = waitTime;
    }

    @Override
    public String toString() {
        return "Lift{" +
                "time=" + time +
                ", liftID=" + liftID +
                ", waitTime=" + waitTime +
                '}';
    }
}
