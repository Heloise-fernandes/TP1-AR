package babySteps.O2_MultiThread;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Serveur {
	protected int port;
	protected ServerSocket serverTCPSoc;
	protected Boolean alive;
	protected int id;
	
	public Serveur()
	{
		this.port = 3000;
		this.alive = false;
		this.id = 0;
		
	}
	
	
	public void start()
	{
		this.alive = true;
		try 
		{
			
			this.serverTCPSoc = new ServerSocket(this.port);
			while(this.alive)
			{
				Socket client = this.serverTCPSoc.accept();
				this.id++;
				(new Spooler(client,this.id)).start();
				
			}	
			
		} catch (IOException e) {e.printStackTrace();}
		
	}
	
	public void listen(Socket client)
	{
		
		System.out.println("SERVEUR : reception");
		Tupple tupple = TCP_Actions.readFile(client);
	
		System.out.println("SERVEUR : resultat : "+tupple.getNotification());
		
		System.out.println("SERVEUR : resultat : "+tupple.getFOS());
		
		System.out.println("SERVEUR : envoie");
		TCP_Actions.writeFile(client, Notification.REPLY_PRINT_OK);
	
	}
	
	public static void main(String[] args)
	{
		Serveur s = new Serveur();
		s.start();
	}

}
