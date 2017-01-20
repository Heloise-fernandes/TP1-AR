package babySteps.O1_Sequentiel;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Serveur {
	protected int port;
	protected ServerSocket serverTCPSoc;
	protected Boolean alive;
	
	public Serveur()
	{
		this.port = 3000;
		this.alive = false;
		
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
				
				//�couter
				this.listen(client);
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
		TCP_Actions.writeFile(client, Not.REPLY_PRINT_OK);
	
	}
	
	public static void main(String[] args)
	{
		Serveur s = new Serveur();
		s.start();
	}

}
