package assignment1_car;

public class Vehicle {
	
// private instance fields
private String make;
private String model;
private int year;
private double price;
	   
// public default and non default constructors
public Vehicle() {}
public Vehicle(String make, String model, int year, double price) {
   this.make = make;
   this.model = model;
   this.year = year;
   this.price = price;
}

// accessor/mutators
public String getMake() { return make; }
public String getModel() { return model; }
public int getYear() { return year; } 
public double getPrice() { return price; }
	   
public void setMake(String make) { this.make = make;}
public void setModel(String model) { this.model = model;}
public void setYear( int year) {this.year = year;}
public void setPrice(int price) {this.price = price;}
	   
	   
// toString() 
public String toString() {
   String strVehicle = make + ";" + model + ";" + year + ";" + price;
   return strVehicle;
}
}
