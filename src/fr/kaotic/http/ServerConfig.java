package fr.kaotic.http;

import fr.kaotic.daemon.ThreadDaemon;

public abstract class ServerConfig {
	
	public abstract HttpHandleCls generateHttpHandleCls(final String tagFile);
	
	public int port(){
		
		return ThreadDaemon.WebPort;
	}
}
