package ac.ircnp;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import javax.imageio.ImageIO;

public class NetworkUtils {
	public static Decoder BASE64DECODER=Base64.getDecoder();
	
	public static BufferedImage getImage(String url)
	{
		if(url==null)
			return null;
		try {
			URL imgURL = new URL(url);
			URLConnection con=imgURL.openConnection();
			con.setDoOutput(true);
			return ImageIO.read(con.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static BufferedImage getBase64Image(String base64code)
	{
		if(base64code.startsWith("data:image/"))
			base64code=base64code.substring(22);
		try {
			ByteArrayInputStream imgBytes=new ByteArrayInputStream(BASE64DECODER.decode(base64code));
			return ImageIO.read(imgBytes);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean createFile(File file)
	{
		if(file.exists())
			return true;
		if(!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
