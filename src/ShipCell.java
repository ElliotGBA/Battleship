public class ShipCell {

   static final String ANSI_RED = "\u001B[1;31m";
   static final String ANSI_GREEN = "\033[1;32m";
   static final String ANSI_RESET = "\u001B[0m";

   Position position;
   int state = 1; // 0 is hit, 1 is undamaged
   int team = 0; // 0 is friendly, 1 is hostile
   String icon = createIcon();

   ShipCell(Position position, int state, int team) {
      this.team = team;
      this.position = position;
      this.state = state;
   }

   String createIcon() {
      return team == 0 ? (ANSI_GREEN + "[0]" + ANSI_RESET) : (ANSI_RED + "[*]" + ANSI_RESET);
   }
   public String getIcon() {
      return icon;
   }
   public int getState() {
      return state;
   }
   public void setState(int state) {
      this.state = state;
   }
   public int getX() {
      return this.position.getX();
   }
   public int getY() {
      return this.position.getY();
   }

}
