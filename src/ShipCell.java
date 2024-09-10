public class ShipCell {

   static final String ANSI_RED = "\u001B[1;31m";
   static final String ANSI_GREEN = "\033[1;32m";
   static final String ANSI_RESET = "\u001B[0m";

   Position position;
   int state = 1; // 0 is hit, 1 is undamaged
   int team = 0; // 0 is friendly, 1 is hostile
   String icon;
   String type = "t";

   ShipCell(Position position, int state, int team, String type) {
      this.position = position;
      this.state = state;
      this.team = team;
      this.type = type;
      this.icon = createIcon();
   }

   String createIcon() {
      String s = "[" + this.type.charAt(0) + "]" + ANSI_RESET;
      //String s = "[" + this.state + "]" + ANSI_RESET;
      if (this.state == 1) {
         s = this.team == 0 ? (ANSI_GREEN + s) : (ANSI_RED + s);
      } else if (this.state == 0) {
         s = ANSI_RED + "[*]" + ANSI_RESET;
      }
      return s;
   }

   public String getIcon() {
      return icon;
   }

   public int getTeam() {
      return this.team;
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

   public void displayIcon() {
      this.icon = createIcon();
      System.out.print(this.icon);
   }
}