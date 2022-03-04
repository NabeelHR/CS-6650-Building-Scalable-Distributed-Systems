import java.io.*;
import java.net.*;
import java.util.Scanner;

public class GreetClient {
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;

	public void startConnection(String ip, int port) throws IOException {
		clientSocket = new Socket(ip, port);
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	public String sendMessage(String msg) throws IOException {
		out.println(msg);
		String resp = in.readLine();
		return resp;
	}

	public void stopConnection() throws IOException {
		in.close();
		out.close();
		clientSocket.close();
	}

	public static void main(String[] args) throws IOException {
		GreetClient client = new GreetClient();
		client.startConnection("127.0.0.1", 6666);

		while (true) {
			Scanner sc= new Scanner(System.in);    //System.in is a standard input stream
			System.out.print("Enter msg: ");
			String msg = sc.next();
			String response = client.sendMessage(msg);
			System.out.println("Response: " + response);
			if (response == null) {
				System.out.println("Server shut down");
				break;
			}
		}


	}
}