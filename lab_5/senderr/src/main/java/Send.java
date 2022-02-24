import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Send {
	private final static String QUEUE_NAME = "hello";

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		System.out.println("voilalala");
		factory.setHost("localhost");
		try (Connection connection = factory.newConnection();
				 Channel channel = connection.createChannel()) {
					channel.queueDeclare(QUEUE_NAME, false, false, false, null);
					String message = "bibi janam saanam.";// String.join(" ", args);
					channel.basicPublish("", "hello", null, message.getBytes());
					System.out.println(" [x] Sent '" + message + "'");

		}
	}
}
