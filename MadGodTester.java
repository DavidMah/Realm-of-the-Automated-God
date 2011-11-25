import java.util.*;
import java.awt.*;
public class MadGodTester {
  public static void main(String[] args) {
    try {
      Robot r = new Robot();
      MadGod m = new MadGod(r);
      System.out.println("health " + m.getHP());
      System.out.println("enemyCount " + m.getEnemies().size());
      int x = (int)(Math.sin(Math.toRadians(225 * 1.0)) * 150);
      int y = (int)(Math.cos(Math.toRadians(225 * 1.0)) * 150);
      // System.out.println("x " + x);
      // System.out.println("y " + y);
      r.mouseMove(1005 + 92, 375 + 92);
    } catch (AWTException e) {
      e.printStackTrace();
    }
  }
}
