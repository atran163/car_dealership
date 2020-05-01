package assignment1_car;

import java.io.IOException;

public class CarDealerApp {
   public static void main(String args[]) throws IOException{	
   
   //instantiate a CarDealer object with location
   CarDealer newDealer = new CarDealer("Fremont, CA");	
   
   //invoke CarDealer's method ( to load the inventory by user input)
   newDealer.init();
   
   //invoke CarDealer's run method (menu-driven method using a do-while loop)
   int choice = 0;
   newDealer.run(choice);
	
   }	
	
}

