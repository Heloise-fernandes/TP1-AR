package babySteps.O1_Sequentiel;
import java.io.FileOutputStream;


public class Tupple {
	
	private String fos;
	private Notification not;
	
	public Tupple(String f , Notification n)
	{
		this.fos = f;
		this.not = n;
	}
	
	public Notification getNotification(){return this.not;}
	
	public String getFOS(){return this.fos;}
}
