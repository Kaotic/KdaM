/*
 * Créée et développé par Kaotic. https://kaotic.fr/
 * Toutes reproduction/copie entraine une poursuite pénale.
 * Code écrit avec coeur est beaucoup de volontés.
 */
package fr.kaotic.daemon;

import fr.kaotic.func.execFunction;
import fr.kaotic.func.utilsFunction;
import fr.kaotic.http.HttpHandleCls;
import fr.kaotic.http.HttpProtocolCls;
import fr.kaotic.http.ServerConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author kaotic
 */
public class main {
    
    public static String AppVersion = "v4";
  
    public static void main(String[] args) throws IOException{
    	/* COMMENT THIS BECAUSE THIS HAVE BUGS.
    	try {
			utilsFunction.startUp("KdaM");
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        final Thread t = new Thread(new ThreadDaemon());
        t.start();
    }

}




















//WEB SERVER!!! DON'T TOUCH THIS.
class SmartServerConfig extends ServerConfig {
    @Override
    public HttpHandleCls generateHttpHandleCls(String tagFile) {

        if (tagFile.equals("/")) {

            return new BaseHHC();
        }
        else if (tagFile.equals(("/state"))){
            return new State();
        }
        else if (tagFile.equals(("/authentification"))){
            return new Authentification();
        }else
			try {
				if (tagFile.equals(("/api.console/" + utilsFunction.sha1Encrypt(ThreadDaemon.WebPassword) + "@" + utilsFunction.sha1Encrypt(ThreadDaemon.WebUser)))){
				    return new AdminCommands();
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
        return new BaseHHC();
    }
  }
  
class BaseHHC implements HttpHandleCls {

    HttpProtocolCls m_hpc;

    @Override
    public void setHPC(HttpProtocolCls hpc) {

        m_hpc = hpc;
    }

    @Override
    public void response() {

        m_hpc.httpResponse(200, "text/html", main.AppVersion);
    }
}

class AdminCommands implements HttpHandleCls {
    HttpProtocolCls m_hpc;
    int posted = 0;
    String m_Command;
    
    @Override
    public void setHPC(HttpProtocolCls hpc) {

        m_hpc = hpc;

        if(hpc.method == 2){
                String GetCommand = hpc.queryString.get("command");
                if(GetCommand.length() > 1){
                    String command = utilsFunction.pHTTPCommand(GetCommand);
                    posted = 1;
                    m_Command = command;
                }
        }
    }

    public String Commands() {
        StringBuilder sb = new StringBuilder();
        sb.append("<center>");
        
        if(posted == 1){
        	sb.append(execFunction.execRCommand(m_Command));
        }
        
        sb.append("<form method=\"POST\">");
        sb.append("Commande :<br>");
        sb.append("<input type=\"text\" name=\"command\" value=\"wall -n hello\"><br>");
        sb.append("<input type=\"submit\" value=\"Envoyer\">");
        sb.append("</form>");
        sb.append("</center>");
        return sb.toString();
    }
    @Override
    public void response() {

        m_hpc.httpResponse(200, "text/html", Commands());
    }
}
class State implements HttpHandleCls {

    HttpProtocolCls m_hpc;

    @Override
    public void setHPC(HttpProtocolCls hpc) {

        m_hpc = hpc;
    }

    public String StateInfo() {
        StringBuilder sb = new StringBuilder();
        /*com.sun.management.OperatingSystemMXBean os = (com.sun.management.OperatingSystemMXBean)
            java.lang.management.ManagementFactory.getOperatingSystemMXBean();
        /*if(os ==null){
            com.sun.management.OperatingSystemMXBean os = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);
        }
        //GlobalMemory mem = GlobalMemory();

        int mb = 1024 * 1024;
        double TotalMem = (os.getTotalPhysicalMemorySize() / mb);
        double FreeMem = (os.getFreePhysicalMemorySize() / mb);
        double UsedMem = (os.getTotalPhysicalMemorySize() / mb) - (os.getFreePhysicalMemorySize() / mb);
        double TotalSwap = (os.getTotalSwapSpaceSize() / mb);
        double FreeSwap = (os.getFreeSwapSpaceSize() / mb);
        double UsedSwap = (os.getTotalSwapSpaceSize() /mb) - (os.getFreeSwapSpaceSize() / mb);
        
        sb.append("{\"TotalCores\":" + os.getAvailableProcessors() + 
                ",\"CPULoad\":" + Math.round(os.getSystemCpuLoad() * 100) + 
                ",\"TotalMemory\":" + TotalMem + 
                ",\"FreeMemory\":" + FreeMem + 
                ",\"UsedMemory\":" + UsedMem + 
                ",\"TotalSwap\":" + TotalSwap + 
                ",\"FreeSwap\":" + FreeSwap + 
                ",\"UsedSwap\":" + UsedSwap + 
                ",\"IPAddress\":\"" + ThreadDaemon.AdressIP.getHostAddress() +"\"" +
                ",\"name\":\"" + ThreadDaemon.AdressIP.getHostName() +"\"}");*/
        /*sb.append("Total cores: ");
        sb.append(os.getAvailableProcessors());
        sb.append("<br/>");
        sb.append("Proccessor usage: ");
        sb.append(Math.round(os.getSystemCpuLoad() * 100));
        sb.append("<br/>");
        sb.append("Total Memory: ");
        sb.append(os.getTotalPhysicalMemorySize() / mb);
        sb.append("<br/>");
        sb.append("Free Memory: ");
        sb.append(os.getFreePhysicalMemorySize() / mb);
        sb.append("<br/>");
        sb.append("Used Memory: ");
        sb.append((os.getTotalPhysicalMemorySize() /mb )- (os.getFreePhysicalMemorySize() / mb));
        sb.append("<br/>");
        sb.append("Max Memory: ");
        sb.append(os.getTotalPhysicalMemorySize() / mb);
        sb.append("<br/>");*/
        sb.append("{\"content\":\"404\"}");
        return sb.toString();

    }
    @Override
    public void response() {
        m_hpc.httpResponse(200, "application/json", StateInfo());
    }
}
class Authentification implements HttpHandleCls {

	HttpProtocolCls m_hpc;
    int status = 0; //0 DEFAULT - 1 ERROR - 2 POSTED - 3 CORRECT USER AND PASSWORD
    String m_Username;
    String m_Password;
    
    @Override
    public void setHPC(HttpProtocolCls hpc) {

        m_hpc = hpc;

        if(hpc.method == 2){
        	String Username = hpc.queryString.get("username");
            String Password = hpc.queryString.get("password");
                
            if(Username.length() > 1 && Password.length() > 1){
            	try {
					m_Username = utilsFunction.sha1Encrypt(Username);
					m_Password = utilsFunction.sha1Encrypt(Password);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
            	if(m_Username == ThreadDaemon.WebUser && m_Password == ThreadDaemon.WebPassword){
            		status = 3;
            	}
            	status = 2;
            }else{
            	status = 1;
            }
        }
    }

    public String Auth() {
        StringBuilder sb = new StringBuilder();
        sb.append("<center>");
        
        if(status == 0){
        	
        }else if(status == 1){
        	
        }
        
        sb.append("<form method=\"POST\">");
        sb.append("Commande :<br>");
        sb.append("<input type=\"text\" name=\"username\" value=\"user\"><br>");
        sb.append("<input type=\"password\" name=\"password\" value=\"pass\"><br>");
        sb.append("<input type=\"submit\" value=\"Connexion\">");
        sb.append("</form>");
        sb.append("</center>");
        return sb.toString();
    }
    @Override
    public void response() {
    	if(status == 3){
    		m_hpc.httpResponse(302, "text/html", "/api.console/web?token=");
    	}
        
    }
}
