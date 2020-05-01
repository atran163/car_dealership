// ---------------------------------------------------------------------------------------------------------------------------------
// @version4.0 08-08-2019
// @author  Amanda Tran
//  File name:  SmartCarrier.java
//  Program purpose: This program is to process phone messages
//  Disclaimer: If this program is working it's written by the author below. If it is not I don't know who wrote it.
//  Revision history:
//   Date                  Programmer               Change ID   Description
//   08/08/2019           Amanda Tran                 5841       SmartCarier.java
// --------------------------------------------------------------------------------------------------------------------------------
import java.util.*;
import java.util.Map.Entry;

import javax.naming.NamingException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SmartCarrier {
	
   	
   ArrayList<Item> itemList = new ArrayList<Item>(); //used for private functions
   private TreeMap<String, ArrayList<Item>> phoneMap = null;
   private String location;
	
   public SmartCarrier() {
      phoneMap = new TreeMap<String, ArrayList<Item>>();     
   }
   	
   private SmartCarrier(String location) {
      this.location = location;
      phoneMap = new TreeMap<String, ArrayList<Item>>();
   }

  
   
   //public init method -- read text file to build tree map entries 
   public void init() throws IOException {
      String fileLocation = "/Users/AmandaTran/Desktop/records.txt";
	  FileReader in = new FileReader(fileLocation);
	  BufferedReader br = new BufferedReader(in);
	  String data;
	  
	  while ((data = br.readLine())!= null) {
	     String [] current = data.split(",");
		 switch (current[0]) {
		    case "T":
		       Text textObj = new Text(current[4]);	
		       Message<?> text = new Message<Text>(Integer.parseInt(current[1]), current[2], current[3], Double.parseDouble(current[5]), textObj);
		       String fromKey = null;
		       fromKey = text.getFrom(); 	//KEY = FROM PHONE NUMBER	       
		       
		       if (phoneMap.containsKey(fromKey)) {
		    	   phoneMap.get(fromKey).add(text);
		       }
		       else {
		          ArrayList<Item> phoneList = new ArrayList<Item>();
		          phoneList.add(text);
		          phoneMap.put(fromKey, phoneList);   
		       }
		       break;
		       
		    case "M":
		       Media medObj = new Media(Double.parseDouble(current[4]), current[5]);
		       Message<?> media = new Message<Media>(Integer.parseInt(current[1]), current[2], current[3], Double.parseDouble(current[6]), medObj);
		       String fromKey2 = null;
		       fromKey2 = media.getFrom(); 
		       
		       if (phoneMap.containsKey(fromKey2)) {
		    	   phoneMap.get(fromKey2).add(media);
		       }
		       else {
		    	  ArrayList<Item> phoneList = new ArrayList<Item>();
		          phoneList.add(media);
		          phoneMap.put(fromKey2, phoneList);   
		       }
		       break;
		       
		    case "V":
		       Voice voiceObj = new Voice(Integer.parseInt(current[4]), current[5]); 
		       Message<?> voice = new Message<Voice>(Integer.parseInt(current[1]), current[2], current[3], Double.parseDouble(current[6]), voiceObj);
		       String fromKey3 = null;
		       fromKey3 = voice.getFrom(); 
		       
		       if (phoneMap.containsKey(fromKey3)) {
		    	   phoneMap.get(fromKey3).add(voice);
		       }
		       else {
		    	  ArrayList<Item> phoneList = new ArrayList<Item>();
		          phoneList.add(voice);
		          phoneMap.put(fromKey3, phoneList);   
		       }
		       break;
		 }
	  }
	  br.close();	   
   }
   
   
   public void menu() {
	   System.out.println("");
	   System.out.println("FOOTHILL WIRELESS AT " + location);
	   System.out.println("MESSAGE UTILIZATION AND ACCOUNT ADMIN");
	   System.out.println("1. List of all accounts");
	   System.out.println("2. Erase the first media message");
	   System.out.println("3. Disconnect an account");
	   System.out.println("4. Quit");
	}
   
   
   public void run() throws IOException {
	  String inputString = "3";  //this will be used for the do-while loop to make sure it runs the first time
	  while (!inputString.equals("4")) {
      SmartCarrier first = new SmartCarrier("Santa Clara");
      first.menu();
	  BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	  System.out.println("Enter your choice: ");
	  
	  try {
	     inputString = reader.readLine();
	  } 
	  catch (IOException e) {
		 e.printStackTrace();
	  }
	  
	  
	  switch (inputString) {
	     case "1": {
	        showAll(); 	
	        break;
	     }
	     
	     
	     case "2": {
	        ArrayList<Item> value = null;
	    	for (Entry<String, ArrayList<Item>> entry : phoneMap.entrySet()) {
	    	   value = entry.getValue();    //got value of treemap, now iterate through that value
	           eraseMedia(value);  
	        }
	        break;   

	     }
	     
	     
	     case "3": {
            disconnectAccount();
            break;
	     }
	     
	     case "4": {
	        quit();
	    	break;
	     }
      }
	  
	  
   }}
   
   
	  
   private void showAll() {
      ArrayList<Item> value = null;
  
	  for (Entry<String, ArrayList<Item>> entry : phoneMap.entrySet()) {
	     String key = entry.getKey();
	     value = entry.getValue();    //got value of treemap, now iterate through that value
	     System.out.println(key);  
	     ListIterator<Item> iter = value.listIterator();
 	     Item element = null;
 	     double charges = 0.0;

 	     
 	     while (iter.hasNext()) {
 	        element = iter.next();
 	        charges = charges + element.getCharge();
 	        System.out.println(element.toString());
 	        
 	     }
 		  System.out.println("Total Charges: " + charges);
          System.out.println("------------------------------------");
      }
   }
   
   private void eraseMedia(List<? extends Item> list) {
      for (Item element : list) {
         if (element instanceof Message<?>) {
        	Object element2;
            element2 = ((Message<?>) element).getMessage(); //typecast element as Message<?>

            if (element2 instanceof Media) {
                list.remove(element);
                break;
            }
	     }
      }
   }   
  
      
   private void disconnectAccount() throws IOException {
      BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));
      System.out.println("Enter a phone number: ");
	  String userString = null;
      userString = reader2.readLine();
      
      try {
         if (!phoneMap.containsKey(userString))
        	 throw new NamingException();
      }
      
      catch(NamingException ex) {
    	 System.out.println("Account " + userString + " does not exist!"); 
      }
      
      phoneMap.remove(userString); //even if userString does not exist, phoneMap.remove will do nothing and continue on normally 
   }
      
	  
   private void quit() {
      System.out.println("Thank you! Have a nice day!");	   
   }
   

   
   
   
}// last bracket
   
   

   
	
	
	
	
	

