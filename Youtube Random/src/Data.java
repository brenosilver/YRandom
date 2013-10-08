/*  https://developers.google.com/youtube/2.0/developers_guide_protocol_comments#Retrieve_comments
 * 
 * 
 * https://gdata.youtube.com/feeds/api/videos/mE6tHm7dxrQ/comments?v=2&start-index=1&max-results=50&prettyprint=true
 * 
 * 
 */

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;


public class Data extends YRandom {

	private int count, count2, count3, count4;
	private ArrayList<String> authorText, commentContent;
	private ArrayList<String> authorLink, newLink;
	private String url, videoId;
	private Elements userLink, content;
	private int min, max;
	private boolean linkFound;
	private int rDuplicatesCount;
	private Map<String, String> map;
	private ArrayList<People> people;


	// Constructor
	public Data(String videoId) throws IOException{
		this.videoId = videoId;
		linkFound = true;
		count = 0;
		count2 = 0;
		count4 = 0;
		this.rDuplicatesCount = 0;
		this.authorText = new ArrayList<String>();
		this.authorLink = new ArrayList<String>();
		this.commentContent = new ArrayList<String>();
		this.min = 1;
		this.max = 50;
		count3 = 0;
	}

	public void getData() throws IOException{
		//Connect to website
		this.url = "https://gdata.youtube.com/feeds/api/videos/"+ this.videoId +"/comments?v=2.1&start-index=" + min + "&max-results=" + max + "&prettyprint=true";
		Document doc = Jsoup.connect(this.url).ignoreContentType(true).get();

		//Get the name tag from the xml
		Elements author = doc.select("name");
		for (Element element :author ){

			// if the the name = youtube jump
			if(element.text().equals("YouTube")){}

			//if name isn't youtube continue to add each text inside name tag to authorText ArrayList
			else{
				authorText.add(element.text());
				count++;
			}
		}

		//Get the yt:channelId tag from the xml
		userLink = doc.select("yt|channelId");
		for (Element element :userLink ){
			authorLink.add(element.text());
			count2++;
		}
		
		content = doc.select("content");
		for (Element cont :content ){
			commentContent.add(cont.text());
			//count4++;
		}

		makeUserLink();
	}

	//Make link Method
	public ArrayList<String> makeUserLink(){
		this.newLink = this.authorLink;
		String preChannelLink = "http://www.youtube.com/channel/";	

		///breaks links down
		while(this.count3 < newLink.size()){
			//If can't find /users/ in link
			if(newLink == null){
				this.linkFound = false;
				newLink.set(this.count3, "No Link Found");
			}

			//replaces with new broken link
			newLink.set(this.count3, preChannelLink + newLink.get(this.count3));

			count3++;
		}

		return newLink;
	}

	//Remove Duplicates Method
	//Adds them to a Set (doesn't support duplicates) > delete from ArrayList > add again to ArrayList
	public void removeDuplicates(){
		HashSet<People> hs = new HashSet<People>();
		hs.addAll(this.people);
		this.people.clear();
		this.people.addAll(hs);
	}

	//Loop through pages method
	public void loop(int min){
		if(this.min <= 1000){
			try {
				System.out.println("Start Index: " + this.min);
				getData();
				min = this.min+50;
				this.min = min;
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			loop(min);
		}
		
		int count99 = 0;
		this.people = new ArrayList<People>();
		for(String el: authorText){
			this.people.add(new People(el, this.authorLink.get(count99), this.commentContent.get(count99)));
			count99++;
		}

		removeDuplicates();
	}

	//Print Results Method
	public void printMe(){
		System.out.println( "finalllllllllllllllllllllllllllllllllllll");
		int x = 0;
		while(x < this.people.size()){
			System.out.println( x+1 + ": " + this.people.get(x));
			x++;

		}
	}

	//Getter and Setter Methods
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCount2() {
		return count2;
	}

	public void setCount2(int count2) {
		this.count2 = count2;
	}

	public ArrayList<String> getAuthorText() {
		return authorText;
	}

	public void setAuthorText(ArrayList<String> authorText) {
		this.authorText = authorText;
	}

	public ArrayList<String> getAuthorLink() {
		return authorLink;
	}

	public void setAuthorLink(ArrayList<String> authorLink) {
		this.authorLink = authorLink;
	}

	public Elements getUserLink() {
		return userLink;
	}

	public void setUserLink(Elements userLink) {
		this.userLink = userLink;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public ArrayList<People> getPeople() {
		return people;
	}

	public void setPeople(ArrayList<People> people) {
		this.people = people;
	}

}
