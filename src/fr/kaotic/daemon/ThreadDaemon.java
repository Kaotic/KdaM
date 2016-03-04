package fr.kaotic.daemon;

import fr.kaotic.func.utilsFunction;
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
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadDaemon
  implements Runnable
{
  static ServerSocket server;
  public static int WebPort = 3228; //DEFAULT PORT
  public static String WebUser = "d75829799fecd67c3208208f7b57af18455a3791"; //SHA1 USERNAME
  public static String WebPassword = "8308651804facb7b9af8ffc53a33a22d6a1c8ac2"; //SHA1 PASSWORD
  public static InetAddress AdressIP; //HOST IP ADRESS
  
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
        System.out.print("WebServer - D�marrage termin�...\n"); 
        http.start(conf);
  }
}

