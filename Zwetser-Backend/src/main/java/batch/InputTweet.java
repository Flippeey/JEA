package batch;

public class InputTweet {

	private final String screenName;
	private final String tweet;
	private final String postedFrom;
	private final String postDate;

	public InputTweet(String screenName, String tweet, String postedFrom,
			String postDate) {
		this.screenName = screenName;
		this.tweet = tweet;
		this.postedFrom = postedFrom;
		this.postDate = postDate;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getTweet() {
		return tweet;
	}

	public String getPostedFrom() {
		return postedFrom;
	}

	public String getPostDate() {
		return postDate;
	}

	@Override
	public String toString() {
		return "InputTweet [screenName=" + screenName + ", postedFrom="
				+ postedFrom + ", postDate=" + postDate + "]";
	}
}