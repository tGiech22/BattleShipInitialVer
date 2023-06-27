package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * testing board class
 */
public class BoardTest {

  /**
   * tests for board
   */
  @Test
  public void testBoard() {
    Board board = new Board(15, 15);
    assertEquals(board.getWidth(), 15);
    assertEquals(board.getHeight(), 15);
  }
}
