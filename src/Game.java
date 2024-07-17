import java.util.HashMap;
import java.util.Map;

public class Game {

   public static void main(String[] args) {

      Board board = new Board(11, 11);
      board.displayBoard();

      Map<String, Integer> shipTypes = new HashMap<>();
      shipTypes.put("Patrol Boat", 2);
      shipTypes.put("Destroyer", 3);
      shipTypes.put("Submarine", 3);
      shipTypes.put("Battleship", 4);
      shipTypes.put("Carrier", 5);
      for(Map.Entry<String, Integer> entry : shipTypes.entrySet()) {
         board.placeShip(new ShipBuilder(entry.getKey(), entry.getValue(), 0).returnShip());
         board.displayBoard();
      }

   }

}
