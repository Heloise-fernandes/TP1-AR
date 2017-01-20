package babySteps.O2_MultiThread;

import java.net.Socket;


public class Spooler extends Thread{

	Socket client;
	int idCo;
	public Spooler(Socket c, int i)
	{
		this.client = c;
		this.idCo = i;
	}
	
	
	@Override
	public void run() {
		//�couter
		this.listen();
		
	}

	
	private void listen()
	{
		
		System.out.println("SERVEUR : reception "+idCo);
		Tupple tupple = TCP_Actions.readFile(this.client);
	
		System.out.println("SERVEUR : resultat : "+tupple.getNotification());
		
		System.out.println("SERVEUR : resultat : "+tupple.getFOS());
		
		System.out.println("SERVEUR : envoie");
		TCP_Actions.writeFile(this.client, Notification.REPLY_PRINT_OK);
	
	}
}
