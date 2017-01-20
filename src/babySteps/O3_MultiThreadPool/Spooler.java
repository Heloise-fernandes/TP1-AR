package babySteps.O3_MultiThreadPool;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;


public class Spooler extends Thread{

	
	private BlockingQueue<Socket> file;
	private Boolean alive;
	
	public Spooler(BlockingQueue<Socket> f)
	{
		this.file = f;
		this.alive = true;
	}
	
	
	@Override
	public void run() {
		while(this.alive)
		{
			try 
			{
				this.listen(file.take());
			} catch (InterruptedException e) {e.printStackTrace();}
		}	
	}
	
	public void tuer(){this.alive=false;}

	
	
	
	private void listen(Socket client)
	{
		
		System.out.println("SERVEUR : reception ");
		Tupple tupple = TCP_Actions.readFile(client);
	
		System.out.println("SERVEUR : resultat : "+tupple.getNotification());
		
		System.out.println("SERVEUR : resultat : "+tupple.getFOS());
		
		System.out.println("SERVEUR : envoie");
		TCP_Actions.writeFile(client, Not.REPLY_PRINT_OK);
	
	}
}
