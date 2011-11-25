import java.awt.Robot;
import java.util.*;
public class UserCharacter {

  private static int RADIUS  = 1;
  private static int DEGREES = 1;

  private static int CENTER_X = 700;
  private static int CENTER_Y = 675;

  private static int AUTO_ATTACK_KEY = 74;
  private static int NEXUS_KEY = 70;
  private static int UP_KEY    = 87;
  private static int LEFT_KEY  = 65;
  private static int DOWN_KEY  = 83;
  private static int RIGHT_KEY = 68;

  private List<int[]>[] enemyQuadrants;
  private int health;
  private int targetQuadrant;
  private int targetAim;

  private Robot control;

  public UserCharacter(Robot controller) {
    control = controller;
    targetQuadrant = -1;
    triggerAutoAttack();
  }

  private void triggerAutoAttack() {
    control.keyPress(AUTO_ATTACK_KEY);
    control.keyRelease(AUTO_ATTACK_KEY);
  }

  public void runActions() {
    monitorHealth();
    moveTo(findBestQuadrant());
    aimAtEnemies();
  }

  private void moveTo(int tQuadrant) {
    System.out.println("moving to: " + tQuadrant);
    if(tQuadrant != targetQuadrant) {
      control.keyRelease(UP_KEY);
      control.keyRelease(LEFT_KEY);
      control.keyRelease(DOWN_KEY);
      control.keyRelease(RIGHT_KEY);
      if(tQuadrant % 7 < 2) // 7, 0, 1
        control.keyPress(UP_KEY);
      if(tQuadrant > 4) // 5, 6, 7
        control.keyPress(LEFT_KEY);
      if(tQuadrant % 6 > 2) // 3, 4, 5
        control.keyPress(DOWN_KEY);
      if(tQuadrant > 0 && tQuadrant < 4) // 1, 2, 3
        control.keyPress(RIGHT_KEY);
      targetQuadrant = tQuadrant;
    }
  }

  private int findBestQuadrant() {
    // System.out.println("--- finding best ---");
    int tQuadrant    = 0;
    int bestQuadrant = -1;
    for(int i = 0; i < enemyQuadrants.length; i++) {
      List<int[]> quad = enemyQuadrants[i];
      // System.out.println("i " + i + ", size " + quad.size());
      if(bestQuadrant < quad.size()) {
        tQuadrant = i;
        bestQuadrant   = quad.size();
      }
    }
    // System.out.println("Best " + tQuadrant);
    return tQuadrant;
  }

  private void aimAtEnemies() {
    int x = (int)(Math.sin(Math.toRadians(targetAim * 1.0)) * 150);
    int y = (int)(Math.cos(Math.toRadians(targetAim * 1.0)) * 150);
    control.mouseMove(CENTER_X + x, CENTER_Y + y);
  }

  private void monitorHealth() {
    if(health < 30) {
      control.keyPress(NEXUS_KEY);
      control.delay(50);
      control.keyRelease(NEXUS_KEY);
      System.exit(0);
    }
  }

  public void processEnemyData(List<int[]> data) {
    enemyQuadrants = new List[8];
    System.out.println("enemycount " + data.size());
    // System.out.println(Arrays.toString(data.get(0)));
    processMovementCoordinateData(data, enemyQuadrants);
    processAimingCoordinateData(data);
  }

  // Elements of data are pairs: [magnitude, angle]
  // Quadrants start at North as 0.
  // Go around to quadrant 7 which is north north west(just west of north)
  private void processMovementCoordinateData(List<int[]> data, List<int[]>[] quadrants) {

    for(int i = 0; i < 8; i++)
      quadrants[i] = new ArrayList<int[]>();

    for(int[] coordinate: data) {
      int deg = coordinate[DEGREES];
      int quadrant  = (deg  + 15) % 360 / 45;
      quadrants[quadrant].add(coordinate);
    }
  }

  private void processAimingCoordinateData(List<int[]> data) {
    if(data.isEmpty()) 
      return;
    int[] best = data.get(0);
    for(int[] coordinate: data) {
      if(coordinate[RADIUS] > best[RADIUS])
        best = coordinate;
    }
    targetAim = best[DEGREES];
  }

  public void processHealthData(int hp) {
    health = hp;
  }
}
