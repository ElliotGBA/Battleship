import java.util.HashMap;
import java.util.Map;

public class Game {

   public static void main(String[] args) {

      Map<String, Integer> shipTypes = new HashMap<>();
      shipTypes.put("Patrol Boat", 2);
      shipTypes.put("Destroyer", 3);
      shipTypes.put("Submarine", 3);
      shipTypes.put("Battleship", 4);
      shipTypes.put("Carrier", 5);

      Board playerBoard = new Board(11, 11);
      playerBoard.displayBoard(shipTypes);

      for(Map.Entry<String, Integer> entry : shipTypes.entrySet()) {
         playerBoard.placeShip(new ShipBuilder(entry.getKey(), entry.getValue(), 0).returnShip());
         playerBoard.displayBoard(shipTypes);
      }

   }

}
