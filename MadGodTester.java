import java.util.*;
import java.awt.*;
public class MadGodTester {
  public static void main(String[] args) {
    try {
      Robot r = new Robot();
      MadGod m = new MadGod(r);
      System.out.println("health " + m.getHP());
      System.out.println("enemyCount " + m.getEnemies().size());
    } catch (AWTException e) {
      e.printStackTrace();
    }
  }
}
