package cs3500.pa03;

/**
 * enumeration ship type
 */
public enum ShipType {
  CARRIER(6),
  BATTLESHIP(5),
  DESTROYER(4),
  SUBMARINE(3);

  private int shipSize;

  /**
   * constructor to assign size to each type
   *
   * @param shipSize size of ship
   */
  ShipType(int shipSize) {
    this.shipSize = shipSize;
  }

  /**
   * gets the size of each type
   *
   * @return int ship size
   */
  public int getSize() {
    return this.shipSize;
  }
}
