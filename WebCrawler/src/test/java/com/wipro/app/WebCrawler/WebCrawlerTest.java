package com.wipro.app.WebCrawler;


import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import com.wipro.app.WebCrawler.*;

 
import java.util.List;
import java.util.Set;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;



import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;



@RunWith(MockitoJUnitRunner.class)
public class WebCrawlerTest{
	
	String domain="wiprodigital.com";
	
	
	@Mock
	private HtmlPageReader pageLoader;	
	
	
	@Test
	public void should_list_all_the_pages(){

		int depth=2;
		WebCrawler crawler = new WebCrawler(pageLoader,domain,depth);
	 	String htmlRoot="<html><body>"
				+ "<a href='http://wiprodigital.com/subpage/abc.htm'>abc</a>"
				+ "<a href='http://google.com/subpage/abc.htm'>google</a>"
				+"</body></html>";
		
	 	String htmlSubPage="<html><body>"
				+ "<a href='http://wiprodigital.com/subpage3/abc.htm'>abc</a>"
				+ "<a href='https://someotherdomain.com/subpage/abc.htm'>google</a>"
				+"</body></html>";
	 	
		when(pageLoader.read(Mockito.anyString())).thenReturn(htmlRoot).thenReturn(htmlSubPage);
		
		String urls= crawler.crawl();
		assertThat(urls, containsString("http://wiprodigital.com/subpage/abc.htm"));
		assertThat(urls, containsString("http://google.com/subpage/abc.htm"));
		assertThat(urls, containsString("https://someotherdomain.com/subpage/abc.htm"));
	
	}
	
}