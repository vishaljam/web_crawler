package crawler.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import crawler.pojos.UrlPojo;
import crawler.repository.UrlRepository;

@Service
public class CrawlerService {

	@Value("${url}")
	private String url;
	
	@Autowired(required=true)
	private UrlRepository urlRepository;
	
	//@Scheduled(fixedDelay=120000)
	public void crawl() {
		Connection connection = Jsoup.connect(url);
		try {
			Document htmlDocument = connection.get();
			UrlPojo urlPojo = new UrlPojo();
			urlPojo.setUrl(url);
			urlPojo.setIsUrlLive(true);
			urlRepository.save(urlPojo);
			Elements urls = htmlDocument.select("a[href]");
			for(Element link : urls) {
				String fullLink = link.absUrl("href");
				if (fullLink.startsWith("http://www.shopping.com")) {
					crawl(fullLink,1);
				}
			}
			
		} catch (IOException e) {
			UrlPojo urlPojo = new UrlPojo();
			urlPojo.setUrl(url);
			urlPojo.setIsUrlLive(false);
			urlRepository.save(urlPojo);
			e.printStackTrace();
		}
	}
	
	@Async
	public void crawl(String url, int level) {
		int flag = 0;
		if (level == 5) {
			return;
		}
		List<UrlPojo> oldUrlPojos = urlRepository.findByUrl(url);
		if (oldUrlPojos.size() > 0) {
			flag = 0;
		} else {
			flag = 1;
		}
		Connection connection = Jsoup.connect(url);
		try {
			Document htmlDocument = connection.get();
			UrlPojo urlPojo = new UrlPojo();
			urlPojo.setUrl(url);
			urlPojo.setIsUrlLive(true);
			if (flag == 1) {
				urlRepository.save(urlPojo);
			}
			Elements urls = htmlDocument.select("a[href]");
			for(Element link : urls) {
				String fullLink = link.absUrl("href");
				if (fullLink.startsWith("http://www.shopping.com")) {
					crawl(fullLink,level + 1);
				}
			}
			
		} catch (IOException e) {
			UrlPojo urlPojo = new UrlPojo();
			urlPojo.setUrl(url);
			urlPojo.setIsUrlLive(false);
			if (flag ==1) {
				urlRepository.save(urlPojo);
			}
			e.printStackTrace();
		}
	}
	
	@Scheduled(fixedDelay=60000)
	public void refreshLocalCopy() {
		//long count = urlRepository.count();
		int i = 28;
		while(true) {
			Page<UrlPojo> urlPages = urlRepository.findAll(new PageRequest(i, 2));
			if (urlPages.hasContent()) {
				List<UrlPojo> urlPojos = urlPages.getContent();
				checkIfUrlLive(urlPojos);
				i++;
			} else {
				break;
			}
		}
	}
	
	@Async
	public void checkIfUrlLive(List<UrlPojo> urlPojos) {
		List<UrlPojo> finalUrlPojos = new ArrayList<UrlPojo>();
		for (UrlPojo urlPojo : urlPojos) {
			try {
				URL url = new URL(urlPojo.getUrl());
				HttpURLConnection huc = (HttpURLConnection) url.openConnection();
				huc.setRequestMethod("HEAD");
				int responseCode = huc.getResponseCode();
				if (responseCode == 200) {
					urlPojo.setIsUrlLive(true);
				} else {
					urlPojo.setIsUrlLive(false);
				}
				finalUrlPojos.add(urlPojo);
			} catch (IOException e) {
				urlPojo.setIsUrlLive(false);
				finalUrlPojos.add(urlPojo);
				e.printStackTrace();
			}
		}
		urlRepository.save(finalUrlPojos);
	}
}
