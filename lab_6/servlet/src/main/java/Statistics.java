/**
 * Class representing the statistic for a certain operation.
 */
public class Statistics {

    String URL;
    String operation;
    int mean;
    int max;

    public Statistics(String url, String operation, int mean, int max) {
        this.URL = url;
        this.operation = operation;
        this.mean = mean;
        this.max = max;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "URL='" + URL + '\'' +
                ", operation='" + operation + '\'' +
                ", mean=" + mean +
                ", max=" + max +
                '}';
    }
}
