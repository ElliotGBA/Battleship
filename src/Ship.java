import java.util.ArrayList;

public class Ship {

   private ArrayList<Position> ship;
   private String type;

   Ship(ArrayList<Position> ship, String type) {
      this.ship = ship;
      this.type = type;
   }

   public boolean isDestroyed() {
      return ship.isEmpty();
   }
   public ArrayList<Position> getShip() {
      return ship;
   }
   public void setShip(ArrayList<Position> ship) {
      this.ship = ship;
   }

}
