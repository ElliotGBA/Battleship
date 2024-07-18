import java.util.ArrayList;
import java.util.Scanner;

// this class is essentially a large function for better organization
// it takes parameters and returns a new ship

public class ShipBuilder {

   static final String ANSI_RED = "\u001B[31m";
   static final String ANSI_RESET = "\u001B[0m";
   static final String ANSI_YELLOW = "\u001B[33m";

   Ship ship;

   ShipBuilder(String type, int length, int team) {
      inputShipPositions(type, length, team);
   }

   public void inputShipPositions(String type, int length, int team) {
      System.out.println("Current ship: " + ANSI_YELLOW + type + ANSI_RESET +
            " (length: " + ANSI_YELLOW + length + ANSI_RESET + ")");
      System.out.print("Enter bow position (e.g. C3): ");
      int[] bowPositions = getPositionInput(type , length);
      System.out.print("Enter stern position (e.g. C3): ");
      int[] sternPositions = getPositionInput(type , length);
      ArrayList<Position> positions = calculateLine(bowPositions, sternPositions, length, type);
      createShip(positions, type, team, length);
   }

   private int[] getPositionInput(String type, int length) {
      Scanner in = new Scanner(System.in);
      String newPos = in.next();
      if (!isValidCoordinate(newPos)) {
         System.out.println(ANSI_RED + "That is not a valid coordinate, try again." + ANSI_RESET);
         System.out.print("Enter position (e.g. C3)");
         return getPositionInput(type, length);
      } else {
         int newPosX = newPos.charAt(0) - 'A';
         newPosX = newPosX == 0 ? newPosX : newPosX % 10 - 1;
         int newPosY = Character.getNumericValue(newPos.charAt(1))+1;
         return new int[]{newPosX, newPosY};
      }
   }

   public void createShip(ArrayList<Position> positions, String type, int team, int length) {
      ArrayList<ShipCell> shipCells = new ArrayList<ShipCell>();
      for (Position p : positions) {
         shipCells.add(new ShipCell(p, 1, team));
      }
      this.ship = new Ship(shipCells, type, length, team);
   }

   private ArrayList<Position> calculateLine(int[] bowPositions, int[] sternPositions, int length, String type) {
      // Check if x or y coordinate stays the same and length is correct
      if (verifyVerticalOrHorizontalPlacement(bowPositions, sternPositions, length)) {
         ArrayList<int[]> intPositions = new ArrayList<>();
         ArrayList<Position> positions = new ArrayList<>();
         if (bowPositions[0] == sternPositions[0]) {
            // The line is vertical
            int x = bowPositions[0];
            int startY = Math.min(bowPositions[1], sternPositions[1]);
            for (int i = 0; i < length; i++) {
               intPositions.add(new int[]{x, startY + i});
            }
         } else if (bowPositions[1] == sternPositions[1]) {
            // The line is horizontal
            int y = bowPositions[1];
            int startX = Math.min(bowPositions[0], sternPositions[0]);
            for (int i = 0; i < length; i++) {
               intPositions.add(new int[]{startX + i, y});
            }
         }
         for (int[] i : intPositions) {
            positions.add(new Position(i[0], i[1]));
         }
         return positions;
      } else {
         System.out.println(ANSI_RED + "That is not a valid ship placement! Try again!" + ANSI_RESET);
         return calculateLine(getPositionInput("bow", length), getPositionInput("stern", length), length, type);
      }
   }

   private boolean verifyVerticalOrHorizontalPlacement(int[] bowPositions, int[] sternPositions, int length) {
      int bowX = bowPositions[0];
      int bowY = bowPositions[1];
      int sternX = sternPositions[0];
      int sternY = sternPositions[1];
      int deltaX = Math.abs(sternX - bowX);
      int deltaY = Math.abs(sternY - bowY);
      //debugging
      //System.out.println("bowX: " + bowX + ", bowY: " + bowY);
      //System.out.println("sternX: " + sternX + ", sternY: " + sternY);
      //System.out.println("deltaX: " + deltaX + ", deltaY: " + deltaY);
      return (bowX == sternX && deltaY == length - 1) ||
            (bowY == sternY && deltaX == length - 1);
   }

   public boolean isValidCoordinate(String s) {
      return s != null && s.matches("^[a-zA-Z][0-9]$");
   }

   int getNumberForChar(char c) {
      if (Character.isDigit(c)) {
         return Character.getNumericValue(c);
      }
      return 0;
   }

   public Ship returnShip() {
      return this.ship;
   }

}
