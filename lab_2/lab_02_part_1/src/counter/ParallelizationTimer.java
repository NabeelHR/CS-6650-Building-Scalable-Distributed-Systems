package counter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

public class ParallelizationTimer {
	private int myCount;
	final private int numThreads;
	Map<Integer, Integer> mt;

	public ParallelizationTimer(int n) {
		this.numThreads = n;
		mt = new Hashtable<>();
	}

	public class HashtableWorker extends Thread {
		private CountDownLatch latch;
		private int target;

		public HashtableWorker(CountDownLatch latch, int target) {
			super();
			this.latch = latch;
			this.target = target;
		}

		@Override
		public void run() {
			super.run();
			for (int i = 0; i < target; i++) {
				app();
			}
			latch.countDown();
		}

		public void app() {
			int randomNum = ThreadLocalRandom.current().nextInt(0, 1000 + 1);
			mt.put(randomNum, 7);
		}

	}

	public class Worker extends Thread {
		private CountDownLatch latch;

		public Worker(CountDownLatch latch) {
			super();
			this.latch = latch;
		}

		@Override
		public void run() {
			super.run();
			for (int i = 0; i < 10; i++) {
				incr();
			}
			latch.countDown();
		}

		synchronized public void incr() {
			myCount++;
		}

	}

	public void basicVector(int numElements) {
		List<Integer> vect = new Vector<>();
		for (int i = 0; i < numElements; i++) {
			vect.add(7);
		}
	}

	public void basicArrayList(int numElements) {
		List<Integer> vect = new ArrayList<>();
		for (int i = 0; i < numElements; i++) {
			vect.add(7);
		}
	}

	public void basicHashMap(int numElements) {
		Map<Integer, Integer> hm = new HashMap<>();
		for (int i = 0; i < numElements; i++) {
			int randomNum = ThreadLocalRandom.current().nextInt(0, 1000 + 1);
			hm.put(randomNum, 7);
		}
	}

	public void basicHashTable(int numElements) {
		Map<Integer, Integer> hm = new Hashtable<>();
		for (int i = 0; i < numElements; i++) {
			int randomNum = ThreadLocalRandom.current().nextInt(0, 1000 + 1);
			hm.put(randomNum, 7);
		}
	}

	// makes 100 threads to append same number of elements to a data structure
	public void multiHashTable(int numElements) throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(100);
		int target = numElements / 100;
		for (int i = 0; i < 100; i++) {
			HashtableWorker w = new HashtableWorker(latch, target);
			w.start();
		}
		latch.await();

	}

	// uses 50 threads to append same number of elements to a data structure
	public void concurrentHashMap(int numElements) throws InterruptedException {
		Map<Integer, Integer> hm = new ConcurrentHashMap<>();
		CountDownLatch latch = new CountDownLatch(50);
		int target = numElements / 50;
		for (int i = 0; i < 50; i++) {
			HashtableWorker w = new HashtableWorker(latch, target);
			w.start();
		}
		latch.await();

	}
	public void basicCounter () throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(numThreads);

		for (int i = 0; i < numThreads; i++) {
			Worker w = new Worker(latch);
			w.start();
		}
		latch.await();
	}


	public static void main(String[] args) throws InterruptedException {
		long startTime;
		long endTime;
		long duration;
		Scanner sc= new Scanner(System.in);
		System.out.print("Enter N: ");
		int n = sc.nextInt();


		int avg = 0;
		for (int i = 0; i < 50 ; i++) {
			startTime = System.nanoTime();

			//////////////change the following code for various steps of testing//////////////
			ParallelizationTimer watch = new ParallelizationTimer(n);
			watch.concurrentHashMap(n);
			//////////////////			//////////////			//////////////			//////////////////

			endTime = System.nanoTime();
			duration = (endTime - startTime);
			avg += duration / 1000000;
		}
		// taking avg of 50 results for better estimate
		System.out.println("Avg time taken was : " + avg / 50);
	}
}
