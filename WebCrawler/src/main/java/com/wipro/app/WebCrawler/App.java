package com.wipro.app.WebCrawler;

import java.util.Set;

import org.junit.Test;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		String domain="wiprodigital.com";
		WebCrawler crawler = new WebCrawler(domain);
		String urls = crawler.crawl();
		System.out.println(urls);
	}
}
