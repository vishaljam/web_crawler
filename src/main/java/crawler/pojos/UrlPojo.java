package crawler.pojos;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="crawler_url")
public class UrlPojo implements Serializable {
	
	@Id
	@GeneratedValue
	private long id;
	
	private @Getter @Setter String url;
		
	@Getter @Setter private Boolean isUrlLive;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getIsUrlLive() {
		return isUrlLive;
	}

	public void setIsUrlLive(Boolean isUrlLive) {
		this.isUrlLive = isUrlLive;
	}
	
}
