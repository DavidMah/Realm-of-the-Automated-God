import java.util.*;
import java.awt.*;
public class MadGodTester {
  public static void main(String[] args) {
    try {
      Robot r = new Robot();

      Point[] positions = getScreenCoordinates();
      Point t = positions[0];
      Point l = positions[1];

      MadGod m = new MadGod(r, t, l);
      System.out.println("health " + m.getHP());
    } catch (AWTException e) {
      e.printStackTrace();
    }
  }
  public static Point[] getScreenCoordinates() {
    try {
      System.out.println("Place Mouse in Top Left");
      Thread.sleep(5000);
      Point topLeft = MouseInfo.getPointerInfo().getLocation();
      System.out.println("Recorded Top Left: " + topLeft);

      System.out.println("Place Mouse in Bottom Right");
      Thread.sleep(3000);
      Point bottomRight = MouseInfo.getPointerInfo().getLocation();
      System.out.println("Recorded Bttom Right: + bottomRight");

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
