
import java.io.*;
import java.net.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
* Skeleton socket client.
* Accepts host/port on command line or defaults to localhost/12031
* Then starts MAX_Threads and waits for them all to terminate before terminating main()
* @author Ian Gorton
*/
public class SocketCliMulti {

	static CyclicBarrier barrier;

	public static void main(String[] args) throws InterruptedException, BrokenBarrierException  {
		String hostName;
		final int MAX_THREADS = 500;
		int port;

		if (args.length == 2) {
			hostName = args[0];
			port = Integer.parseInt(args[1]);
		} else {
			hostName= null;
			port = 12031;  // default port in SocketServer
		}
		//initialization of barrier for the threads
		barrier = new CyclicBarrier(MAX_THREADS+1);

		long startTime;
		long endTime;
		long duration;
		startTime = System.nanoTime();
		//create and start MAX_THREADS SocketClientThread
		for (int i=0; i< MAX_THREADS; i++){
			new SocketClientThread(hostName, port, barrier).start();
		}

		//wait for all threads to complete
		barrier.await();
		endTime = System.nanoTime();
		duration = (endTime - startTime);
		duration = duration / 1000000;
		System.out.println("Total wall time taken was : " + duration);

	}
}