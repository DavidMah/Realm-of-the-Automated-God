import java.util.*;
import java.awt.Robot;
import java.awt.AWTException;
public class Runner {
  public static void main(String[] args) {
    try {
      Robot controller = new Robot();
      controller.delay(5000);
      MadGod monitor   = new MadGod(controller);
      UserCharacter u  = new UserCharacter(controller);
      while(true) {
        List<int[]> enemies = monitor.getEnemies();
        // List<int[]> enemies = new ArrayList<int[]>();
        // int[] stub = {15, 320};
        // enemies.add(stub);
        u.processHealthData(monitor.getHP());
        u.processEnemyData(enemies);
        u.runActions();

        monitor.rest();
      }
    } catch (AWTException e) {
      System.out.println("X11 could not be connected to");
      e.printStackTrace();
      System.exit(1);
    }
  }
}
