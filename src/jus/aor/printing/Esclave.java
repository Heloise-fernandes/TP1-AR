package jus.aor.printing;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class Esclave extends Thread {

	private Socket soc;
	private Logger log;
	private Spooler spooler;
	private boolean occupe = false;
	
	
	public Esclave(Spooler s)
	{
		this.soc = null;
		this.log = Logger.getLogger("Jus.Aor.Printing.Esclave","jus.aor.printing.Esclave");
		this.log.setLevel(Level.INFO_2);
		this.spooler = s;
	}
	
	public Esclave(Socket p, Spooler s)
	{
		this.soc = p;
		this.log = Logger.getLogger("Jus.Aor.Printing.Esclave","jus.aor.printing.Esclave");
		this.log.setLevel(Level.INFO_2);
		this.spooler = s;
	}
	
	public void setSocket(Socket soc){
		this.soc = soc;
	}
	@Override
	public void run() {
		this.estOccupe();
		try {
			try {
				Notification ret = TCP.readProtocole(soc);
				JobKey jK = TCP.readJobKey(soc);
				String file = TCP.readData(soc);
				
				log.log(Level.INFO_1,"Server.notification = "+ ret);
				log.log(Level.INFO_1,"Server.key = "+ jK);
				
				this.spooler.add(new JobPrint(jK, file));
				
				
				if(ret == Notification.QUERY_PRINT)
				{
						TCP.writeProtocole(soc, Notification.REPLY_PRINT_OK);
						TCP.writeJobKey(soc, jK);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			catch(ArrayIndexOutOfBoundsException e){
				TCP.writeProtocole(soc,Notification.REPLY_UNKNOWN_NOTIFICATION);
			}catch(Exception e){
				TCP.writeProtocole(soc,Notification.REPLY_UNKNOWN_ERROR);
			}
		
		}catch (Exception e){
			e.printStackTrace();
			System.exit(-1);
		}
		this.nEstPlusOccupe();
	}
	
	private synchronized void estOccupe(){
		this.occupe = true;
	}
	
	private synchronized void nEstPlusOccupe(){
		this.occupe = false;
	}
	
	public synchronized boolean work()
	{
		return this.occupe;
	}

}
