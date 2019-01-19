package com.wipro.app.WebCrawler;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WebCrawler {

	Pattern urlPattern = Pattern
			.compile("https?://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?");
	private HtmlPageReader pageLoader;
	Set<String> foundUrls = new HashSet<>();
	Set<String> urlsVisited = new HashSet<>();
	private String domain;
	private int depth;

	public WebCrawler(String domain) {
		this(new HtmlPageReader(), domain,2);
	}

	public WebCrawler(HtmlPageReader pageLoader, String domain, int depth) {
		this.pageLoader = pageLoader;
		this.domain = domain;
		this.depth = depth;
	}

	public String crawl() {
		doCrawlRecursively("http://"+domain,depth);
		return foundUrls.stream().collect(Collectors.joining(System.lineSeparator()));
	}

	private void doCrawlRecursively(String url,int depth) {
	
		if(depth == 0) return;
	
		String html =  pageLoader.read(url);
		urlsVisited.add(url);
		
		if(html==null) return ;
		Set<String> urls = findURLsToBeVisited(html);
		
		for (String u : urls) {
			
			doCrawlRecursively(u, depth - 1);
		}
	}

	private Set<String> findURLsToBeVisited(String html) {
		Matcher matcher = urlPattern.matcher(html);

		Set<String> visitUrls = new HashSet<>();
		while (matcher.find()) {
			String urlDomain = matcher.group(1);
			String fullUrl = matcher.group(0);
			foundUrls.add(fullUrl);
			if (urlDomain.equals(domain) && !urlsVisited.contains(fullUrl)) {
				visitUrls.add(fullUrl);
			}
		}
		return visitUrls;
	}

	public Set<String> getFoundUrls() {
		return foundUrls;

	}

}
