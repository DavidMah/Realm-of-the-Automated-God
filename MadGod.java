import java.awt.*;
import java.awt.image.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
public class MadGod {

  private static int RED   = 0;
  private static int GREEN = 1;
  private static int BLUE  = 2;

  private static int LEVEL_Y = 600;
  private static int HP_Y    = 625;
  private static int MP_Y    = 650;
  private static int BAR_START_X  = 1015;
  private static int BAR_WIDTH    = 170;
  private static int BAR_PRECISION = 1;

  private static int REST_TIME = 100;

  private static int MAP_START_X   = 1005;
  private static int MAP_LENGTH    = 185;
  private static int MAP_START_Y   = 375;
  private static int MAP_PRECISION = 3;

  private Robot control;

  public MadGod(Robot controller) {
    control = controller;
  }
  // Returns HP on a scale of 1 to 100
  public int getHP() {
    return getBar(HP_Y, RED);
  }

  // Returns Level Progress on a scale of 1 to 100
  public int getLevelProgress() {
    return getBar(LEVEL_Y, GREEN);
  }

  public int getMP() {
    return getBar(MP_Y, BLUE);
  }

  private int getBar(int height, int colorType) {
    Rectangle bounds  = new Rectangle(BAR_START_X, height - 1, BAR_WIDTH, 2);
    BufferedImage bar = control.createScreenCapture(bounds);
    Raster pixels = bar.getData();

    int result = 0;
    for(int x = 0; x < BAR_WIDTH; x += BAR_PRECISION) {
      int[] pixel = new int[3];
      pixels.getPixel(x, 1, pixel);
      if(!barMaintained(pixel, colorType))
        break;
      result += BAR_PRECISION;
    }
    return (int)((1.0 * result / BAR_WIDTH) * 100);
  }

  private boolean barMaintained(int[] pixel, int colorType) {
    boolean correct = true;
    for(int col = 0; col < 3; col++) {
      if(colorType != col &&pixel[col] >= pixel[colorType])
        correct = false;
    }
    return correct;
  }

  // Returns a List of enemy coordinates where current player is at (0, 0)
  public List<int[]> getEnemies() {
    return MadGod.cartesianToPolar(getMapType(RED));
  }

  // Returns a List of ally coordinates where current player is at (0, 0)
  // Not well accurate because allies can clump
  public List<int[]> getAllies() {
    return MadGod.cartesianToPolar(getMapType(GREEN));
  }

  private List<Point> getMapType(int colorType) {
    List<Point> results = new ArrayList<Point>();
    Rectangle bounds     = new Rectangle(MAP_START_X, MAP_START_Y, MAP_LENGTH, MAP_LENGTH);
    BufferedImage map   = control.createScreenCapture(bounds);
    Raster pixels = map.getData();

    for(int x = 0; x < MAP_LENGTH; x += MAP_PRECISION) {
      for(int y = 0; y < MAP_LENGTH; y += MAP_PRECISION) {
        int[] pixel = new int[3];
        pixels.getPixel(x, y, pixel);

        if(mapTargetIdentified(pixel, colorType)) {
          int itemX = x - (MAP_LENGTH / 2);
          int itemY = (y - (MAP_LENGTH / 2)) * -1;
          results.add(new Point(itemX, itemY));
        }
      }
    }
    return results;
  }

  private boolean mapTargetIdentified(int[] pixel, int colorType) {
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
    return correct;
  }

  public static List<int[]> cartesianToPolar(List<Point> ps) {
    List<int[]> results = new ArrayList<int[]>();
    for(int i = 0; i < ps.size(); i++) {
      results.add(cartesianToPolar(ps.get(i)));
    }
    return results;
  }

  public static int[] cartesianToPolar(Point p) {
    double radians = (Math.atan2(p.getX(), p.getY()));
    int degrees    = (int)(radians / (2 * Math.PI) * 360);
    if(degrees < 0) {
      degrees *= -1;
      degrees  = 180 - degrees;
      degrees += 180;
    }

    int distance = (int)(Math.sqrt(p.getX() * p.getX() + p.getY() * p.getY()));
    int[] polar  = {distance, degrees};
    return polar;
  }

  public void rest() {
    control.delay(REST_TIME);
  }

}
