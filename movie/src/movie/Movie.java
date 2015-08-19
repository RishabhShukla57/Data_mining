/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movie;

import java.io.File;
import java.io.IOException;
import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
//import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
//import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
//import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
//import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
//import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
//import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/**
 *
 * @author rishabh
 */
public class Movie {

    /**
     * @param args the command line arguments
   
     */
    public static void main(String[] args){
        try {
            
            DataModel dm = new FileDataModel(new File("data/movies.csv"));
             ItemSimilarity us = new PearsonCorrelationSimilarity(dm);
             //UserNeighborhood neighborhood = new NearestNUserNeighborhood(25, us, dm);
             GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dm, us);
             int x= 1;
             for(LongPrimitiveIterator items = dm.getItemIDs();items.hasNext();){
                 long itemId = items.nextLong();
                 List<RecommendedItem>recommendations = recommender.mostSimilarItems(itemId,5);
                 for(RecommendedItem recommendation:recommendations){
                     System.out.println(itemId+","+recommendation.getItemID());
                     
                 }
                 x++;
                 if(x>10)
                     System.exit(1);
                 
             }
             
    

          //  List<RecommendedItem> recommendations = cachingRecommender.recommend(2, 3);
          
          /*  for (RecommendedItem recommendation : recommendations) {
             System.out.println(recommendation);
}*/
        } catch (IOException ex) {
          
            ex.printStackTrace();
        } catch (TasteException ex) {
          
            System.out.println("their was a taste exception");
             ex.printStackTrace();
        }
    }
    
}
