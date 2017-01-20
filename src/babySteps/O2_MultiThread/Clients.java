package babySteps.O2_MultiThread;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Clients 
{
	
	private String host = "localhost";
	private int port=3000;
	private boolean alive=true;
	
	public Clients()
	{
	}	
	
	
	public void envoyerFichier(String s)
	{
		try 
		{
			System.out.println("CLIENT : connexion");
			Socket serveur = new Socket(this.host,this.port);
			
			//get data
			FileInputStream fis = ouvrirFichier(s);
			long size = new File(s).length();
			
			//envoie
			envoieFichier(fis,serveur,size);
			
			//�couter
			listenNotification(serveur);
			
		} catch (IOException e) {e.printStackTrace();}
		
	}
	
	
	public void listenNotification(Socket s)
	{
		System.out.println("CLIENT : reception");
		Notification not = TCP_Actions.readNotification(s);
		
		if(not == Notification.REPLY_PRINT_OK){System.out.println("CLIENT : ok");}
		else{System.out.println("CLIENT : nope");}
	}
	
	
	public void envoieFichier(FileInputStream fis,Socket s,long size)
	{

        System.out.println("CLIENT : envoie");
        TCP_Actions.writeFile(s, Notification.QUERY_PRINT, fis, size);
   
	}
	
	public FileInputStream ouvrirFichier(String s) throws FileNotFoundException
	{
		return new FileInputStream(new File(s));
	}
	
	public static void main(String[] args)
	{
		Clients c = new Clients();
	
		String absolutePath = System.getProperty("user.dir");
		c.envoyerFichier(absolutePath+"\\test.txt");
		
	}
	

}
