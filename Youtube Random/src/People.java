import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


public class People {
	public String pName = "default name";
	private String content, decodedComment;
	public String pChannelLink = "default link";


	public People() {
		this.pName = "default name";
		this.pChannelLink = "default link";
		this.content = "default content";
	}

	public People(String name, String channelLink, String content){
		this.pName = name;
		this.pChannelLink = channelLink;
		this.content = content;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((pChannelLink == null) ? 0 : pChannelLink.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof People)) {
			return false;
		}
		People other = (People) obj;
		if (pChannelLink == null) {
			if (other.pChannelLink != null) {
				return false;
			}
		} else if (!pChannelLink.equals(other.pChannelLink)) {
			return false;
		}
		return true;
	}

	//To String Method
	public String toString(){
		return "Name: " + pName + " || ChannelLink: " + pChannelLink + " || " + " Content: " + this.content;
	}

}
