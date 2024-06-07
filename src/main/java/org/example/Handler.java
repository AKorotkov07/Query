package org.example;


import java.io.*;
import java.util.Map;

class RequestHandler {

	public static void handleClientRequest(String requestLine, PrintWriter out) {
		String[] requestParts = requestLine.split(" ");
		String path = requestParts[1];
		String[] pathParts = path.split("\\?");
		String filePath = pathParts[0];
		String queryString = pathParts.length > 1 ? pathParts[1] : null;

		File file = new File(filePath);
		if (file.exists() && !file.isDirectory()) {
			sendFileContent(file, out);
		} else {
			sendNotFound(out);
		}

		if (queryString != null) {
			Map<String, String> queryParams = QueryParamParser.parse(queryString);
			handleQueryParams(queryParams, out);
		}
	}

	private static void sendFileContent(File file, PrintWriter out) {
		out.println("HTTP/1.0 200 OK");
		out.println("Content-Type: " + getContentType(file.getName()));
		out.println();
		try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = fileReader.readLine()) != null) {
				out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void sendNotFound(PrintWriter out) {
		out.println("HTTP/1.0 404 Not Found");
		out.println("Content-Type: text/html; charset=utf-8");
		out.println();
		out.println("Файл не найден");
	}

	private static void handleQueryParams(Map<String, String> queryParams, PrintWriter out) {
		queryParams.forEach((key, value) -> out.println(key + " = " + value));
	}

	private static String getContentType(String fileName) {
		if (fileName.endsWith(".html")) {
			return "text/html";
		} else if (fileName.endsWith(".txt")) {
			return "text/plain";
		} else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
			return "image/jpeg";
		} else if (fileName.endsWith(".png")) {
			return "image/png";
		}
		return "application/octet-stream";
	}
}