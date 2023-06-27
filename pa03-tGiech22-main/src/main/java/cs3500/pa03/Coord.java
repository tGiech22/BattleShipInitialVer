package cs3500.pa03;

/**
 * representation of a coordinate pair
 */
public class Coord {
  private int x;
  private int y;

  /**
   * constructor
   *
   * @param x coordinate of object
   * @param y coordinate of object
   */
  Coord(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * getter for int x
   *
   * @return the x coordinate
   */
  public int getX() {
    return x;
  }

  /**
   * getter for int y
   *
   * @return the y coordinate
   */
  public int getY() {
    return y;
  }

}
