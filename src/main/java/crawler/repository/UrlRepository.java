package crawler.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import crawler.pojos.UrlPojo;

public interface UrlRepository extends JpaRepository<UrlPojo, Serializable> {
	
	public List<UrlPojo> findByUrl(String url);
	
	public long count();
	
	public List<UrlPojo> findByUrlContaining(String url);
}
