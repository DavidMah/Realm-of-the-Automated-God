import java.util.*;
import java.awt.Point;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.AWTException;
public class Runner {
  public static void main(String[] args) {
    try {
      Point[] screenPosition = getScreenCoordinates();
      Point topLeft     = screenPosition[0];
      Point bottomRight = screenPosition[1];

      System.out.println("Proceeding to Play Game");

      Robot controller = new Robot();
      MadGod monitor   = new MadGod(controller, topLeft, bottomRight);
      UserCharacter u  = new UserCharacter(controller, topLeft, bottomRight);
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

  // Asks user to put mouse in top left and bottom right of game screen
  // So that Bot can get screen coordinates
  public static Point[] getScreenCoordinates() {
    try {
      System.out.println("Place Mouse in Top Left");
      Thread.sleep(5000);
      Point topLeft = MouseInfo.getPointerInfo().getLocation();
      System.out.println("Recorded Top Left: " + topLeft);

      System.out.println("Place Mouse in Bottom Right");
      Thread.sleep(3000);
      Point bottomRight = MouseInfo.getPointerInfo().getLocation();
      System.out.println("Recorded Bottom Right: + bottomRight");

      Point[] positions = new Point[2];
      positions[0] = topLeft;
      positions[1] = bottomRight;
      return positions;

    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
    return null;
  }
}
