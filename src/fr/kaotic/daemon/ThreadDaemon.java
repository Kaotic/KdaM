package fr.kaotic.daemon;

import fr.kaotic.func.utilsFunction;
import fr.kaotic.http.WebServer;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadDaemon
  implements Runnable
{
  static ServerSocket server;
  public static int WebPort = 3228; //DEFAULT PORT
  public static String WebUser = "d75829799fecd67c3208208f7b57af18455a3791"; //SHA1 USERNAME
  public static String WebPassword = "8308651804facb7b9af8ffc53a33a22d6a1c8ac2"; //SHA1 PASSWORD
  public static String WebToken; //TOKEN FOR ADD SECURITY
  public static InetAddress AdressIP; //HOST IP ADRESS
  
  public void run()
  {
      try {
    	  WebToken = utilsFunction.tokenGenerate(128);
    	  System.out.println("Auth token: " + WebToken);
          startDaemon();
      } catch (IOException ex) {
          Logger.getLogger(ThreadDaemon.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
  
  public static void startDaemon() throws IOException {
       try
        {
            AdressIP = InetAddress.getLocalHost();
        }
        catch (UnknownHostException UnknownHostException)
        {
          UnknownHostException.printStackTrace();
        }

        //lancement config webserver
        System.out.print("WebServer - Configuration en cours...\n");
        SmartServerConfig conf = new SmartServerConfig();
        WebServer http = new WebServer();
        System.out.print("WebServer - Démarrage terminé...\n"); 
        http.start(conf);
  }
}

