package rainer_sieberer;

import java.util.*;

public class NN{
   
   ArrayList<ArrayList<Node>> net;
   double learningrate;
   
   public NN(){
      net = new ArrayList<ArrayList<Node>>();
   }
   
   public void setLearningrate(double lr){
      learningrate = lr;
   }
   
   public boolean add(int anz){
      
      try{
         if(net.size() > 0){
            setWeigth(net.size() - 1, anz);
         }

         ArrayList<Node> help = new ArrayList<Node>();
         
         for(int i = 0; i < anz; i++){
            help.add(new Node());
         }
         
         net.add(help);
      }
      catch(Exception e){
         System.out.println("Unknown Error!");
         return false;
      }
      
      return true;
   }
   
   private void setWeigth(int stelle, int anz){
      for(int i = 0; i < net.get(stelle).size(); i++){
         net.get(stelle).get(i).newRandomWeigth(anz);
      }
   }
   
   public void print(){
   
      System.out.print("Neuronal Network with " + net.size() + " Layers");
      System.out.println(" and Learningrate " + learningrate + ": ");
   
      for(int i = 0; i < net.size(); i++){
         for(int j = 0; j < net.get(i).size(); j++){
            System.out.print("(" + net.get(i).get(j).getInput() + ",");
            System.out.print(net.get(i).get(j).getOutput() + ",");
            System.out.print(net.get(i).get(j).getError() + ") ;");
           // System.out.print(net.get(i).get(j).weigth.size() + ") ; ");
         }
         
         System.out.println();
      }
   }
   
   private double sigmoid(double x){
      return 1 / (1 + Math.pow(Math.E, -x));
   }
   
   private double dersig(double x){
      return sigmoid(x) * (1 - sigmoid(x));
   }
   
   private void feedForward(ArrayList<Double> inputlist, ArrayList<Double> targetlist){
      
      if(net.size() < 1){
         System.out.println("No Layers in this Network!");
         return;
      }
      
      if(inputlist.size() != net.get(0).size() || targetlist.size() != net.get(net.size() - 1).size()){
         System.out.println("Wrong Input or Targets!");
         return;
      }
      
      setInputlayer(inputlist);
      
      for(int i = 1; i < net.size(); i++){
         calcInput(i);
         calcOutput(i);
      }
   }
   
   private void feedForward(ArrayList<Double> inputlist){
      
      if(net.size() < 1){
         System.out.println("No Layers in this Network!");
         return;
      }
      
      if(inputlist.size() != net.get(0).size()){
         System.out.println("Wrong Input!");
         return;
      }
      
      setInputlayer(inputlist);
      
      for(int i = 1; i < net.size(); i++){
         calcInput(i);
         calcOutput(i);
      }
   }
   
   private void setInputlayer(ArrayList<Double> inputlist){
      for(int i = 0; i < inputlist.size(); i++){
         net.get(0).get(i).setInput(inputlist.get(i));
         net.get(0).get(i).setOutput(inputlist.get(i));
      }
   }
   
   private void calcInput(int layer){
      double sum;
      
      for(int i = 0; i < net.get(layer).size(); i++){
         sum = 0;
         
         for(int j = 0; j < net.get(layer - 1).size(); j++){
            sum += net.get(layer - 1).get(j).getOutput() * net.get(layer - 1).get(j).weigth.get(i);
         }
         
         net.get(layer).get(i).setInput(sum);
      }
   }
   
   private void calcOutput(int layer){
      for(int i = 0; i < net.get(layer).size(); i++){
         net.get(layer).get(i).setOutput(sigmoid(net.get(layer).get(i).getInput()));
      }
   }
   
   private void calcErrorOut(ArrayList<Double> targetlist){
      for(int i = 0; i < net.get(net.size() - 1).size(); i++){
         double actual = net.get(net.size() - 1).get(i).getOutput();
        // net.get(net.size() - 1).get(i).setError(Math.pow(targetlist.get(i) - actual, 2) / 2);
         net.get(net.size() - 1).get(i).setError((targetlist.get(i) - actual) * dersig(actual));
      }
   }
   
   private void calcError(){
      
      ArrayList<Double> errors = new ArrayList<Double>();
      
      for(int i = net.size() - 2; i > 0; i--){
         for(int j = 0; j < net.get(i).size(); j++){
            
            double error = 0;
            
            for(int k = 0; k < net.get(i + 1).size(); k++){
               error += net.get(i).get(j).weigth.get(k) * net.get(i + 1).get(k).getError(); 
            }
            
            errors.add(error);
         } 
         
         for(int j = 0; j < net.get(i).size(); j++){
            net.get(i).get(j).setError(errors.get(j) * dersig(net.get(i).get(j).getOutput()));
         }
      }
   }
   
   public void updateWeigth(){
      
      for(int i = net.size() - 2; i >= 0; i--){
         for(int j = 0; j < net.get(i).size(); j++){
            for(int k = 0; k < net.get(i).get(j).weigth.size(); k++){
               net.get(i).get(j).setWeigth(k, net.get(i).get(j).weigth.get(k) + (learningrate * (net.get(i + 1).get(k).getError() * dersig(net.get(i + 1).get(k).getOutput()))));
            }
         }
      }
   }
   
   public void train(ArrayList<Double> inputlist, ArrayList<Double> targetlist){
      
      feedForward(inputlist, targetlist);
      calcErrorOut(targetlist);
      calcError();
      
      updateWeigth();
   }
   
   public ArrayList<Double> query(ArrayList<Double> inputlist){
      feedForward(inputlist);
      
      ArrayList<Double> help = new ArrayList<Double>();
      
      for(int i = 0; i < net.get(net.size() - 1).size(); i++){
         help.add(net.get(net.size() - 1).get(i).getOutput());
      }
      
      return help;
   }
   
   private class Node{
      
      double input;
      double output;
      double error;
      ArrayList<Double> weigth;
      
      public Node(){
         input = 0;
         output = 0;
         error = 0;
         weigth = new ArrayList<Double>();
      }
      
      private void setInput(double input){
         this.input = input;
      }
      
      private double getInput(){
         return input;
      }
      
      private void setOutput(double output){
         this.output = output;
      }
      
      private double getOutput(){
         return output;
      }
      
      private void setError(double error){
         this.error = error;
      }
      
      private double getError(){
         return error;
      }
      
      private void newRandomWeigth(int anz){
         for(int i = 0; i < anz; i++){
            weigth.add(Math.random());
         }
      }

      private void setWeigth(int pos, double newWeigth){
         weigth.set(pos, newWeigth);
      }
   }
}