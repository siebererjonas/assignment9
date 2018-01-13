package rainer_sieberer;

import java.util.*;

public class As9{
   
   NN net;
   
   public void init(int inputnodes, int hiddennodes, int outputnodes, double learningrate){
      
      try{
         net = new NN();
          
         net.add(inputnodes);
         net.add(hiddennodes);
         net.add(outputnodes);
         
         net.setLearningrate(learningrate);
      }
      catch(Exception e){
         System.out.println("ERROR Network not initialised!");
      }
   }
    
   public void train(ArrayList<Double> inputlist, ArrayList<Double> targetlist){
      
      try{
         net.train(inputlist, targetlist);
      }
      catch(Exception e){
         System.out.println("ERROR while Training!");
      }
   }
   
   public int query(ArrayList<Double> inputlist){
      
      ArrayList<Double> result = net.query(inputlist);
      
      return max(result);
   }
   
   private int max(ArrayList<Double> list){
   
      int max = -1;
      double maxWert = 0.0;
      
      for(int i = 0; i < list.size(); i++){
         if(list.get(i) > maxWert){
            maxWert = list.get(i);
            max = i;
         }
      }
      
      return max;
   }
}