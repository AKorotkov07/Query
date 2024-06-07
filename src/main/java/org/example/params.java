package org.example;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class QueryParamParser {

	public static Map<String, String> parse(String queryString) {
		Map<String, String> queryParams = new HashMap<>();
		List<NameValuePair> params = URLEncodedUtils.parse(queryString, StandardCharsets.UTF_8);
		for (NameValuePair param : params) {
			queryParams.put(param.getName(), param.getValue());
		}
		return queryParams;
	}

	public static String getQueryParam(String name, String queryString) {
		List<NameValuePair> params = URLEncodedUtils.parse(queryString, StandardCharsets.UTF_8);
		for (NameValuePair param : params) {
			if (param.getName().equals(name)) {
				return param.getValue();
			}
		}
		return null;
	}
}