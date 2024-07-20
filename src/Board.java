import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {

   static final String ANSI_RESET = "\u001B[0m";
   static final String ANSI_RED = "\u001B[1;31m";
   static final String ANSI_GREEN = "\033[1;32m";
   static final String ANSI_YELLOW = "\u001B[33m";
   static final String ANSI_YELLOW_BOLD = "\u001B[1;33m";
   static final String ANSI_BLUE = "\u001B[34m";

   int[][] board;
   ShipCell[][] shipMatrix;
   String boardName;
   Set<Position> occupiedPositions;

   Board(int height, int width, String boardName) {
      this.boardName = boardName;
      board = new int[height][width];
      shipMatrix = new ShipCell[height][width];
      occupiedPositions = new HashSet<>();
   }

   public void placeShip(Ship ship) {
      ArrayList<ShipCell> positions = ship.getShip();
      for (int i = 0; i < ship.getShip().size(); i++) {
         int currentX = positions.get(i).getX();
         int currentY = positions.get(i).getY();
         shipMatrix[currentX][currentY] = ship.getShip().get(i);
         occupiedPositions.add(new Position(currentX, currentY));
      }
   }

   public int[][] getBoard() {
      return board;
   }

   public Set<Position> getOccupiedPositions() {
      return occupiedPositions;
   }

   public void displayBoard(Map<String, Integer> shipTypes) {
      ArrayList<String> shipNames = new ArrayList<>();
      ArrayList<Integer> shipLengths = new ArrayList<>();
      for (Map.Entry<String, Integer> entry : shipTypes.entrySet()) {
         shipNames.add(entry.getKey());
         shipLengths.add(entry.getValue());
      }
      String horizontalBar = "";
      for (int i = -1; i < board.length - 1; i++) {
         horizontalBar += ANSI_YELLOW + "[" + (i == -1 ? " " : i) + "]" + ANSI_RESET;
      }

      String friendlyBoard = ANSI_GREEN + boardName + ANSI_RESET;
      String enemyBoard = ANSI_RED + boardName + ANSI_RESET;
      String displayName = this.boardName == "Your Board" ? friendlyBoard : enemyBoard;
      System.out.print("           " + displayName);
      System.out.print("\n" + horizontalBar);
      for (int i = 0; i < board.length; i++) {
         for (int j = 0; j < board[i].length; j++) {
            printBoardTiles(i, j);
         }
         if (i == 0) {
            System.out.print(ANSI_YELLOW_BOLD + "  Ship Types:" + ANSI_RESET);
         }
         if (i > 0 && i < 6) {
            System.out.print("  " + shipNames.get(i - 1) + " [" + ANSI_YELLOW_BOLD + shipLengths.get(i - 1) + ANSI_RESET + "]");
         }
         // legend
         if (i == 7) {
            System.out.print("  " + ANSI_GREEN + "[0]" + ANSI_RESET + " = friendly ship");
         }
         if (i == 8) {
            System.out.print("  " + ANSI_RED + "[*]" + ANSI_RESET + " = hit shot");
         }
         if (i == 9) {
            System.out.print("  [ ] = missed shot");
         }
         System.out.println();
      }
   }

   String getCharForNumber(int i) {
      String s = i > 0 && i < 27 ? String.valueOf((char) (i + 'A' - 1)) : null;
      return ANSI_YELLOW + "[" + s + "]" + ANSI_RESET;
   }

   void printBoardTiles(int i, int j) {
      String waterCell = ANSI_BLUE + "[~]" + ANSI_RESET;
      String enemyCell = ANSI_RED + "[*]" + ANSI_RESET;
      if (j == 0 && i != 0) {
         System.out.print(getCharForNumber(i));
      } else if (i != 0 && j != 0 && shipMatrix[i][j] == null) {
         System.out.print(waterCell);
      } else if (shipMatrix[i][j] != null && shipMatrix[i][j].team == 0) {
         System.out.print(shipMatrix[i][j].getIcon());
      } else if (shipMatrix[i][j] != null && shipMatrix[i][j].team == 1) {
         System.out.print(enemyCell);
      }
   }
}