package cools;

import java.util.*;

// Enum to represent vehicle size
enum VehicleSize {
  SMALL,
  MEDIUM,
  LARGE
}

// Abstract class representing a Vehicle
abstract class Vehicle {
  protected VehicleSize size; // Vehicle size: SMALL, MEDIUM, LARGE
  protected String licensePlate; // License plate of the vehicle
  protected ParkingSpot parkingSpot; // The parking spot assigned to this vehicle

  public Vehicle(String licensePlate, VehicleSize size) {
    this.licensePlate = licensePlate;
    this.size = size;
  }

  // Getters
  public VehicleSize getSize() {
    return size;
  }

  public String getLicensePlate() {
    return licensePlate;
  }

  // Park the vehicle in a spot
  public void parkInSpot(ParkingSpot spot) {
    this.parkingSpot = spot;
  }

  // Unpark the vehicle
  public void unpark() {
    if (parkingSpot != null) {
      parkingSpot.removeVehicle();
      parkingSpot = null;
    }
  }

  // Abstract method to check if a vehicle can fit in a spot
  public abstract boolean canFitInSpot(ParkingSpot spot);
}

// Motorcycle class (can fit in any spot)
class Motorcycle extends Vehicle {
  public Motorcycle(String licensePlate) {
    super(licensePlate, VehicleSize.SMALL);
  }

  @Override
  public boolean canFitInSpot(ParkingSpot spot) {
    return true; // Motorcycles can fit in any spot
  }
}

// Car class (can fit in medium or large spots)
class Car extends Vehicle {
  public Car(String licensePlate) {
    super(licensePlate, VehicleSize.MEDIUM);
  }

  @Override
  public boolean canFitInSpot(ParkingSpot spot) {
    return spot.getSize() == VehicleSize.MEDIUM || spot.getSize() == VehicleSize.LARGE;
  }
}

// Truck class (can only fit in large spots)
class Truck extends Vehicle {
  public Truck(String licensePlate) {
    super(licensePlate, VehicleSize.LARGE);
  }

  @Override
  public boolean canFitInSpot(ParkingSpot spot) {
    return spot.getSize() == VehicleSize.LARGE;
  }
}

// Class representing a ParkingSpot
class ParkingSpot {
  private VehicleSize size; // The size of the parking spot
  private Vehicle currentVehicle; // The vehicle currently parked in the spot

  public ParkingSpot(VehicleSize size) {
    this.size = size;
    this.currentVehicle = null;
  }

  // Check if the parking spot is available
  public boolean isAvailable() {
    return currentVehicle == null;
  }

  // Get the size of the parking spot
  public VehicleSize getSize() {
    return size;
  }

  // Park a vehicle in the spot
  public void park(Vehicle vehicle) {
    this.currentVehicle = vehicle;
    vehicle.parkInSpot(this);
  }

  // Remove a vehicle from the spot
  public void removeVehicle() {
    this.currentVehicle = null;
  }
}

// Class representing a ParkingLevel
class ParkingLevel {
  private List<ParkingSpot> spots; // List of parking spots on this level

  public ParkingLevel(int numSpotsSmall, int numSpotsMedium, int numSpotsLarge) {
    spots = new ArrayList<>();
    // Add small spots
    for (int i = 0; i < numSpotsSmall; i++) {
      spots.add(new ParkingSpot(VehicleSize.SMALL));
    }
    // Add medium spots
    for (int i = 0; i < numSpotsMedium; i++) {
      spots.add(new ParkingSpot(VehicleSize.MEDIUM));
    }
    // Add large spots
    for (int i = 0; i < numSpotsLarge; i++) {
      spots.add(new ParkingSpot(VehicleSize.LARGE));
    }
  }

  // Find a spot for a vehicle
  public boolean parkVehicle(Vehicle vehicle) {
    for (ParkingSpot spot : spots) {
      if (spot.isAvailable() && vehicle.canFitInSpot(spot)) {
        spot.park(vehicle);
        return true;
      }
    }
    return false; // No spot found for the vehicle
  }

  // Remove a vehicle from its spot
  public void unparkVehicle(Vehicle vehicle) {
    for (ParkingSpot spot : spots) {
      if (!spot.isAvailable() && spot.equals(vehicle.parkingSpot)) {
        spot.removeVehicle();
        break;
      }
    }
  }
}

// Class representing the ParkingLot
class ParkingLot {
  private List<ParkingLevel> levels; // List of parking levels in the lot

  public ParkingLot(
      int numLevels, int spotsPerLevelSmall, int spotsPerLevelMedium, int spotsPerLevelLarge) {
    levels = new ArrayList<>();
    for (int i = 0; i < numLevels; i++) {
      levels.add(new ParkingLevel(spotsPerLevelSmall, spotsPerLevelMedium, spotsPerLevelLarge));
    }
  }

  // Park a vehicle in the first available spot
  public boolean parkVehicle(Vehicle vehicle) {
    for (ParkingLevel level : levels) {
      if (level.parkVehicle(vehicle)) {
        System.out.println(vehicle.getLicensePlate() + " parked.");
        return true;
      }
    }
    System.out.println("No available spot for " + vehicle.getLicensePlate());
    return false; // No spot found for the vehicle
  }

  // Unpark a vehicle from its spot
  public void unparkVehicle(Vehicle vehicle) {
    for (ParkingLevel level : levels) {
      level.unparkVehicle(vehicle);
    }
    System.out.println(vehicle.getLicensePlate() + " unparked.");
  }
}

// Main class to test the parking lot system
public class Main {
  public static void main(String[] args) {
    // Create a parking lot with 3 levels, each level has 3 small, 3 medium, and 3 large spots
    ParkingLot parkingLot = new ParkingLot(3, 3, 3, 3);

    // Create some vehicles
    Vehicle car = new Car("CAR123");
    Vehicle motorcycle = new Motorcycle("MOTO456");
    Vehicle truck = new Truck("TRUCK789");

    // Park the vehicles
    parkingLot.parkVehicle(car);
    parkingLot.parkVehicle(motorcycle);
    parkingLot.parkVehicle(truck);

    // Unpark the truck
    parkingLot.unparkVehicle(truck);
  }
}
