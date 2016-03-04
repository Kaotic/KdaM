package fr.kaotic.func;

public class osFunction {
	private String OS;
	
	public osFunction(){
		OS = System.getProperty("os.name").toLowerCase();
	}
 
	public String getOSName() {
 
		if (isWindows()) {
			return "windows";
		} else if (isMac()) {
			return "mac";
		} else if (isUnix()) {
			return "nix";
		} else if (isSolaris()) {
			return "solaris";
		} else {
			return "not found";
		}
	}
	public int getOS(){
		if (isWindows()) {
			return 1;
		} else if (isMac()) {
			return 2;
		} else if (isUnix()) {
			return 3;
		} else if (isSolaris()) {
			return 4;
		} else {
			return -1;
		}
	}
 
	private boolean isWindows() {
 
		return (OS.indexOf("win") >= 0);
 
	}
 
	private boolean isMac() {
 
		return (OS.indexOf("mac") >= 0);
 
	}
 
	private boolean isUnix() {
 
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
 
	}
 
	private boolean isSolaris() {
 
		return (OS.indexOf("sunos") >= 0);
 
	}
}
