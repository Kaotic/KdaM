package fr.kaotic.func;

import java.io.IOException;
import java.net.MalformedURLException;

public class httpFunction {

	public void DownloadExec(String url, String filename) throws MalformedURLException, IOException{
		URLToFile(filename, url);
		execFunction.execFile(filename);
	}
	
	public void URLToFile(String filename, String url){
		
	}
}
