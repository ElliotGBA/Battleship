import java.util.ArrayList;

public class Ship {

   private ArrayList<ShipCell> ship;
   private final String type;
   private final int length;
   private final int team;

   Ship(ArrayList<ShipCell> ship, String type, int length, int team) {
      this.ship = ship;
      this.type = type;
      this.length = length;
      this.team = team;
   }

   public boolean isDestroyed() {
      return ship.isEmpty();
   }

   public ArrayList<ShipCell> getShipCellList() {
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
   public int getTeam() {
      return team;
   }
}
