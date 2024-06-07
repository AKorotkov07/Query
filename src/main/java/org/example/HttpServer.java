package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

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
				Map<String, String> headers = new HashMap<>();
				String httpMethod = "";
				String httpVersion = "";

				if (requestLine != null && !requestLine.isEmpty()) {
					String[] requestParts = requestLine.split(" ");
					httpMethod = requestParts[0];
					httpVersion = requestParts[2];

					String headerLine;
					while ((headerLine = in.readLine()) != null && !headerLine.isEmpty()) {
						String[] headerParts = headerLine.split(": ");
						headers.put(headerParts[0], headerParts[1]);
					}

					RequestHandler.handleClientRequest(requestLine, out, headers, httpMethod, httpVersion);
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