package fr.kaotic.func;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class execFunction {

	
	public static void execFile(String filename){
		try {
			//Runtime.getRuntime().exec(fname, null, new File(location));
			Desktop.getDesktop().open(new File(filename));
			
		} catch (IOException e) {
			throw new IllegalArgumentException("Fichier non trouvé.");
		}
	}
	
	public void execCommand(String command){
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public StringBuilder execRCommand(String command){
		StringBuilder rs = new StringBuilder();
		String line;
		try{
    		
            Process p = Runtime.getRuntime().exec (command);
            
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            if(error.readLine() == null){
            	rs.append("Commande execut&eacute; :<br>");
                while ((line = input.readLine()) != null)
                	rs.append(line + "<br>");
                input.close();
            }else{
            	rs.append("Error :<br>");
            	while ((line = error.readLine()) != null)
            		rs.append(line + "<br>");
                error.close();
            }
            
    	}catch(IOException ex){
    		
    	}
		return rs;
	}
}
