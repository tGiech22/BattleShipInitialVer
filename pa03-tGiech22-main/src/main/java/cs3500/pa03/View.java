package cs3500.pa03;

import java.io.IOException;

/**
 * view representation
 */
public class View {
  static Appendable appendable;

  /**
   * constructor
   *
   * @param appendable for output
   */
  View(Appendable appendable) {
    this.appendable = appendable;
  }

  /**
   * prints out given message
   *
   * @param message is a string that is to be outputted
   *
   * @throws IOException in case message is invalid
   */
  public static void printMessage(String message) throws IOException {
    appendable.append(message);
  }

  /**
   * displays the given board representation
   *
   * @param boardRep a char array that represents the current game board
   *
   * @throws IOException in case boardRep is invalid
   */
  public static void displayBoard(char[][] boardRep) throws IOException {
    for (int i = 0; i < boardRep.length; i++) {
      for (int j = 0; j < boardRep[i].length; j++) {
        appendable.append(boardRep[i][j] + " ");
      }
      appendable.append("\n");
    }
  }
}
