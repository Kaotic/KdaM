package fr.kaotic.func;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.Driver;

import fr.kaotic.utils.WinRegistry;

public class utilsFunction {
	public static void startUp(String fname) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		
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
}
