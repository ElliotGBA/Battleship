import java.util.ArrayList;

public class Ship {

   private ArrayList<ShipCell> ship;
   private String type;
   private int length;
   private int team;

   Ship(ArrayList<ShipCell> ship, String type, int length, int team) {
      this.ship = ship;
      this.type = type;
      this.length = length;
      this.team = team;
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
   public String getType() {
      return type;
   }
   public int getLength() {
      return length;
   }

}
