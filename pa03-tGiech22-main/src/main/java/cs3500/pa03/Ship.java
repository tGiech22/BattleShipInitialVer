package cs3500.pa03;

import java.io.IOException;
import java.util.List;

/**
 * a representation of a ship
 */
public class Ship {

  List<Coord> location;
  ShipType type;

  /**
   * constructor
   *
   * @param location of the ship
   * @param type of the ship i.e. (Carrier, battleship etc.)
   */
  Ship(List<Coord> location, ShipType type) {
    this.location = location;
    this.type = type;
  }

  /**
   * getter for List of coord location
   *
   * @return the location of the ship
   */
  public List<Coord> getLocation() {
    return this.location;
  }

  /**
   * getter for the ship type
   *
   * @return an enumeration that is the ship type
   */
  public ShipType getType() {
    return type;
  }
}
