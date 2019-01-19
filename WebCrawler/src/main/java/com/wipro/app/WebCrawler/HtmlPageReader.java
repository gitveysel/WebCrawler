package com.wipro.app.WebCrawler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HtmlPageReader {

	RestTemplate restTemplate = new RestTemplate();

	public String read(String url) {

		try {
			ResponseEntity<String> response = getResponse(url);
			while (response.getStatusCode() == HttpStatus.MOVED_PERMANENTLY) {
				System.out.println(response.getHeaders().get("Location").get(0));
				response = getResponse(response.getHeaders().get("Location").get(0));
			}

			return response.getBody();
		} catch (Exception ex) {
			return null;
		}

	}	
	private ResponseEntity<String> getResponse(String url) {
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		return response;
	}

}
