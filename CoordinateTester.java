import java.awt.*;
public class CoordinateTester {
  public static void main(String[] args) throws Exception {
      // PointerInfo p = MouseInfo.getPointerInfo();
      // System.out.println(p.getLocation());
      // Thread.sleep(1000);
      Robot r = new Robot();
      // int i = 700;
      // while(true){
      //   r.mouseMove(i, 500);
      //   System.out.println(i);
      //   i -= 20;
      //   r.delay(100);
      // }
      Toolkit tk = Toolkit.getDefaultToolkit();
      Dimension dim = tk.getScreenSize();
      System.out.println(dim.width);
      System.out.println(dim.height);
  }
}
