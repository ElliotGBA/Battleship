import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game {

   static Map<String, Integer> shipTypes = new HashMap<>();
   public static ArrayList<Ship> ships = new ArrayList<>();
   static Board playerBoard;
   static Board enemyBoard;

   public static void main(String[] args) {

      createShips();
      createGame();
      //getPlayerPositions(); // place player ships
      gameLoop();

   }

   static void gameLoop() {
      boolean playing = true;
      boolean playerTurn = true;
      while (playing) {
         displayGame(playerBoard, enemyBoard);
         //Firing firing = new Firing(playerTurn ? enemyBoard : playerBoard); // for real game (take turns back and forth)
         Firing firing = new Firing(enemyBoard); // for testing (only fire at enemy board)
         playerTurn = !playerTurn;
      }
   }

   static void createGame() {
      playerBoard = new Board(11, 11, "Your Board");
      enemyBoard = new Board(11, 11, "Enemy Board");
      // fill the enemy board
      for(Map.Entry<String, Integer> entry : shipTypes.entrySet()) {
         enemyBoard.placeShip(new ShipBuilder(entry.getKey(), entry.getValue(), 1, true, enemyBoard).getShip());
      }
      //getPlayerPositions();
   }

   static void getPlayerPositions() {
      displayGame(playerBoard, enemyBoard);
      for(Map.Entry<String, Integer> entry : shipTypes.entrySet()) {
         playerBoard.placeShip(new ShipBuilder(entry.getKey(), entry.getValue(), 0, false, playerBoard).getShip());
         displayGame(playerBoard, enemyBoard);
      }
   }

   static void createShips() {
      // Key = Name, Value = Length
      shipTypes.put("Patrol Boat", 2);
      shipTypes.put("Destroyer", 3);
      shipTypes.put("Submarine", 3);
      shipTypes.put("Battleship", 4);
      shipTypes.put("Carrier", 5);
   }

   static void displayGame(Board playerBoard, Board enemyBoard) {
      System.out.println();
      enemyBoard.displayBoard(shipTypes);
      System.out.println();
      playerBoard.displayBoard(shipTypes);
   }

   public static void addShip(Ship ship) {
      ships.add(ship);
   }
   public static ArrayList<Ship> getShips() {
      return ships;
   }

}
