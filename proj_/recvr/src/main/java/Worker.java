import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import entity.Season;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Worker {

		private final static String QUEUE_NAME = "chany";
		private static Map<String, Season> map = new ConcurrentHashMap<>();
		private static int parseInt(String s) {
			return Integer.parseInt(s);
		}

		public static void retrieveMessageFromServer() throws Exception {
//			ConnectionFactory factory = new ConnectionFactory();
//			factory.setHost("localhost");

			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			factory.setUsername("guest");
			factory.setPassword("guest");


			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();

			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

			DeliverCallback deliverCallback = (consumerTag, delivery) -> {
				String msgStr = new String(delivery.getBody(), "UTF-8");
				System.out.println(" [x] Received '" + msgStr + "'");
				final String[] message = msgStr.split("/");
//				foo(message[0], seasons);
				Season season = new Season(parseInt(message[1]), parseInt(message[2]), parseInt(message[3]));
				map.put(message[0], season);
				System.out.println("received message");
			};
			boolean autoAck = true; // acknowledgment is covered below
			channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });
		}

	public static void main (String[] args) {
		int numThreads = 16;
		if (args.length != 0) {
			numThreads = Integer.parseInt(args[0]);
		}
		for (int i = 0; i < numThreads; i++) {
			Runnable thread = () -> {
				try {
					retrieveMessageFromServer();
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
			new Thread(thread).start();
		}
	}

	private static void doWork(String task) throws InterruptedException {
		for (char ch: task.toCharArray()) {
			if (ch == '.') Thread.sleep(2000);
		}
	}
	}
