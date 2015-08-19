
import java.io.IOException;
import java.util.ArrayList;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import org.jfree.chart.* ;
import org.jfree.data.*;
import java.awt.*;
import java.io.*;
import org.jfree.chart.*;
import org.jfree.chart.entity.*;
import org.jfree.data.general.*;
public class twitterManager {
 
	SentimentClassifier sentClassifier;
	int LIMIT= 1000; //the number of retrieved tweets
	ConfigurationBuilder cb;
	Twitter twitter;

	public twitterManager() {
		cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey("76YOm2yXYtr0CGPux1IZRDJ5d");
		cb.setOAuthConsumerSecret("lGRy3teQFZiPxbkevc4Cjq28xjLOeq9Mnw0J7ZRFCf8zVOrTcj");
		cb.setOAuthAccessToken("117119257-cpnaWYkvlSh7cX3KbDSyRTaqh0zm0usm73DYEgzT");
		cb.setOAuthAccessTokenSecret("adf6AbPlKvkqpaH8vJF1OxZP7OhC3WpUsMxHOZEkJNZun");
		twitter = new TwitterFactory(cb.build()).getInstance();
		sentClassifier = new SentimentClassifier();
	}

	public void performQuery(String inQuery) throws InterruptedException, IOException, TwitterException {
		Query query = new Query(inQuery);
		query.setCount(1000);
                int count=0;
                        int positive=0;
                        int negative=0;
                        int neutral=0;
		try {
			
			QueryResult r;
                        File f1=new File("trainDirectory/neg/a.txt");
                        f1.createNewFile();
                        File f2=new File("trainDirectory/pos/a.txt");
                        f2.createNewFile();
                        File f3=new File("trainDirectory/neu/a.txt");
                        f3.createNewFile();
                        File f4=new File("trainDirectory/verdict.txt");
                        f4.createNewFile();
                        BufferedWriter bw1=new BufferedWriter(new FileWriter(f1.getAbsoluteFile()));
                        BufferedWriter bw2=new BufferedWriter(new FileWriter(f2.getAbsoluteFile()));
                        BufferedWriter bw3=new BufferedWriter(new FileWriter(f3.getAbsoluteFile()));
                        do {
				r = twitter.search(query);
				ArrayList ts= (ArrayList) r.getTweets();
                                
				for (int i = 0; i< ts.size() && count <LIMIT; i++) {
					count++;
					Status t = (Status) ts.get(i);
					String text = t.getText();
					System.out.println("Text: " + text);
					String name = t.getUser().getScreenName();
					System.out.println("User: " + name);
					String sent = sentClassifier.classify(t.getText());
					System.out.println("Sentiment: " + sent); 
                                        if(sent.equals("neg")){
                                            negative++;
                                            bw1.write("------------------\n");
                                            bw1.write("Text: "+text+"\n");
                                            bw1.write("User: "+name+"\n");
                                        }else if(sent.equals("pos")){
                                            positive++;
                                            bw2.write("------------------\n");
                                            bw2.write("Text: "+text+"\n");
                                            bw2.write("User: "+name+"\n");                                            
                                        }else{
                                            neutral++;
                                            bw3.write("------------------");
                                            bw3.write("Text: "+text+"\n");
                                            bw3.write("User: "+name+"\n");                                            
                                        }
                                }   
                                
			} while ((query = r.nextQuery()) != null && count < LIMIT);
                       FileOutputStream fw = new FileOutputStream("trainDirectory/verdict.txt");
                       PrintStream pw1=new PrintStream(fw);
                       String fs5;
                       if(positive>negative)
                       {
                           fs5="General Sentiment is Positive";
                       }
                       else
                       {
                           fs5="General Sentiment is Negative";
                       }
                           
                       String fs1="Total Number Of Tweets Crawled:"+ count;
                       String fs2="Number Of Positive Tweets:" + positive;
                       String fs3="Number Of Negative Tweets:" + negative;
                       String fs4="Number Of Neutral Tweets:" + neutral;
                       pw1.println(fs1);
                       pw1.println(fs2);
                       pw1.println(fs3);
                       pw1.println(fs4);
                       pw1.println(fs5);
                       pw1.close();
                }
                	catch (TwitterException te) {
			System.out.println("Couldn't connect: " + te);
		}
                catch (IOException ie){
                    System.out.println("file error");
                }
	
                       DefaultPieDataset pieDataset = new DefaultPieDataset();

      
       pieDataset.setValue("Positives", new Double(positive));
        pieDataset.setValue("Negatives", new Double(negative));
         pieDataset.setValue("Neutral", new Double(neutral));
         
 
JFreeChart chart = ChartFactory.createPieChart ("Twitter Analysis", pieDataset,true,true,true);
                

	try 
	{
	  ChartUtilities.saveChartAsPNG
	  (
          new java.io.File("chart.jpg"), chart, 500, 300);
	} 
	catch (java.io.IOException exc)
	 {
	    System.err.println("Error writing image to file");

	}
    
	                      
		}
		
	

}