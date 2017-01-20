package babySteps.O3_MultiThreadPool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;



public class TCP_Actions 
{
	public final static int MAX_BUFF = 1024;
	
	//---------------------------------------------------------------------------------------WRITE
	public static void writeFile(Socket c, Notification not)
	{
		try 
		{
			DataOutputStream dos = new DataOutputStream(c.getOutputStream());
			dos.writeInt(not.ordinal());
			
		} catch (IOException e) {e.printStackTrace();}
	}
	
	
	public static void writeFile(Socket c, Notification not, FileInputStream fis, long size)
	{
		byte[] buf = new byte[MAX_BUFF];
        
        int n = 0;
        System.out.println("Size = "+size);
		try 
		{
			DataOutputStream dos = new DataOutputStream(c.getOutputStream());
			//�crire notification
			dos.writeInt(not.ordinal());
			
			//�crire taille
			dos.writeLong(size);
			
			//�crire fichier
			while ((n = fis.read(buf)) > 0) 
	        {
				System.out.println(new String(buf));
	        	dos.write(buf);
	        }
			
		} catch (IOException e) {e.printStackTrace();}
	}
	
	
	//---------------------------------------------------------------------------------------READ
	public static Notification readNotification(Socket c)
	{
		try 
		{
			DataInputStream dis = new DataInputStream(c.getInputStream());
			return Notification.values()[dis.readInt()];
			
		} catch (IOException e) {e.printStackTrace();}
		return null;
	}
	
	public static Tupple readFile(Socket c)
	{
		byte[] buf = new byte[MAX_BUFF];
        
        int n = 0;
        String fichier = "";
		try 
		{
			DataInputStream dis = new DataInputStream(c.getInputStream());
			
			Notification not =  Notification.values()[dis.readInt()];
			long size = dis.readLong();
			
			System.out.println("Size = "+size);
			
			while(n<=size)
			{
				n += dis.read(buf);
				fichier += new String(buf);
			}
			
			return new Tupple(fichier, not);
			
		} catch (IOException e) {e.printStackTrace();}
		return null;
	}

}
