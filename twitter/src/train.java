import java.io.File;
import java.io.IOException;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.classify.LMClassifier;
import com.aliasi.corpus.ObjectHandler;
import com.aliasi.util.AbstractExternalizable;
import com.aliasi.util.Compilable;
import com.aliasi.util.Files;

/**
*
*/
public class train
{
    //private static String[] mcategories;
public static void main(String[] args) 
{

try {
//train.trainx();
twitterManager TwitterManager = new twitterManager();  
 TwitterManager.performQuery("Piku"); 
//new SentimentClassifier();
} catch (Exception e) {
}


}
    

static void trainx() throws IOException, ClassNotFoundException {  
    File trainDir;  
    String[] categories;  
    LMClassifier class1;  
    trainDir = new File("trainDirectory\\");  
    categories = trainDir.list();  
    int nGram = 7; //the nGram level, any value between 7 and 12 works  
    class1= DynamicLMClassifier.createNGramProcess(categories, nGram);  
    for (int i = 0; i < categories.length; ++i) {  
       String category = categories[i];  
       Classification classification = new Classification(category);  
       File file = new File(trainDir, categories[i]);  
       File[] trainFiles = file.listFiles();  
       for (int j = 0; j < trainFiles.length; ++j) {  
          File trainFile = trainFiles[j];  
          String review = Files.readFromFile(trainFile, "ISO-8859-1");  
          Classified classified = new Classified(review, classification);  
         ( (ObjectHandler) class1).handle(classified);   
       }  
     }  
    AbstractExternalizable.compileTo((Compilable) class1, new File("classifier.txt"));  
 }  
}