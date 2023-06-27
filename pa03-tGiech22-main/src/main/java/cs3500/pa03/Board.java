package cs3500.pa03;

import java.util.ArrayList;

/**
 * a representation of a gameBoard
 */
public class Board {

  private int width;
  private int height;

  /**
   * constructor
   *
   * @param width of the board
   * @param height of the board
   */
  Board(int width, int height) {
    this.width = width;
    this.height = height;
  }

  /**
   * getter for int width
   *
   * @return the width of the board
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * getter for int height
   *
   * @return the height of the board
   */
  public int getHeight() {
    return height;
  }
}
