import java.util.Scanner;

public class Firing {

   static final String ANSI_RED = "\u001B[31m";
   static final String ANSI_RESET = "\u001B[0m";
   static final String ANSI_GREEN = "\033[1;32m";
   static final String ANSI_YELLOW = "\u001B[33m";

   private final Board targetedBoard;
   private final boolean[][] shotsFired;

   Firing(Board targetedBoard) {
      this.targetedBoard = targetedBoard;
      this.shotsFired = targetedBoard.getShotsFired();
      handleFiring();
   }

   void handleFiring() {
      int[] targetedPosition = getPositionInput();
      if (checkForHit(targetedPosition)) {
         updateShotsFired(targetedPosition);
         handleHit(targetedPosition);
      } else {
         handleMiss();
      }
   }

   boolean checkForHit(int[] targetedPosition) {
      ShipCell[][] shipMatrix = targetedBoard.getShipMatrix();
      if (isRepeatShot(targetedPosition)) {
         System.out.println(ANSI_RED + "You already shot there! Pick a different firing position." + ANSI_RESET);
         handleFiring(); // if shot is repeat, restart shooting phase
      }
      return shipMatrix[targetedPosition[0]][targetedPosition[1]] != null;
   }

   boolean isRepeatShot(int[] targetedPosition) {
      return shotsFired[targetedPosition[0]][targetedPosition[1]];
   }

   void updateShotsFired(int[] targetedPosition) {
      boolean[][] newShotsFired = shotsFired;
      newShotsFired[targetedPosition[0]][targetedPosition[1]] = true;
      targetedBoard.setShotsFired(newShotsFired);
   }

   void updateCellState(int[] targetedPosition) {
      ShipCell[][] shipMatrix = targetedBoard.getShipMatrix();
      shipMatrix[targetedPosition[0]][targetedPosition[1]].setState(0);
   }

   void handleHit(int[] targetedPosition) {
      System.out.println(ANSI_GREEN + "Good job! You scored a hit!" + ANSI_RESET);
      updateCellState(targetedPosition);
   }

   void handleMiss() {
      System.out.println(ANSI_RED + "Oops, you missed!" + ANSI_RESET);
   }

   private int[] getPositionInput() {
      Scanner in = new Scanner(System.in);
      System.out.print("Where would you like to fire? (e.g." + ANSI_YELLOW +  "C3" + ANSI_RESET + "): ");
      String newPos = in.next();
      if (!isValidCoordinate(newPos)) {
         System.out.println(ANSI_RED + "That is not a valid coordinate, try again." + ANSI_RESET);
         System.out.print("Enter position (e.g. C3): ");
         return getPositionInput();
      } else {
         int newPosX = newPos.toUpperCase().charAt(0) - 'A' + 1;
         int newPosY = Integer.parseInt(newPos.substring(1)) + 1;
         return new int[]{newPosX, newPosY};
      }
   }

   boolean isValidCoordinate(String s) {
      return s != null && s.matches("^[a-zA-Z][0-9]+$");
   }

}
