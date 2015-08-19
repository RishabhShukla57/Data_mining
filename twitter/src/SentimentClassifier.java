
import com.aliasi.classify.ConditionalClassification;
import com.aliasi.classify.LMClassifier;
import com.aliasi.util.AbstractExternalizable;
import java.io.File;
import java.io.IOException;

public class SentimentClassifier {

	String[] categories;
	LMClassifier class1;

	public SentimentClassifier() {
	
	try {
		class1= (LMClassifier) AbstractExternalizable.readObject(new File("classifier.txt"));
		categories = class1.categories();
	}
	catch (ClassNotFoundException | IOException e) {
	}

	}

	public String classify(String text) {
	ConditionalClassification classification = class1.classify(text);
	return classification.bestCategory();
	}
	
}