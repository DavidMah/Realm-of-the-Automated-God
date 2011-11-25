import java.awt.Robot;
import java.util.*;
public class UserCharacter {

  private static int RADIUS  = 1;
  private static int DEGREES = 1;

  private List<int[]>[] enemyQuadrants;
  private int health;

  private Robot control;

  public UserCharacter(Robot controller) {
    control = controller;
  }

  public void runActions() {
    int targetQuadrant = findBestQuadrant();
    moveTo(targetQuadrant);
  }

  private void moveTo(int targetQuadrant) {
    
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
  // Quadrants start at North north east(just east of north) as 0.
  // Go around to quadrant 7 which is north north west(just west of north)
  private void processCoordinateData(List<int[]> data, List<int[]>[] quadrants) {

    for(int i = 0; i < 4; i++)
      quadrants[i] = new ArrayList<int[]>();

    for(int[] coordinate: data) {
      int deg = coordinate[DEGREES];
      int quadrant  = deg / 45;
      quadrants[quadrant].add(coordinate);
    }
  }

  public void processHealthData(int hp) {
    health = hp;
  }
}
