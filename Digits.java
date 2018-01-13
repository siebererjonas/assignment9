package rainer_sieberer;

import assignment9_int.Assignment9;
import java.io.File;
import java.util.ArrayList;

public class Digits implements Assignment9{
   
   As9 net;
   
   ArrayList<ArrayList<Double>> inputlists;
   ArrayList<ArrayList<Double>> targetlists;
   
   /** loads the .csv file with the training data or throws an Exception if anything goes wrong; returns true iff the initialization completed successfully. */
   public boolean init(File csvTrainingData) throws Exception{
      
      net = new As9();
      net.init(2, 2, 2, 0.1);
      
      FileConverter conv = new FileConverter();
      
      inputlists = conv.getInputs(csvTrainingData);
      targetlists = conv.getTargets(csvTrainingData);
      
      return true;
   }
   
   /** trains the net; returns true iff the training phase completed successfully. */
   public boolean train() throws Exception{
      
      try{
         for(int i = 0; i < inputlists.size(); i++){
            net.train(inputlists.get(i), targetlists.get(i));
         }
      }
      catch(Exception e){
         System.out.println("ERROR while Training!");
         return false;
      }
      
      return true;
   }
   
   /** tries to recognize the digit represented by csvString; returns the digit */
   public int recognize(String csvString) throws Exception{
      
      FileConverter conv = new FileConverter();
      
      return net.query(conv.convert(csvString));
   }
}