package babySteps.O3_MultiThreadPool;
import java.io.FileOutputStream;


public class Tupple {
	
	private String fos;
	private Not not;
	
	public Tupple(String f , Not n)
	{
		this.fos = f;
		this.not = n;
	}
	
	public Not getNotification(){return this.not;}
	
	public String getFOS(){return this.fos;}
}
