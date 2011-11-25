import java.awt.Robot;
import java.awt.AWTException;
public class Runner {
  public static void main(String[] args) {
    try {
      Robot controller = new Robot();
      MadGod monitor   = new MadGod(controller);
      UserCharacter u  = new UserCharacter(controller);
      while(true) {
        u.processHealthData(monitor.getHP());
        u.processEnemyData(monitor.getEnemies());
        u.runActions();
      }
    } catch (AWTException e) {
      System.out.println("X11 could not be connected to");
      e.printStackTrace();
      System.exit(1);
    }
  }
}
