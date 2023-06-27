package cs3500.pa03;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * representation of model
 */
public class Model {
  private Board gameBoard;
  private List<Coord> shipPos;
  private List<Ship> fleet;
  private Random random;
  private char[][] boardRep;
  private List<Coord> allPossibleLocations;

  /**
   * constructor
   *
   * @param gameBoard that the game is being played on
   * @param random a random object to randomly generate ship positions
   */
  Model(Board gameBoard, Random random) {
    this.gameBoard = gameBoard;
    this.random = random;
    shipPos = new ArrayList<>();
    fleet = new ArrayList<>();
    boardRep = new char[this.gameBoard.getHeight()][this.gameBoard.getWidth()];
    allPossibleLocations = new ArrayList<>();
  }

  /**
   * generates the fleet the player will be using
   *
   * @param specifications a map for the type of ships and how many of each type to be
   *                       generated
   */
  public void generateFleet(Map<ShipType, Integer> specifications) {
    getAllPossibleCoords();

    int carrierNum = specifications.get(ShipType.CARRIER);
    int battleshipNum = specifications.get(ShipType.BATTLESHIP);
    int destroyerNum = specifications.get(ShipType.DESTROYER);
    int submarineNum = specifications.get(ShipType.SUBMARINE);

    fleet.addAll(createShips(ShipType.CARRIER, carrierNum));
    fleet.addAll(createShips(ShipType.BATTLESHIP, battleshipNum));
    fleet.addAll(createShips(ShipType.DESTROYER, destroyerNum));
    fleet.addAll(createShips(ShipType.SUBMARINE, submarineNum));
  }

  /**
   * creates all the ships of 1 type
   *
   * @param type of ship
   * @param numOfShip number of ships of that type
   *
   * @return list of ships of 1 specific type
   */
  public ArrayList<Ship> createShips(ShipType type, int numOfShip) {
    ArrayList<Ship> ships = new ArrayList<>();

    for (int i = 0; i < numOfShip; i++) {
      //List<Coord> location = createPos(type, gameBoard.getHeight(), gameBoard.getWidth());
      List<Coord> location = generateShipPos(type, this.random);

      //System.out.println(location.get(i).getX() + " " + location.get(i).getY());

      ships.add(new Ship(location, type));
    }

    return ships;
  }

  /**
   * gets all the possible coordinates for the game board
   */
  public void getAllPossibleCoords() {
    for (int i = 0; i < gameBoard.getHeight(); i++) {
      for (int j = 0; j < gameBoard.getWidth(); j++) {
        this.allPossibleLocations.add(new Coord(j, i));
      }
    }
  }

  /**
   * generates positions for each part of the ship
   *
   * @param type of the ship
   * @param rand to randomize location and orientation
   *
   * @return list of coordinates for ship position
   */
  public List<Coord> generateShipPos(ShipType type, Random rand) {
    boolean validLocation = false;
    List<Coord> locationOfShip = new ArrayList<>();
    Coord headShip = null;
    boolean isVertical = false;

    while (!validLocation) {
      isVertical = rand.nextBoolean();
      headShip = new Coord(random.nextInt(gameBoard.getWidth()), random.nextInt(
          gameBoard.getHeight()));

      validLocation = (notOutOfBound(headShip, type, isVertical)
          && noneOverLap(headShip, type.getSize(), isVertical));
    }

    locationOfShip.add(headShip);

    if (isVertical) {
      for (int i = 1; i < type.getSize(); i++) {
        locationOfShip.add(new Coord(headShip.getX(), headShip.getY() + i));
      }
    } else {
      for (int i = 1; i < type.getSize(); i++) {
        locationOfShip.add(new Coord(headShip.getX() + i, headShip.getY()));
      }
    }

    shipPos.addAll(locationOfShip);
    return locationOfShip;
  }

  /**
   * checks to see if ship is out of bounds
   *
   * @param bowOfShip where the ship starts
   * @param type of the ship
   * @param isVert determines orientation of the ship
   *
   * @return a boolean to determine if ship is out of bounds
   */
  private boolean notOutOfBound(Coord bowOfShip, ShipType type, boolean isVert) {
    for (int i = 0; i < type.getSize(); i++) {
      if (isVert) {
        if (bowOfShip.getY() + i >= this.gameBoard.getHeight()) {
          return false;
        }
      } else {
        if (bowOfShip.getX() + i >= this.gameBoard.getWidth()) {
          return false;
        }
      }
    }

    return true;
  }

  /**
   * checks to make sure none of the ships overlaps
   *
   * @param coord of the bow of the ship
   * @param size of the ship
   * @param isVert determines orientation
   *
   * @return a boolean if some overlap and some don't
   */
  private boolean noneOverLap(Coord coord, int size, boolean isVert) {
    for (int i = 0; i < size; i++) {
      for (Coord existing : shipPos) {
        int x = coord.getX();
        int y = coord.getY();
        if (isVert) {
          x = x + i;
        } else {
          y = y + i;
        }

        if (x == existing.getX() && y == existing.getY()) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * initializes the board representation with just '-' as water
   */
  public void initializeBoardRep() {
    for (int i = 0; i < boardRep.length; i++) {
      for (int j = 0; j < boardRep[i].length; j++) {
        boardRep[i][j] = '-';
      }
    }
  }

  /**
   * places the given ship on the board at its location
   *
   * @param ship to be placed
   */
  public void placeShip(Ship ship) {
    List<Coord> locationOfShip = ship.getLocation();
    for (int i = 0; i < locationOfShip.size(); i++) {
      Coord coord = locationOfShip.get(i);
      boardRep[coord.getY()][coord.getX()] = 'S';
    }
  }

  /**
   * updates the board representation
   *
   * @param shots list of shots that were taken
   * @param boardRep as a char[][] to represent the board visually
   *
   * @return the boardRep
   */
  public char[][] updateBoardRep(List<Coord> shots, char[][] boardRep) {
    int x = 0;
    int y = 0;

    for (Coord coord : shots) {
      x = coord.getX();
      y = coord.getY();
      if (boardRep[y][x] == 'S') {
        boardRep[y][x] = 'H';
      } else if (boardRep[y][x] == '-') {
        boardRep[y][x] = 'M';
      }
    }

    return boardRep;
  }

  /**
   * getter for list of ships representing fleet
   *
   * @return fleet
   */
  public List<Ship> getFleet() {
    return this.fleet;
  }

  /**
   * getter for list of coord representing all ship positions
   *
   * @return shipPos
   */
  public List<Coord> getShipPos() {
    return this.shipPos;
  }

  /**
   * getter for 2D char array representing the board
   *
   * @return char[][] boardRep
   */
  public char[][] getBoardRep() {
    return this.boardRep;
  }
}
