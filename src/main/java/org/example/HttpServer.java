package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

	private final int port;

	public HttpServer(int port) {
		this.port = port;
	}

	public void start() throws IOException {
		ServerSocket serverSocket = new ServerSocket(port);
		System.out.println("Сервер запущен и ожидает подключения...");

		while (true) {
			try (Socket clientSocket = serverSocket.accept();
				 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

				String requestLine = in.readLine();
				if (requestLine != null && !requestLine.isEmpty()) {
					RequestHandler.handleClientRequest(requestLine, out);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		HttpServer server = new HttpServer(8080);
		server.start();
	}
}