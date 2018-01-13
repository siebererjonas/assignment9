package rainer_sieberer;

import java.util.*;
import java.io.*;

public class FileConverter{
   
   public ArrayList<ArrayList<Double>> getInputs(File filename) throws Exception{   
      
     ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();
     ArrayList<String> lines = new ArrayList<String>();
      
      BufferedReader br = new BufferedReader( new FileReader ( filename ) );
      
		while ( br.ready() ){
			lines.add( br.readLine() );
      }
      
		br.close();
      
      int size = lines.size();
      
      for ( int j = 0; j < size; j++ ){
			String[] parts = lines.remove(0).split(",");
         
         ArrayList<Double> help = new ArrayList<Double>();
         
			for ( int i = 0; i < 784; i++ ){
				help.add(Double.parseDouble(parts[i+1])/255*0.99f+0.01f );
         }
         
         result.add(help);
		}
		return result;
      
   }
   
   public ArrayList<ArrayList<Double>> getTargets(File filename) throws Exception{
      
      ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();
      ArrayList<String> lines = new ArrayList<String>();
      
      BufferedReader br = new BufferedReader( new FileReader ( filename ) );
      
		while ( br.ready() ){
			lines.add( br.readLine() );
      }
      
		br.close();
      
      int size = lines.size();
      
      for ( int j = 0; j < size; j++ ){
			String[] parts = lines.remove(0).split(",");
         
         int digit = 0;
         ArrayList<Double> help = new ArrayList<Double>();
         
			for ( int i = 0; i < 784; i++ ){
				digit = Integer.parseInt(parts[0]);
         }
         
         for(int i = 0; i < 10; i++){
            if(i == digit){
               help.add(1.0);
            }
            else{
               help.add(0.0);
            }
         }
         
         result.add(help);
		}
      
		return result;
   }
   
   public void print(ArrayList<ArrayList<Double>> list){
      
      for(int i = 0; i < list.size(); i++){
         for(int j = 0; j < list.get(i).size(); j++){
            System.out.println(list.get(i).get(j) + " , ");
         }
         
         System.out.println("\n");
      }
   }
   
   public ArrayList<Double> convert(String csvString) throws Exception{
      
      
      String[] parts = csvString.split(",");
      
      ArrayList<Double> result = new ArrayList<Double>();
      
      for(int i = 0; i < parts.length; i++){
         result.add(Double.parseDouble(parts[i])/255*0.99f+0.01f);
      }
      
      return result;
   }
}