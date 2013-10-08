import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.util.IO;

//package edu.uci.ics.crawler4j.examples.basic;


/**
 * @author Yasser Ganjisaffar <lastname at gmail dot com>
 */
public class BasicCrawlController {
	
	private static final Logger logger = Logger.getLogger(CrawlController.class.getName());
	
       //public static void main(String[] args) throws Exception {
	public void startThis(String def, String num) throws Exception{
        	
                if (def.equals(null) || num.equals(null)) {
                     System.out.println("Needed parameters: ");
                        System.out.println("\t rootFolder (it will contain intermediate crawl data)");
                        System.out.println("\t numberOfCralwers (number of concurrent threads)");
                        return;
                }

                /*
                 * crawlStorageFolder is a folder where intermediate crawl data is
                 * stored.
                 */
                String crawlStorageFolder = def;

                /*
                 * numberOfCrawlers shows the number of concurrent threads that should
                 * be initiated for crawling.
                 */
                int numberOfCrawlers = Integer.parseInt(num);

                CrawlConfig config = new CrawlConfig();

                config.setCrawlStorageFolder(crawlStorageFolder);

                /*
                 * Be polite: Make sure that we don't send more than 1 request per
                 * second (1000 milliseconds between requests).
                 */
                config.setPolitenessDelay(200);

                /*
                 * You can set the maximum crawl depth here. The default value is -1 for
                 * unlimited depth
                 */
                config.setMaxDepthOfCrawling(0);

                /*
                 * You can set the maximum number of pages to crawl. The default value
                 * is -1 for unlimited number of pages
                 */
                config.setMaxPagesToFetch(20);

                /*
                 * Do you need to set a proxy? If so, you can use:
                 * config.setProxyHost("proxyserver.example.com");
                 * config.setProxyPort(8080);
                 * 
                 * If your proxy also needs authentication:
                 * config.setProxyUsername(username); config.getProxyPassword(password);
                 */

                /*
                 * This config parameter can be used to set your crawl to be resumable
                 * (meaning that you can resume the crawl from a previously
                 * interrupted/crashed crawl). Note: if you enable resuming feature and
                 * want to start a fresh crawl, you need to delete the contents of
                 * rootFolder manually.
                 */
                config.setResumableCrawling(false);

                /*
                 * Instantiate the controller for this crawl.
                 */
                PageFetcher pageFetcher = new PageFetcher(config);
                RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
                RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
                CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

                
                boolean resumable = config.isResumableCrawling();
                

                // Create env for DB del
                EnvironmentConfig envConfig = new EnvironmentConfig();
                envConfig.setAllowCreate(true);
                envConfig.setTransactional(resumable);
                envConfig.setLocking(resumable);
                // End Create env for DB del

 

                /*
                 * For each crawl, you need to add some seed urls. These are the first
                 * URLs that are fetched and then the crawler starts following links
                 * which are found in these pages
                 */

                controller.addSeed("http://www.youtube.com/all_comments?v=SUAKDRyARN0"); //Kripparian comments



                /*
                 * Start the crawl. This is a blocking operation, meaning that your code
                 * will reach the line after this only when crawling is finished.
                 */
                
                controller.start(BasicCrawler.class, numberOfCrawlers);
                
                
                
        }
}