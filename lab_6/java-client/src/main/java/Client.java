import com.google.gson.Gson;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;
import org.threeten.bp.Duration;
import org.threeten.bp.Instant;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;



public class Client implements Runnable {
    public static class Logger implements Runnable{

        @Override
        public void run() {
            int i = 0;
            while (true) {
                i++;
                try {
                    String log = logs.take();
                    System.out.println(log);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    static Vector<Integer> statusCodes = new Vector<>();
    private static BlockingQueue<String> logs;
    static {
        logs = new LinkedBlockingQueue<>();
    }

    int startID;
    int endID;
    int numThreads;
    int numLifts;
    int numRuns;
    int startTime;
    int endTime;
    CountDownLatch countDownLatch;
    SkiersApi apiInstance = new SkiersApi();
    ApiClient client = apiInstance.getApiClient();



    public Client(int startID, int endID, int numThreads, int numLifts, int numRuns, int startTime, int endTime, String basepath, CountDownLatch countDownLatch) {
        this.startID = startID;
        this.endID = endID;
        this.numThreads = numThreads;
        this.numLifts = numLifts;
        this.numRuns = numRuns;
        this.startTime = startTime;
        this.endTime = endTime;
        this.countDownLatch = countDownLatch;
        client.setBasePath(basepath);
    }


    public Vector getStatusCodes() {
        return statusCodes;
    }

    @Override
    public void run() {
//        System.out.println("start tim .." +  startTime + " " + this.endID + " " +  this.startID);
        int i = 0;
        int skierID;
        int lift;
        int waitTime;
        int time;
        Random random = new Random();
        ApiResponse res;


        LiftRide liftRide = new LiftRide();
        Gson gson = new Gson();
        String request;
//        System.out.println(String.format("->%d %d %d %d", i, numRuns, this.endID, this.startID));

        while(i < numRuns * (this.endID - this.startID)) {
            i++;

            skierID = random.nextInt(endID - startID) + startID;
            lift = random.nextInt(numLifts);
            waitTime = random.nextInt(10);
            time = random.nextInt(this.endTime - this.startTime) + this.startTime;

            liftRide.setLiftID(lift);
            liftRide.setWaitTime(waitTime);
            liftRide.setTime(time);


            for(int j = 0; j < 2; j++) {
                try {
                    long startTimer;
                    long endTimer;
                    long duration;
                    startTimer = System.nanoTime();
                    res = apiInstance.writeNewLiftRideWithHttpInfo(liftRide, 100, "123", "24", skierID);
                    endTimer = System.nanoTime();
                    duration = (endTimer - startTimer) / 1000000;
                    logs.put(String.format("%d, %d", duration, this.startTime));
                    statusCodes.add(res.getStatusCode());
//                numRequests.incrementAndGet();
                } catch (ApiException e) {
                    e.printStackTrace();
                    if(j == 1) {
                        statusCodes.add(400);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //            res = apiInstance.getSkierDayVertical();

        }

//        System.out.println("I= " + i);
        this.countDownLatch.countDown();
        return;
    }


    /**
     * Input the parameters with the name followed by a space.
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("beginn...");

        int numThreads = 16;
        int numSkiers = 1024;
        int numLifts = 20;
        int numRuns = 16;
        String basePath = "http://44.203.130.37:8080/tommy/";

        for(int i = 0; i < args.length; i+=2) {
            if(args[i].equalsIgnoreCase("numThreads")) {
                numThreads = Integer.parseInt(args[i + 1]);
            } else if( args[i].equalsIgnoreCase("numSkiers")) {
                numSkiers = Integer.parseInt(args[i + 1]);
            } else if( args[i].equalsIgnoreCase("numLifts")) {
                numLifts = Integer.parseInt(args[i + 1]);
            } else if( args[i].equalsIgnoreCase("numRuns")) {
                numRuns = Integer.parseInt(args[i + 1]);
            } else if (args[i].equalsIgnoreCase("IPAddress")) {
                basePath = args[i + 1];
            }
        }

        // Error checking
        if(numThreads < 1 || numThreads > 1024) {
            System.out.println("Thread input error");
            return;
        } else if (numSkiers < 1 || numSkiers > 100000) {
            System.out.println("Skier input error");
            return;
        } else if (numLifts < 5 || numLifts > 60) {
            System.out.println("Lift input error");
            return;
        } else if (numRuns < 1 || numRuns > 20) {
            System.out.println("Run input error");
            return;
        }
//        basePath = basePath + ipAddress + ":8080/servlet_war_exploded";

        long startTime;
        long endTime;
        long duration;

        ExecutorService es = Executors.newFixedThreadPool(numThreads);
//        es.execute(new Logger());

        startTime = System.nanoTime();

        //Start up
        int startupNumThreads = numThreads/ 4 + 1;
        int skierInterval = numSkiers / startupNumThreads;
        CountDownLatch startupCountdown = new CountDownLatch((int) (startupNumThreads * 0.2));
        Client stats = new Client(0,0,0,0,0,0,0, "", startupCountdown);

        for(int i = 0; i < startupNumThreads; i++) {

            es.execute(new Client(skierInterval * i, (skierInterval * i) + skierInterval - 1, startupNumThreads, numLifts, (int) (numRuns * 0.2), 0, 90, basePath, startupCountdown));

        }
        try {
            startupCountdown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Peak
//        es = Executors.newFixedThreadPool(numThreads);
        int peakSkierInterval = numSkiers / numThreads;
        CountDownLatch peakCountdown = new CountDownLatch((int) (numThreads * 0.2));
        for(int i = 0; i < numThreads; i++) {

            es.execute(new Client(peakSkierInterval * i, (peakSkierInterval * i) + peakSkierInterval - 1, numThreads, numLifts, (int) (numRuns * 0.6), 65, 420, basePath, peakCountdown));

        }

        try {
            peakCountdown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        System.out.println("Skier interval: "+ skierInterval + " startup num threads: " + startupNumThreads );


        //End
//        stats.resetAtomicInteger();
        int endNumThreads = numThreads / 10;
        if (endNumThreads < 1) {
            endNumThreads = 1;
        }
        CountDownLatch cooldownCountdown = new CountDownLatch((int) (endNumThreads * 0.2));

        int endSkierInterval = numSkiers / endNumThreads;
        for(int i = 0; i < endNumThreads; i++) {

            es.execute(new Client(endSkierInterval * i, (endSkierInterval * i) + endSkierInterval, endNumThreads, numLifts, (int) (numRuns * 0.1), 361, 420, basePath, cooldownCountdown));

        }

        es.shutdown();
        try {
            boolean finished = es.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Reset atomic integer
//        stats.resetAtomicInteger();




        Instant end = Instant.now();
        endTime = System.nanoTime();

        duration = (endTime - startTime) / 1000000;

        System.out.println(stats.getStatusCodes().size());
        Vector v = stats.getStatusCodes();
        int numberOf201 = 0;
        for(int i = 0; i < v.size(); i++ ) {
            if(v.get(i).equals(201)) {
                numberOf201 ++;
            }
        }
        System.out.println("Correct response codes: " + numberOf201);
        System.out.println("Incorrect response codes: " + (v.size() - numberOf201));
        System.out.println("duration: " + duration + "ms");
        System.out.println(basePath);


    }


}
