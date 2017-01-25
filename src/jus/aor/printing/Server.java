package jus.aor.printing;


import java.net.*;
import java.util.*;
import java.util.logging.Logger;

//import jus.aor.printing.Esclave.Slave;

/**
 * Représentation du serveur d'impression.
 * @author Morat
 */
public class Server {
	/** 1 second timeout for waiting replies */
	protected static final int TIMEOUT = 1000;
	protected static final int MAX_REPONSE_LEN = 1024;
	/** la taille de la temporisation */
	protected int backlog =10;
	/** le port de mise en oeuvre du service */
	protected int port=3000;
	/** le nombre d'esclaves maximum du pool */
	protected int poolSize = 10;
	/** le contrôle d'arret du serveur */
	protected boolean alive = false;
	/** le master server TCP socket */
	protected ServerSocket serverTCPSoc;
	/** le logger du server */
	Logger log = Logger.getLogger("Jus.Aor.Printing.Server","jus.aor.printing.Server");
	
	protected ArrayList<Esclave> spooler = new ArrayList<Esclave>();
	
	/**
	 * Construction du server d'impression
	 */
	public Server() {
		log.setLevel(Level.INFO_1);
		
		
	}
	/**
	 * le master thread TCP.
	 */
	private void runTCP(){
		try{
			
			Socket soc=null;
			serverTCPSoc = new ServerSocket(port, backlog);
			Notification protocole=null;
			log.log(Level.INFO_1,"Server.TCP.Started",new Object[] {port,backlog});
			
			Spooler s = new Spooler(3300);
			
			for(int i =1; i<=poolSize; i++){
				spooler.add(new Esclave(s));
			}
			
			while(alive) 
			{
				log.log(Level.INFO_1,"Server.TCP.Waiting");
				try{
					//accepter connexion
					soc = this.serverTCPSoc.accept();
					
					//=========================>Objectif 1
					
					/*Notification ret = TCP.readProtocole(soc);
					JobKey jK = TCP.readJobKey(soc);
					String file = TCP.readData(soc);
					
					log.log(Level.INFO_1,"Server.notification = "+ ret);
					log.log(Level.INFO_1,"Server.key = "+ jK);
					log.log(Level.INFO_1,"Server.data = "+file);
					
					if(ret == Notification.QUERY_PRINT)
					{
							TCP.writeProtocole(soc, Notification.REPLY_PRINT_OK);
							TCP.writeJobKey(soc, jK);
					}*/
					
					//=========================>Objectif 2
					
					Esclave e = null;
					for (Esclave esclave : spooler) {
						if(!esclave.work()){
							e = esclave;
							e.setSocket(soc);
							e.start();
							break;
						}
					}
					if (e==null){
						//Normalement c'est juste qu'il n'y a plus de place...
						TCP.writeProtocole(soc,Notification.REPLY_UNKNOWN_ERROR);
					}
					
					
				}catch(SocketException e){
						// socket has been closed, master serverTCP will stop.
				}catch(ArrayIndexOutOfBoundsException e){
					TCP.writeProtocole(soc,Notification.REPLY_UNKNOWN_NOTIFICATION);
				}catch(Exception e){
					TCP.writeProtocole(soc,Notification.REPLY_UNKNOWN_ERROR);
				}
			}
			log.log(Level.INFO_1,"Server.TCP.Stopped");
			serverTCPSoc.close();
		}catch (Exception e){
			e.printStackTrace();
			System.exit(-1);
		}
	}
	protected void setBacklog(int backlog) {this.backlog=backlog;}
	protected void setport(int port) {this.port=port;}
	protected void setPoolSize(int poolSize) { this.poolSize=poolSize;}
	/**
	 * @param f
	 * @see jus.aor.printing.Spooler#impressionTimeOfSize(jus.util.Formule)
	 */
	//public void impressionTimeOfSize(Formule f){spooler.impressionTimeOfSize(f);}
	/**
	 * 
	 */
	void start(){
		// A COMPLETER
		this.alive = true;
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				runTCP();
				
			}
		});
		t.start();
		
		
		
	}
	/**
	 * 
	 */
	public void stop(){
		//A COMPLETER
		this.alive = false;
	}
	/**
	 * 
	 * @param args
	 */
	public static void main (String args[]) { 
      new ServerGUI(new Server()); 
	}
}