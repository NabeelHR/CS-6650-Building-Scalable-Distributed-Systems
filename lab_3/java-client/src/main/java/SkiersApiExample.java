import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
		import io.swagger.client.ApiResponse;
		import io.swagger.client.api.SkiersApi;

import java.util.concurrent.CountDownLatch;

public class SkiersApiExample {
	public static class Pawn extends Thread {

		private CountDownLatch latch;

		public Pawn(CountDownLatch latch) {
			super();
			this.latch = latch;
		}

		@Override
		public void run() {
			super.run();
			long startTime;
			long endTime;
			long duration;
			startTime = System.nanoTime();
			pingServer();
			endTime = System.nanoTime();
			duration = (endTime - startTime) / 1000000;
			System.out.println(duration);
			latch.countDown();
		}
	}

	private static void pingServer() {
		String basePath = "http://localhost:8080/slowpoke/";
		SkiersApi apiInstance = new SkiersApi();
		ApiClient client = apiInstance.getApiClient();
		client.setBasePath(basePath);

		// Example for the GET
		Integer resortID = 56; // Integer | ID of the resort
		String seasonID = "56"; // String | ID of the season
		String dayID = "56"; // String | ID of the day
		Integer skierID = 56; // Integer | ID of the skier
		try {
			ApiResponse res = apiInstance.getSkierDayVerticalWithHttpInfo(resortID, seasonID, dayID, skierID);
			Integer verticalResult = apiInstance.getSkierDayVertical(resortID, seasonID, dayID, skierID);
		} catch (ApiException e) {
			System.err.println("Exception when calling SkiersApi#getSkierDayVertical");
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws InterruptedException {


		long startTime;
		long endTime;
		long duration;
		startTime = System.nanoTime();
		int numThreads = 500;
		CountDownLatch latch = new CountDownLatch(numThreads);

		for (int i = 0; i < numThreads; i++) {
			Pawn w = new Pawn(latch);
			w.start();
		}
		latch.await();

		endTime = System.nanoTime();
		duration = (endTime - startTime) / 1000000;
		System.out.println("time taken was : " + duration + "ms");

	}
}
