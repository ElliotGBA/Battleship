import java.util.ArrayList;
import java.util.Scanner;

public class Board {

   static final String ANSI_RESET = "\u001B[0m";
   static final String ANSI_RED = "\u001B[31m";
   static final String ANSI_GREEN = "\u001B[32m";
   static final String ANSI_YELLOW = "\u001B[33m";
   static final String ANSI_BLUE = "\u001B[34m";

   int[][] board;
   ShipCell[][] shipMatrix;
   Scanner in = new Scanner(System.in);

   Board(int height, int width) {
      board = new int[height][width];
      shipMatrix = new ShipCell[height][width];
   }

   public void placeShip(Ship ship) {
      ArrayList<ShipCell> positions = ship.getShip();
      if (checkForShipCollisions(positions)) {
         for (int i = 0; i < ship.getShip().size(); i++) {
            int currentX = positions.get(i).getX();
            int currentY = positions.get(i).getY();
            shipMatrix[currentX][currentY] = ship.getShip().get(i);
         }
      } else {
         System.out.println(ANSI_RED + "That ship is colliding with another ship!" + ANSI_RESET);
         placeShip(new ShipBuilder(ship.getType(), ship.getLength(), 0).returnShip());
      }
   }

   private boolean checkForShipCollisions(ArrayList<ShipCell> positions) {
      for (ShipCell s : positions) {
         if (shipMatrix[s.getX()][s.getY()] != null) {
            return false;
         } else {
            return true;
         }
      }
      return false;
   }

   public int[][] getBoard() {
      return board;
   }

   public void displayBoard() {
      String horizontalBar = "";
      for (int i = -1; i < board.length-1; i++) {
         horizontalBar += ANSI_YELLOW + "[" + (i == -1 ? " " : i) + "]" + ANSI_RESET;
      }
      System.out.println(horizontalBar);
      for (int i = 0; i < board.length; i++) {
         for (int j = 0; j < board[i].length; j++) {
            printBoardTiles(i, j);
         }
         if (i != 0) {
            System.out.println();
         }
      }
   }

   String getCharForNumber(int i) {
      String s = i > 0 && i < 27 ? String.valueOf((char)(i + 'A' - 1)) : null;
      return ANSI_YELLOW + "[" + s + "]" + ANSI_RESET;
   }

   void printBoardTiles(int i, int j) {
      String waterCell = ANSI_BLUE + "[~]" + ANSI_RESET;
      if (j == 0 && i != 0) {
         System.out.print(getCharForNumber(i));
      } else if (i != 0 && j != 0 && shipMatrix[i][j] == null) {
         System.out.print(waterCell);
      } else if (shipMatrix[i][j] != null) {
         System.out.print(shipMatrix[i][j].getIcon());
      }
   }

}
