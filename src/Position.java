import java.util.Objects;

public class Position {

   private int x = -1;
   private int y = -1;

   Position(int x, int y) {
      this.x = x;
      this.y = y;
   }

   public int getX() { return this.x; }
   public int getY() { return this.y; }
   public void setX(int x) { this.x = x; }
   public void setY(int y) { this.y = y; }

}
