package org.example;

import java.util.HashMap;
import java.util.Map;

public class Request {
	private String path;
	private Map<String, String> queryParams = new HashMap<>();
	private Map<String, String> headers = new HashMap<>();
	private String httpMethod;
	private String httpVersion;

	// Конструктор
	public Request(String requestLine, Map<String, String> headers, String httpMethod, String httpVersion) {
		String[] requestParts = requestLine.split(" ");
		this.path = requestParts[1];
		this.headers = headers;
		this.httpMethod = httpMethod;
		this.httpVersion = httpVersion;

		String[] pathParts = this.path.split("\\?");
		if (pathParts.length > 1) {
			this.queryParams = QueryParamParser.parse(pathParts[1]);
		}
	}

	public String getPath() {
		return path;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public Map<String, String> getQueryParams() {
		return queryParams;
	}
	
	public String getQueryParam(String name) {
		return queryParams.get(name);
	}
}