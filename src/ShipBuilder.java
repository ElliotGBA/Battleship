import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class ShipBuilder {

   static final String ANSI_RED = "\u001B[31m";
   static final String ANSI_RESET = "\u001B[0m";
   static final String ANSI_YELLOW = "\u001B[33m";

   Ship ship;
   Random random = new Random();
   Board board;

   ShipBuilder(String type, int length, int team, boolean isAI, Board board) {
      this.board = board;
      if (isAI) {
         generateRandomShipPositions(type, length, team);
      } else {
         inputShipPositions(type, length, team);
      }
   }

   public void inputShipPositions(String type, int length, int team) {

      System.out.println("Current ship: " + ANSI_YELLOW + type + ANSI_RESET +
            " (length: " + ANSI_YELLOW + length + ANSI_RESET + ")");
      System.out.print("Enter bow position (e.g. C3): ");
      int[] bowPositions = getPositionInput(type, length, team);
      System.out.print("Enter stern position (e.g. C3): ");
      int[] sternPositions = getPositionInput(type, length, team);
      ArrayList<Position> positions = calculateLine(bowPositions, sternPositions, length, type, team);

      if (positions != null && checkForShipCollisions(positions)) {
         createShip(positions, type, team, length);
         System.out.println("\n\n\n\n\n\n\n");
      } else {
         System.out.println(ANSI_RED + "That ship is colliding with another ship! Try again." + ANSI_RESET);
         inputShipPositions(type, length, team);
      }

   }

   private int[] getPositionInput(String type, int length, int team) {
      Scanner in = new Scanner(System.in);
      String newPos = in.next();
      if (!isValidCoordinate(newPos)) {
         System.out.println(ANSI_RED + "That is not a valid coordinate, try again." + ANSI_RESET);
         System.out.print("Enter position (e.g. C3): ");
         return getPositionInput(type, length, team);
      } else {
         int newPosX = newPos.toUpperCase().charAt(0) - 'A' + 1;
         int newPosY = Integer.parseInt(newPos.substring(1)) + 1;
         return new int[]{newPosX, newPosY};
      }
   }

   public void createShip(ArrayList<Position> positions, String type, int team, int length) {
      ArrayList<ShipCell> shipCells = new ArrayList<>();
      for (Position p : positions) {
         shipCells.add(new ShipCell(p, 1, team, type));
      }
      this.ship = new Ship(shipCells, type, length, team);
      board.placeShip(this.ship);
      Game.addShip(this.ship);
   }

   private ArrayList<Position> calculateLine(int[] bowPositions, int[] sternPositions, int length, String type, int team) {
      if (verifyVerticalOrHorizontalPlacement(bowPositions, sternPositions, length)) {
         ArrayList<int[]> intPositions = new ArrayList<>();
         ArrayList<Position> positions = new ArrayList<>();
         if (bowPositions[0] == sternPositions[0]) {
            int x = bowPositions[0];
            int startY = Math.min(bowPositions[1], sternPositions[1]);
            for (int i = 0; i < length; i++) {
               intPositions.add(new int[]{x, startY + i});
            }
         } else if (bowPositions[1] == sternPositions[1]) {
            int y = bowPositions[1];
            int startX = Math.min(bowPositions[0], sternPositions[0]);
            for (int i = 0; i < length; i++) {
               intPositions.add(new int[]{startX + i, y});
            }
         }
         for (int[] i : intPositions) {
            positions.add(new Position(i[0], i[1]));
         }
         if (checkForShipCollisions(positions)) {
            return positions;
         }
      } else {
         System.out.println(ANSI_RED + "That is not a valid ship placement! Try again!" + ANSI_RESET);
         inputShipPositions(type, length, team);
      }
      return null;
   }

   private boolean verifyVerticalOrHorizontalPlacement(int[] bowPositions, int[] sternPositions, int length) {
      int bowX = bowPositions[0];
      int bowY = bowPositions[1];
      int sternX = sternPositions[0];
      int sternY = sternPositions[1];
      int deltaX = Math.abs(sternX - bowX);
      int deltaY = Math.abs(sternY - bowY);
      return (bowX == sternX && deltaY == length - 1) ||
            (bowY == sternY && deltaX == length - 1);
   }

   private void generateRandomShipPositions(String type, int length, int team) {
      int[] bowPositions, sternPositions;
      boolean validPlacement = false;

      while (!validPlacement) {
         bowPositions = getRandomPosition();
         sternPositions = getRandomPosition();
         if (verifyVerticalOrHorizontalPlacement(bowPositions, sternPositions, length)) {
            ArrayList<Position> positions = calculateLine(bowPositions, sternPositions, length, type, team);
            if (positions != null && checkForShipCollisions(positions)) {
               createShip(positions, type, team, length);
               validPlacement = true;
            }
         }
      }
   }

   private int[] getRandomPosition() {
      int x = random.nextInt(10) + 1;
      int y = random.nextInt(10) + 1;
      return new int[]{x, y};
   }

   public boolean isValidCoordinate(String s) {
      return s != null && s.matches("^[a-zA-Z][0-9]+$");
   }

   public Ship getShip() {
      return this.ship;
   }

   private boolean checkForShipCollisions(ArrayList<Position> positions) {
      Set<Position> occupiedPositions = board.getOccupiedPositions();
      for (Position pos : positions) {
         if (occupiedPositions.contains(pos)) {
            return false;
         }
      }
      return true;
   }
}
