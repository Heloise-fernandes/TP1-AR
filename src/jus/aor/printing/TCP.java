/**
 * J<i>ava</i> U<i>tilities</i> for S<i>tudents</i>
 */
package jus.aor.printing;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.management.ManagementFactory;
import java.net.Socket;

import babySteps.O1_Sequentiel.Not;
import babySteps.O1_Sequentiel.Tupple;

/**
 * Classe de service fournissant toutes les interactions (read, write) en mode TCP.
 * @author Morat 
 */
class TCP{
	private static final int MAX_LEN_BUFFER = 1024;
	/**
	 * 
	 * @param soc the socket
	 * @param queryPrint the notification
	 * @throws IOException
	 */
	static void writeProtocole(Socket soc,  Notification queryPrint) throws IOException {
	// A COMPLETER
		DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
		dos.writeInt(queryPrint.ordinal());
		
	}
	/**
	 * 
	 * @param soc the socket 
	 * @return the notification
	 * @throws IOException
	 */
	static Notification readProtocole(Socket soc) throws IOException {
	// A COMPLETER
		DataInputStream dis = new DataInputStream(soc.getInputStream());
		return Notification.values()[dis.readInt()]; 
	}
	/**
	 * 
	 * @param soc the socket
	 * @param key the JobKey to write
	 * @throws IOException
	 */
	static void writeJobKey(Socket soc, JobKey key) throws IOException {
	// A COMPLETER TODO
		
		
		byte[] keyM =	key.marshal();
		int size = keyM.length;
		System.out.println("Clé : "+keyM.toString());
		DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
		dos.writeInt(size);
		dos.write(keyM);
	}
	/**
	 * 
	 * @param soc the socket
	 * @return the JobKey
	 * @throws IOException
	 */
	static JobKey readJobKey(Socket soc) throws IOException {
	// A COMPLETER TODO
		DataInputStream dis = new DataInputStream(soc.getInputStream());
		
		int size;
		size = dis.readInt();
		
		byte[] key = new byte[size];
		int i = 0;
		
		while(i<size)
		{
			key[i] = dis.readByte();
			i++;
		}
		System.out.println("Clé : "+key.toString());
		JobKey jK = new JobKey(key);
		return jK;
		
	}
	/**
	 * 
	 * @param soc the socket
	 * @param fis the input stream ti transfert
	 * @param len th len of the input stream
	 * @throws IOException
	 */
	static void writeData(Socket soc, InputStream fis, int len) throws IOException {
	// A COMPLETER
		byte[] buf = new byte[MAX_LEN_BUFFER];
        
        int n = 0;
		try 
		{
			DataOutputStream dos = new DataOutputStream(soc.getOutputStream());

			dos.writeLong(len);
			System.out.println("Taille : "+len);
			while ((n = fis.read(buf)) > 0) 
	        {
	        	dos.write(buf);
	        }
			
		} catch (IOException e) {e.printStackTrace();}
	}
	/**
	 * 
	 * @param soc th socket
	 * @return string data 
	 * @throws IOException
	 */
	static String readData(Socket soc) throws IOException {
	// A COMPLETER
		byte[] buf = new byte[MAX_LEN_BUFFER];
        
        int n = 0;
        String fichier = "";
		try 
		{
			DataInputStream dis = new DataInputStream(soc.getInputStream());
			
			long size = dis.readLong();
			System.out.println("Taille : "+size);
			
			while(n < (int)size)
			{
				n += dis.read(buf);
				System.out.println(n);
				fichier += new String(buf);
			}
			
			return fichier;
			
		} catch (IOException e) {e.printStackTrace();}
		
		return null;
	}
	/**
	 * 
	 * @param soc the socket
	 * @param jobs the JobState
	 * @throws IOException
	 */
	static void writeJobState(Socket soc,  JobState jobs) throws IOException {
	//----------------------------------------------------------------------------- A COMPLETER
	}
	/**
	 * 
	 * @param soc the socket 
	 * @return the JobState
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	static JobState readJobState(Socket soc) throws IOException, ClassNotFoundException {
	//----------------------------------------------------------------------------- A COMPLETER
		return null;
	}
}
