package dsa_cw.student_4011322;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/** DO NOT CHANGE THIS CLASS */
public class Util {

  /** Not used. */
  public static Map<Color,Integer> compress(Map<Color,Integer> map) {
    Map<Color,Integer> compressed = new TreeMap<Color,Integer>();
    int step = map.size() / 100;
    Iterator<Map.Entry<Color,Integer>> it = map.entrySet().iterator();
    for (int i = 0; i < 100; i++) {      
      int count = 0;
      int r = 0;
      int g = 0;
      int b = 0;
      for (int j = 0; j < step; j++) {
        Entry<Color, Integer> me = it.next();
        count += me.getValue();
        Color c = me.getKey();
        r += c.getRed();
        g += c.getGreen();
        b += c.getBlue();
      }
      count = count / step;
      r = r / step;
      g = g / step;
      b = b / step;
      CColor key = new CColor(new Color(r, g, b).getRGB());
      compressed.put(key, count);
    }
    return compressed;
  }
  
  public static Map<Color,Integer> orderByValues(Map<Color,Integer> map) {
    Map<Color,Integer> output = new LinkedHashMap<Color,Integer>();

    int algorithm = 0;
    switch( algorithm) {
    case 0:
    {
      // create a TreeSet that sorts the entries in the input map 
      Set<Map.Entry<Color, Integer>> set = map.entrySet();
      TreeSet<Map.Entry<Color, Integer>> sort = new TreeSet<Map.Entry<Color, Integer>>( 
          new Comparator<Map.Entry<Color, Integer>>() {
            @Override
            public int compare(Entry<Color, Integer> o1, Entry<Color, Integer> o2) {
              if (o1.getValue() > o2.getValue()) {
                return -1;
              } else if (o1.getValue() < o2.getValue()) {
                return 1;
              } else {
                return ((CColor) o1.getKey()).compareTo((CColor)o2.getKey());
              }
            }
          });
      sort.addAll(set);
      // iterate over the sorted set putting the entries into the output map in the order they come
      for(Map.Entry<Color,Integer> e : sort) {
        output.put(e.getKey(), e.getValue());
      }
    }
    break;
    case 1:
    {
      // repeatedly remove the maximum item in the input and put it in the output map
      while(!map.isEmpty()) {
        int max = 0;
        Color maxC = null;
        for(Color c : map.keySet()) {
          int got = map.get(c);
          if ( maxC == null || got > max) {
            max = got;
            maxC = c;
          }
        }
        output.put(maxC, max);
        map.remove(maxC);
      }
    }
    break;
    default:
      throw new RuntimeException();
    }
    return output;
  }
  
  public static void showFrequencyCountLogarithmic(Map<Color,Integer> map, String name) {
    if (map.isEmpty()) {
      System.out.println( "Cannot display empty frequency map!");
      return;
    }
    final int imageHeight = 400;
    double max = 0;
    for(int i : map.values()) {
      double j = Math.log(i);
      if ( j > max) { max = j; }
    }
    double scale = imageHeight / (double)max;
    
    Iterator<Integer> countIterator = map.values().iterator();
    while(countIterator.hasNext()) {
      int count = countIterator.next();
      if ( scale * Math.log(count) < 1) { countIterator.remove(); }
    }

    int width = map.size();
    int barWidth = 1;
    if ( width < 1200) { barWidth = 1200 / width; }

    //System.out.println( width + " " + max + " " + scale + " " + (scale * max));
    BufferedImage output = new BufferedImage(width * barWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
    Graphics g = output.getGraphics();
    int x = 0;
    for(Color c : map.keySet()) {
      g.setColor(c);
      int h = (int) (scale * Math.log(map.get(c)));
      g.fillRect(x, imageHeight - h, barWidth, h);
      x += barWidth;
    }
    SwingUtilities.invokeLater(
        new Runnable() {
          @Override
          public void run() {
            JFrame frame = new JFrame("Frequency map: " + name);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JLabel imageComponent = new JLabel(new ImageIcon(output));
            JScrollPane sp = new JScrollPane( imageComponent);
            frame.add(sp);
            frame.pack();
            frame.setVisible(true);
          }
        });
  }

}
