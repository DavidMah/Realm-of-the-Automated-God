import java.awt.*;
import java.awt.image.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
public class MadGod {

  private static int HP_Y = 625;
  private static int HP_START_X   = 1015;
  private static int HP_END_X     = 1185;
  private static int HP_PRECISION = 6;

  private static int REST_TIME = 250;

  private static int MAP_START_X   = 1010;
  private static int MAP_LENGTH    = 190;
  private static int MAP_START_Y   = 375;
  private static int MAP_PRECISION = 5;

  private Robot control;

  public MadGod() throws AWTException{
    control = new Robot();
  }
  // Returns returning HP on a scale of 1 to 100
  public int getHP() {
    int result = 0;
    for(int x = HP_START_X; x < HP_END_X; x += HP_PRECISION) {
      Color current = control.getPixelColor(x, HP_Y);
      if(current.getRed() <= current.getBlue() || current.getRed() <= current.getGreen())
        break;
      result += HP_PRECISION;
    }
    return (int)((1.0 * result / (HP_END_X - HP_START_X)) * 100);
  }

  // Returns a List of enemy coordinates where current player is at (0, 0)
  public List<Point> getEnemies() {
    return getMapType(0);
  }

  // Returns a List of ally coordinates where current player is at (0, 0)
  // Not well accurate because allies can clump
  public List<Point> getAllies() {
    return getMapType(1);
  }

  private List<Point> getMapType(int colorType) {
    List<Point> results = new ArrayList<Point>();
    BufferedImage map = control.createScreenCapture(new Rectangle(MAP_START_X, MAP_START_Y, MAP_LENGTH, MAP_LENGTH));
    Raster pixels = map.getData();

    for(int x = 0; x < MAP_LENGTH; x += MAP_PRECISION) {
      for(int y = 0; y < MAP_LENGTH; y += MAP_PRECISION) {
        int[] pixel = new int[3];
        pixels.getPixel(x, y, pixel);

        boolean correct = true;
        for(int col = 0; col < 3; col++) {
          if(col == colorType) {
            if(pixel[col] < 240)
              correct = false;
          } else {
            if(pixel[col] > 15)
              correct = false;
          }
        }
        if(correct)
          results.add(new Point(x, y));
      }
    }
    return results;
  }

  public void rest() {
    control.delay(REST_TIME);
  }
}
