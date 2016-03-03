package fr.kaotic.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WebServer {

	
	public void start(ServerConfig config) throws IOException {
		System.out.print("Démarrage du serveur web");
		ServerSocket sSocket = null;
		sSocket = new ServerSocket(config.port());
		final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
				3,
				50,
				5, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(3),
				new ThreadPoolExecutor.CallerRunsPolicy());
		while (true) {
		
			final Socket cSocket = sSocket.accept();
			cSocket.setSoTimeout(300);
			final ServerThread sThread = new ServerThread(cSocket,config);
			threadPool.execute(sThread);
		}
	}
}
