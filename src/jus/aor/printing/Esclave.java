package jus.aor.printing;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class Esclave extends Thread {

	private Socket soc;
	private Logger log;
	public Esclave(Socket p,Logger l)
	{
		this.soc = p;
		this.log = l;
		
	}
	@Override
	public void run() {
		try {
			try {
				Notification ret = TCP.readProtocole(soc);
				JobKey jK = TCP.readJobKey(soc);
				String file = TCP.readData(soc);
				
				log.log(Level.INFO_1,"Server.notification = "+ ret);
				log.log(Level.INFO_1,"Server.key = "+ jK);
				log.log(Level.INFO_1,"Server.data = "+file);
				
				if(ret == Notification.QUERY_PRINT)
				{
						TCP.writeProtocole(soc, Notification.REPLY_PRINT_OK);
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
	}

}
