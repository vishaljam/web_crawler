package crawler.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crawler.pojos.UrlPojo;
import crawler.repository.UrlRepository;

@Service
public class QueryService {

	@Autowired(required=true)
	private UrlRepository urlRepository;
	
	public int getCountByKeyword(String keyword) {
		String finalKeyword = "KW=" + keyword;
		List<UrlPojo> urlPojos = urlRepository.findByUrlContainingAndIsUrlLive(finalKeyword,true);
		if (urlPojos != null) {
			return urlPojos.size();
		}
		return 0;
	}
	
	public List<String> getRecordsByKeywordAndPageNo(String keyword, String pageNumber) {
		String finalKeyword = "KW=" + keyword;
		String finalPageNumber = "PG-" + pageNumber;
		List<UrlPojo> urlPojos = urlRepository.findDistinctUrlByUrlContainingAndUrlContainingAndIsUrlLive(finalKeyword, finalPageNumber, true);
		List<String> urls = new ArrayList<String>();
		for (UrlPojo urlPojo : urlPojos) {
			String url = urlPojo.getUrl();
			urls.add(url);
		}
		return urls;
	}
}
