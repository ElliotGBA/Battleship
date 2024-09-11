import java.util.*;

public class Board {

   static final String ANSI_RESET = "\u001B[0m";
   static final String ANSI_RED = "\u001B[1;31m";
   static final String ANSI_GREEN = "\033[1;32m";
   static final String ANSI_YELLOW = "\u001B[33m";
   static final String ANSI_YELLOW_BOLD = "\u001B[1;33m";
   static final String ANSI_BLUE = "\u001B[34m";
   static final String ANSI_BLACK = "\u001B[30m";

   String boardName;

   ShipCell[][] shipMatrix;
   boolean[][] shotsFired;
   Set<Position> occupiedPositions;

   Board(int height, int width, String boardName) {
      this.boardName = boardName;
      shipMatrix = new ShipCell[height][width];
      shotsFired = new boolean[height][width];
      occupiedPositions = new HashSet<>();
   }

   public void placeShip(Ship ship) {
      ArrayList<ShipCell> cells = ship.getShipCellList();
      for (ShipCell c : cells) {
         shipMatrix[c.getX()][c.getY()] = c;
         occupiedPositions.add(new Position(c.getX(), c.getY()));
      }
   }

   public Set<Position> getOccupiedPositions() {
      return occupiedPositions;
   }

   public void updateShipMatrix(int[] targetedPosition, ShipCell newCell) {
      //shipMatrix[targetedPosition[0]][targetedPosition[1]] = newCell;
      System.out.println("Updated cell state at position: " + targetedPosition[0] + ", " + targetedPosition[1]);
   }

   public void displayBoard(Map<String, Integer> shipTypes) {
      ArrayList<String> shipNames = new ArrayList<>();
      ArrayList<Integer> shipLengths = new ArrayList<>();
      for (Map.Entry<String, Integer> entry : shipTypes.entrySet()) {
         shipNames.add(entry.getKey());
         shipLengths.add(entry.getValue());
      }
      StringBuilder horizontalBar = new StringBuilder();
      for (int i = -1; i < shipMatrix.length - 1; i++) {
         horizontalBar.append(ANSI_YELLOW + "[").append(i == -1 ? " " : i).append("]").append(ANSI_RESET);
      }

      String friendlyBoard = ANSI_GREEN + boardName + ANSI_RESET;
      String enemyBoard = ANSI_RED + boardName + ANSI_RESET;
      String displayName = Objects.equals(this.boardName, "Your Board") ? friendlyBoard : enemyBoard;
      System.out.print("           " + displayName);
      System.out.print("\n" + horizontalBar);
      for (int i = 0; i < shipMatrix.length; i++) {
         for (int j = 0; j < shipMatrix[i].length; j++) {
            printBoardTiles(i, j);
         }
         // ship legend
         if (i == 0) {System.out.print(ANSI_YELLOW_BOLD + "  Ship Types:" + ANSI_RESET);}
         if (i > 0 && i < 6) {System.out.print("  " + shipNames.get(i - 1) + " [" + ANSI_YELLOW_BOLD + shipLengths.get(i - 1) + ANSI_RESET + "]");}
         if (i == 7) {System.out.print("  " + ANSI_GREEN + "[0]" + ANSI_RESET + " = friendly ship");}
         if (i == 8) {System.out.print("  " + ANSI_RED + "[*]" + ANSI_RESET + " = hit shot");}
         if (i == 9) {System.out.print(ANSI_BLACK + "  [X]" + ANSI_RESET + " = missed shot");}
         System.out.println();
      }
      System.out.println();
   }

   String getCharForNumber(int i) {
      String s = i > 0 && i < 27 ? String.valueOf((char) (i + 'A' - 1)) : null;
      return ANSI_YELLOW + "[" + s + "]" + ANSI_RESET;
   }

   void printBoardTiles(int i, int j) {
      String waterCell = ANSI_BLUE + "[~]" + ANSI_RESET;
      String missedWaterCell = ANSI_BLACK + "[X]" + ANSI_RESET;

      if (j == 0 && i != 0) { // edge of board
         System.out.print(getCharForNumber(i));
      } else if (shotsFired[i][j] && shipMatrix[i][j] == null) { // missed water shot
         System.out.print(missedWaterCell);
      } else if (i != 0 && j != 0 && (shipMatrix[i][j] == null || (shipMatrix[i][j].getState() == 1) && shipMatrix[i][j].getTeam() == 1)) { // print water tiles over water and hidden enemy ship cells
         System.out.print(waterCell);
      } else if (shipMatrix[i][j] != null) {
         shipMatrix[i][j].displayIcon();
      }
   }

   public ShipCell[][] getShipMatrix() {
      return shipMatrix;
   }
   public void setShotsFired(boolean[][] shotsFired) {
      this.shotsFired = shotsFired;
   }
}