import java.awt.Robot;
import java.util.*;
public class UserCharacter {

  private static int RADIUS  = 1;
  private static int DEGREES = 1;

  private static int AUTO_ATTACK_KEY = 70;

  private static int UP_KEY    = 87;
  private static int LEFT_KEY  = 65;
  private static int DOWN_KEY  = 83;
  private static int RIGHT_KEY = 68;

  private List<int[]>[] enemyQuadrants;
  private int health;

  private Robot control;

  public UserCharacter(Robot controller) {
    control = controller;
    triggerAutoAttack();
  }

  private void triggerAutoAttack() {
    control.keyPress(AUTO_ATTACK_KEY);
  }

  public void runActions() {
    int targetQuadrant = findBestQuadrant();
    moveTo(targetQuadrant);
  }

  private void moveTo(int targetQuadrant) {
    control.keyRelease(UP_KEY);
    control.keyRelease(LEFT_KEY);
    control.keyRelease(DOWN_KEY);
    control.keyRelease(RIGHT_KEY);
    if(targetQuadrant % 7 < 2) // 7, 0, 1
      control.keyPress(UP_KEY);
    if(targetQuadrant > 4) // 5, 6, 7
      control.keyPress(LEFT_KEY);
    if(targetQuadrant % 6 > 2) // 3, 4, 5
      control.keyPress(DOWN_KEY);
    if(targetQuadrant > 0 && targetQuadrant < 4) // 1, 2, 3
      control.keyPress(RIGHT_KEY);
  }

  private int findBestQuadrant() {
    int targetQuadrant = -1;
    int bestQuadrant   = -1;
    for(int i = 0; i < enemyQuadrants.length; i++) {
      List<int[]> quad = enemyQuadrants[i];
      if(bestQuadrant > quad.size()) {
        targetQuadrant = i;
        bestQuadrant   = quad.size();
      }
    }
    return targetQuadrant;
  }

  public void processEnemyData(List<int[]> data) {
    enemyQuadrants = new List[4];
    processCoordinateData(data, enemyQuadrants);
  }

  // Elements of data are pairs: [magnitude, angle]
  // Quadrants start at North as 0.
  // Go around to quadrant 7 which is north north west(just west of north)
  private void processCoordinateData(List<int[]> data, List<int[]>[] quadrants) {

    for(int i = 0; i < 4; i++)
      quadrants[i] = new ArrayList<int[]>();

    for(int[] coordinate: data) {
      int deg = coordinate[DEGREES];
      int quadrant  = (deg  + 15) % 360 / 45;
      quadrants[quadrant].add(coordinate);
    }
  }

  public void processHealthData(int hp) {
    health = hp;
  }
}
