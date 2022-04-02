package ac.ircnp;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.MessageFormat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NuDat {
	static private String levelSchemeBaseURL="https://www.nndc.bnl.gov/nudat3/NuDatBandPlotServlet?nucleus={0}&unc=nds";
	static private String bandParametersBaseURL="https://www.nndc.bnl.gov/nudat3/NuDatBandParametersServlet?nucleus={0}";
	
	static public BufferedImage getLevelScheme(String nucleus)
	{
		String url=MessageFormat.format(levelSchemeBaseURL,nucleus);
		Document doc;
		String imgBase64Code=null;
		try {
			doc = Jsoup.connect(url).get();
			if (doc.html().isEmpty())
				return null;
			Elements imgs=doc.select("img");
			for(Element e:imgs)
				imgBase64Code=e.attr("src");
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
		return NetworkUtils.getBase64Image(imgBase64Code);
	}
	
	static public BufferedImage[] getBandParameters(String nucleus)
	{
		String url=MessageFormat.format(bandParametersBaseURL,nucleus);
		Document doc;
		BufferedImage bands[]=null;
		try {
			doc = Jsoup.connect(url).get();
			if (doc.html().isEmpty())
				return null;
			Elements imgs=doc.select("img");
			bands=new BufferedImage[imgs.size()];
			int idx=0;
			for(Element e:imgs)
			{
				bands[idx]=NetworkUtils.getBase64Image(e.attr("src"));
				++idx;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return bands;
	}
	
	static public BufferedImage getBandParameters(String nucleus,int idx)
	{
		String url=MessageFormat.format(bandParametersBaseURL,nucleus);
		Document doc;
		String imgBase64Code=null;
		try {
			doc = Jsoup.connect(url).get();
			if (doc.html().isEmpty())
				return null;
			Elements imgs=doc.select("img");
			imgBase64Code=imgs.get(idx).attr("src");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return NetworkUtils.getBase64Image(imgBase64Code);
	}
	
	static public int getBandParametersPlotNum(String nucleus)
	{
		String url=MessageFormat.format(bandParametersBaseURL,nucleus);
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			if (doc.html().isEmpty())
				return 0;
			Elements imgs=doc.select("img");
			return imgs.size();
		}catch (IOException e1) {
			e1.printStackTrace();
		}
		return 0;
	}
}
