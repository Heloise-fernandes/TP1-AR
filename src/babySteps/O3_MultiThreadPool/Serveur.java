package babySteps.O3_MultiThreadPool;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Serveur {
	protected int port;
	protected ServerSocket serverTCPSoc;
	protected Boolean alive;
	protected int id;
	
	private BlockingQueue<Socket> file;
	private Spooler[] pool;
	private int nbEsclave;
	
	public Serveur()
	{
		this.port = 3000;
		this.alive = false;
		this.id = 0;
		
		file = new LinkedBlockingQueue<Socket>();
		
		this.nbEsclave = 5;
		this.pool = new Spooler[this.nbEsclave];
		for(int i =0; i<this.nbEsclave;i++){this.pool[i] = new Spooler(this.file);}
		for(int i =0; i<this.nbEsclave;i++){this.pool[i].start();}
		
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
				try 
				{
					this.file.put(client);
				} catch (InterruptedException e) {e.printStackTrace();}
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
