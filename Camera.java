import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO; 
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Camera 
{
	private File old = null;
	private File ny = null;
	private String saveDir = "";
	private int nbrOfPictures = 0;
	private final int SLOW = 10000;
	private final int FAST = 1000;
	private int time;
	private SimpleDateFormat ft1 = new SimpleDateFormat("yyyy.MM.dd"); // ett datumformat
	private SimpleDateFormat ft2 = new SimpleDateFormat("HH.mm.ss"); // ett datumformat
		      

	public Camera()
	{
		saveDir = "/home/pi/Pictures/";
	}
	public Camera (String saveDir)
	{
		this.saveDir = saveDir;
		
	}

	public void start() throws Exception
	{
		time = SLOW;
		if (old == null)
		{
			Process p1 = Runtime.getRuntime().exec("raspistill -e png -o " + saveDir + (++nbrOfPictures)+".png");
			p1.waitFor();	
			File old = new File(saveDir + nbrOfPictures+".png");
			Thread.sleep(time);	
		}
		while (true) 
		{
			Process p2 = Runtime.getRuntime().exec("raspistill -e png -o " + saveDir + (++nbrOfPictures)+".png");
			p2.waitFor();
			File ny =  new File(saveDir + nbrOfPictures+".png");
			if (isDifferent(old,ny))
			{
				upload(ny);
				time = FAST;
			}
			else
				time = SLOW;
			Process p3 = Runtime.getRuntime().exec("rm "+ saveDir + (nbrOfPictures-1));
			p3.waitFor();
			old = ny;
			Thread.sleep(time);
		
		}
	}
	
	private boolean isDifferent(File file1, File file2)
	{
		double len1MB = (double)file1.length()/(1024*1024);
		double len2MB = (double)file2.length()/(1024*1024);
		return Math.abs(len1MB-len2MB)*20 >= 0.05;
	}
	
	private void upload(File file) throws Exception
	{
		BufferedImage img = ImageIO.read(file);
		Date date = new Date(); // ett objekt med aktuellt datum
		String yyyymmdd = ft1.format(date);
		String hhmmss = ft2.format(date);
		String ID = "" + nbrOfPictures;
		push(database, new ImageData(ID,yyyymmdd,hhmmss,img));
	
		
	}


}
