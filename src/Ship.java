import java.util.ArrayList;

public class Ship {

   private ArrayList<ShipCell> ship;
   private final String type;
   private final int length;
   private final int team;

   static final String ANSI_RESET = "\u001B[0m";
   static final String ANSI_GREEN = "\033[1;32m";

   Ship(ArrayList<ShipCell> ship, String type, int length, int team) {
      this.ship = ship;
      this.type = type;
      this.length = length;
      this.team = team;
   }

   public void isDestroyed() {
      boolean isDestroyed = true;
      for (ShipCell c : ship) {
         if (c.getState() == 1) {
            isDestroyed = false;
            break;
         }
      }
      if (isDestroyed) {
         System.out.println(ANSI_GREEN + "You sunk their " + this.type + "!" + ANSI_RESET);
         for (ShipCell c : ship) {
            c.setIsShipDestroyed(true);
         }
      }
   }

   public ArrayList<ShipCell> getShipCellList() {
      return ship;
   }

   public ShipCell getShipCellAtIndex(int index) {
      return ship.get(index);
   }

}
