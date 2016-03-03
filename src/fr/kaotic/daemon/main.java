/*
 * Créée et développé par Kaotic. https://kaotic.fr/
 * Toutes reproduction/copie entraine une poursuite pénale.
 * Code écrit avec coeur est beaucoup de volontés.
 */
package fr.kaotic.daemon;

import fr.kaotic.http.HttpHandleCls;
import fr.kaotic.http.HttpProtocolCls;
import fr.kaotic.http.ServerConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author kaotic
 */
public class main {
    
    public static String AppVersion = "v4";
  
    public static void main(String[] args) throws IOException{
      //Démarrage du WebServer
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
        else if (tagFile.equals(("/" + ThreadDaemon.WebUser + "/api.console/" + ThreadDaemon.WebPassword + "@" + ThreadDaemon.WebUser))){
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
                    String command = GetCommand
                        .replaceAll("\\+", " ")
                        .replaceAll("%3A",":")
                        .replaceAll("%5C", "\\\\")
                        .replaceAll("%7C", "|")
                        .replaceAll("%3F", "?")
                        .replaceAll("%3B", ";")
                        .replaceAll("%2C", ",")
                        .replaceAll("%25", "%")
                        .replaceAll("%5E", "^")
                        .replaceAll("%24", "\\$")
                        .replaceAll("%22", "\"")
                        .replaceAll("%27", "'")
                        .replaceAll("%7E", "~")
                        .replaceAll("%26", "&")
                        .replaceAll("%23", "#")
                        .replaceAll("%60", "`")
                        .replaceAll("%28", "(")
                        .replaceAll("%29", ")")
                        .replaceAll("%2F", "/");
                    	posted = 1;
                    	m_Command = command;
                }
        }else{
        }
        
            
            
            /*String line;
            Process p = Runtime.getRuntime().exec (command);
            BufferedReader input =new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader error =new BufferedReader(new InputStreamReader(p.getErrorStream()));

            System.out.println("OUTPUT\n");
            while ((line = input.readLine()) != null)
              System.out.println(line);
            input.close();

            System.out.println("ERROR\n");
            while ((line = error.readLine()) != null)
              System.out.println(line);
            error.close();*/
            
        
        

    }

    public String Commands() {
        StringBuilder sb = new StringBuilder();
        sb.append("<center>");
        
        if(posted == 1){
        	try{
        		String line;
                Process p = Runtime.getRuntime().exec (m_Command);
                
                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));

                if(error.readLine() == null){
                    sb.append("Commande execut&eacute; :<br>");
	                while ((line = input.readLine()) != null)
	                	sb.append(line + "<br>");
	                input.close();
                }else{
                	sb.append("Error :<br>");
                	while ((line = error.readLine()) != null)
                    	sb.append(line + "<br>");
                    error.close();
                }
                
        	}catch(IOException ex){
        		
        	}
            
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
        sb.append("Not Here.");
        return sb.toString();

    }
    @Override
    public void response() {
        m_hpc.httpResponse(200, "application/json", StateInfo());
    }
}
