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

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Position position = (Position) o;
      return x == position.x && y == position.y;
   }

   @Override
   public int hashCode() {
      return Objects.hash(x, y);
   }
}
