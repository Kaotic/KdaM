package fr.kaotic.daemon;

import fr.kaotic.http.WebServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadDaemon
  implements Runnable
{
  static ServerSocket server;
  public static int WebPort = 3228;
  public static int DaemonPort = 3227;
  public static String WebUser = "KJava";
  public static String WebPassword = "TesxT3";
  public static InetAddress AdressIP;
  
  public void run()
  {
      try {
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
          InetAddress AdressIP;
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

