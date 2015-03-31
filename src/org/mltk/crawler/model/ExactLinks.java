package org.mltk.crawler.model;

public class ExactLinks {

	private String url;
	private String urlMD5;

	public ExactLinks() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExactLinks(String url, String urlMD5) {
		super();
		this.url = url;
		this.urlMD5 = urlMD5;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlMD5() {
		return urlMD5;
	}

	public void setUrlMD5(String urlMD5) {
		this.urlMD5 = urlMD5;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((urlMD5 == null) ? 0 : urlMD5.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExactLinks other = (ExactLinks) obj;
		if (urlMD5 == null) {
			if (other.urlMD5 != null)
				return false;
		} else if (!urlMD5.equals(other.urlMD5))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ExactLinks [url=" + url + ", urlMD5=" + urlMD5 + "]";
	}

}
