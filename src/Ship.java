import java.util.ArrayList;

public class Ship {

   private ArrayList<ShipCell> ship;
   private String type;

   Ship(ArrayList<ShipCell> ship, String type) {
      this.ship = ship;
      this.type = type;
   }

   public boolean isDestroyed() {
      return ship.isEmpty();
   }

   public ArrayList<ShipCell> getShip() {
      return ship;
   }
   public void setShip(ArrayList<ShipCell> ship) {
      this.ship = ship;
   }

}
