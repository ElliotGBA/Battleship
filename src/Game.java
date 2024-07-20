import java.util.HashMap;
import java.util.Map;

public class Game {

   static Map<String, Integer> shipTypes = new HashMap<>();
   static Board playerBoard;
   static Board enemyBoard;

   public static void main(String[] args) {

      shipTypes.put("Patrol Boat", 2);
      shipTypes.put("Destroyer", 3);
      shipTypes.put("Submarine", 3);
      shipTypes.put("Battleship", 4);
      shipTypes.put("Carrier", 5);

      createGame();

   }

   static void createGame() {
      playerBoard = new Board(11, 11, "Your Board");
      enemyBoard = new Board(11, 11, "Enemy Board");
      for(Map.Entry<String, Integer> entry : shipTypes.entrySet()) {
         enemyBoard.placeShip(new ShipBuilder(entry.getKey(), entry.getValue(), 1, true, enemyBoard).getShip());
      }
      getPlayerInput();
   }

   static void getPlayerInput() {
      displayGame(playerBoard, enemyBoard);
      for(Map.Entry<String, Integer> entry : shipTypes.entrySet()) {
         playerBoard.placeShip(new ShipBuilder(entry.getKey(), entry.getValue(), 0, false, playerBoard).getShip());
         displayGame(playerBoard, enemyBoard);
      }
   }

   static void displayGame(Board playerBoard, Board enemyBoard) {
      System.out.println();
      enemyBoard.displayBoard(shipTypes);
      System.out.println();
      playerBoard.displayBoard(shipTypes);
   }

}
