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

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author kaotic
 */

public class main {
    
    public static String AppVersion = "v4.1";
  
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
        }else if(tagFile.equals(("/api.console/web"))){
			return new AdminCommands();
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
    
    String DefaultHTML(){
    	StringBuilder c = new StringBuilder();
    	c.append("<html><head>");
    	c.append("<title>KdaM : Remote Adminitration Tools " + main.AppVersion + "</title>");
    	c.append("<meta name=\"description\" LANG=\"en\" content=\"Created by Kaotic\">");
    	c.append("<meta name=\"robots\" content=\"nofollow\">");
    	c.append("<meta http-equiv=\"refresh\" content=\"5; URL=/authentification\">");
    	c.append("</head><body>");
    	c.append("Redirect in 5 seconds on authentification page.<br>");
    	c.append("<a href=\"https://github.com/Kaotic/KdaM\">Github KdaM by Kaotic</a>");
    	c.append("</body></html>");
		return c.toString();
    }

    @Override
    public void response() {

        m_hpc.httpResponse(200, "text/html", DefaultHTML());
    }
}

class AdminCommands implements HttpHandleCls {
    HttpProtocolCls m_hpc;
    int posted = 0;
    int access = 0;
    String m_Command;
    
    @Override
    public void setHPC(HttpProtocolCls hpc) {

        m_hpc = hpc;
        access = 0;

        if(hpc.method == 1 | hpc.method == 2){
            String Token = hpc.queryString.get("token");
            if(Token.length() > 1){
                if(new String(Token).equals(ThreadDaemon.WebToken)){
                	access = 1;
                	System.out.println("User access 1 on page");
                }
            }
        }
        
        if(hpc.method == 2 && access == 1){
                String GetCommand = hpc.queryString.get("command");
                if(GetCommand.length() > 1){
                    String command = utilsFunction.pHTTPCommand(GetCommand);
                    posted = 1;
                    m_Command = command;
                    System.out.println("User access 1 try command on page");
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
    String c_Username;
    String c_Password;
    
    @Override
    public void setHPC(HttpProtocolCls hpc) {

        m_hpc = hpc;

        if(hpc.method == 2){
        	String Username = hpc.queryString.get("username");
            String Password = hpc.queryString.get("password");
                
            if(Username.length() > 2 && Password.length() > 2){
            	try {
					c_Username = utilsFunction.sha1Encrypt(Username);
					c_Password = utilsFunction.sha1Encrypt(Password);
					
	            	System.out.println("Posted username: " + Username + " | SHA1: " + c_Username + " | " + ThreadDaemon.WebUser);
	            	System.out.println("Posted password: " + Password + " | SHA1: " + c_Password + " | " + ThreadDaemon.WebPassword);
	            	
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
            	status = 1;

            	if(new String(c_Username).equals(ThreadDaemon.WebUser) && new String(c_Password).equals(ThreadDaemon.WebPassword)){
            		status = 3;
            	}else{
            		status = 1;
            	}
            }else{
            	status = 2;
            }
        }
    }

    public String Auth() {
        StringBuilder sb = new StringBuilder();
        sb.append("<center>");
        sb.append("<h1>Authentification :</h1><br>");
        if(status == 0){
        	System.out.println("USER ON AUTHENTIFICATION PAGE.");
        }else if(status == 1){
        	System.out.println("USER FAILED AUTHENTIFICATION.");
        	sb.append("<b style=\"color: red;\">AUTHENTIFICATION FAILED.</b>");
        }else if(status == 2){
        	System.out.println("USER POSTED INFORMATION ON AUTHENTIFICATION PAGE.");
        	sb.append("<b style=\"color: red;\">USERNAME OR PASSWORD FIELD IS EMPTY.</b>");
        }else if(status == 3){
        	//Useless
        	System.out.println("USER AUTH.");
        }
        
        sb.append("<form method=\"POST\">");
        sb.append("<input type=\"text\" name=\"username\" placeholder=\"Username\"><br>");
        sb.append("<input type=\"password\" name=\"password\" placeholder=\"Password\"><br><br>");
        sb.append("<input type=\"submit\" value=\"Connexion\">");
        sb.append("</form>");
        sb.append("<a href=\"https://github.com/Kaotic/KdaM\">Github KdaM by Kaotic</a>");
        sb.append("</center>");
        return sb.toString();
    }
    @Override
    public void response() {
    	if(status == 3){
    		m_hpc.httpRedirect("/api.console/web?token=" + ThreadDaemon.WebToken);
    	}else{
    		m_hpc.httpResponse(200, "text/html", Auth());
    	}
    }
}
