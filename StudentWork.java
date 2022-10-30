package dsa_cw.student_4011322;

import java.awt.Color;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.HashMap;

public class StudentWork {
  
  /** Implementation provided : DO NOT CHANGE THIS METHOD. */
  public static Collection<Color> distinctColoursUsingList(LoadedImage image) {
    List<Color> collection = new ArrayList<Color>(image.getWidth() * image.getHeight());
    for(int x = 0; x < image.getWidth(); x++) {
      for(int y = 0; y < image.getHeight(); y++) {
        Color c = image.getColor(x, y);
        if (!collection.contains(c)) {
          collection.add(c);
        }
      }
    }
    return collection;
  }

  /** Student to provide implementation. */
  public static Collection<Color> distinctColoursUsingTreeSet(LoadedImage image) {
    Set<Color> colorTreeSet = new TreeSet<Color>();
    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        Color c = image.getColor(x,y);
        colorTreeSet.add(c);
      }
    }
    return colorTreeSet;
  }

  /** Student to provide implementation. */
  public static Collection<Color> distinctColoursUsingHashSet(LoadedImage image) {
    Set<Color> colorHashSet = new HashSet<Color>();
    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        Color c = image.getColor(x,y);
        colorHashSet.add(c);
      }
    }
    return colorHashSet;
  
  }

  /** Student to provide implementation. */
  public static Map<Color,Integer> frequencyCountUsingMap(LoadedImage image) {
    Map<Color,Integer> colorTreeMap = new TreeMap<Color,Integer>();
    Map<Color,Integer> colorHashMap = new HashMap<Color,Integer>();
    boolean hashMap = false; 
    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        Color c = image.getColor(x,y);
        if (hashMap == true) {
          if (colorHashMap.containsKey(c)) {
            Integer count = colorHashMap.get(c);
            colorHashMap.put(c, count + 1);
          } else {
              colorHashMap.put(c, 1);
          }
        } else {
            if (colorTreeMap.containsKey(c)) {
              Integer count = colorTreeMap.get(c);
              colorTreeMap.put(c, count + 1);
          } else {
              colorTreeMap.put(c, 1);
          }
        }
      }
    }
    if (hashMap == true) {
      System.out.println("HashMap: ");
      return colorHashMap;
    } else {
      System.out.println("TreeMap: ");
      return colorTreeMap;
    } 
  }
    

  /**  DO NOT CHANGE THIS METHOD */
  public static void main(String[] args) throws IOException {

    // ignores subdirectories in the target folder
    File[] files = new File("dsacw_images").listFiles(new FilenameFilter(){
      @Override
      public boolean accept(File dir, String name) {
        return new File(dir, name).isFile();
      }});
    Arrays.sort(files); // sorts by filenames which are numbered so that images are ordered by increasing size

    boolean suppressListCalculation = false; // set to true temporarily to skip the slow list computations 
    
    String[] summaries = new String[files.length + 1];
    summaries[0] = "image\tpixels\tunique\tlist\ttreeset\thashset\tfreqmap";
    
    int i = 1;
    for(File file : files) {
      LoadedImage image = new LoadedImage(file);   
      System.out.println( "IMAGE: " + image);

      StringBuilder summary = new StringBuilder(image.toString());
      long startTime, endTime, elapsed;
      Collection<Color> collection = Collections.emptyList();

      System.out.print("USING LIST:     ");
      if (!suppressListCalculation) {
        startTime = System.currentTimeMillis();
        collection = distinctColoursUsingList(image);
        endTime = System.currentTimeMillis();
        elapsed = endTime - startTime;
        System.out.print( collection.size() + " unique colours ");
        System.out.println( "identified in " + elapsed + " ms");
        summary.append("\t"); summary.append(collection.size());
        summary.append("\t"); summary.append(elapsed);
      } else {
        System.out.println("not done");
        summary.append("\tnot done");
        summary.append("\tnot done");        
      }
      
      System.out.print("USING TREE SET: ");
      startTime = System.currentTimeMillis();
      collection = distinctColoursUsingTreeSet(image);
      endTime = System.currentTimeMillis();
      elapsed = endTime - startTime;
      System.out.print( collection.size() + " unique colours ");
      System.out.println( "identified in " + elapsed + " ms");
      summary.append("\t"); summary.append(elapsed);

      System.out.print("USING HASH SET: ");
      startTime = System.currentTimeMillis();
      collection = distinctColoursUsingHashSet(image);
      endTime = System.currentTimeMillis();
      elapsed = endTime - startTime;
      System.out.print( collection.size() + " unique colours ");
      System.out.println( "identified in " + elapsed + " ms");
      summary.append("\t"); summary.append(elapsed);

      startTime = System.currentTimeMillis();
      Map<Color,Integer> map = frequencyCountUsingMap(image);
      endTime = System.currentTimeMillis();
      elapsed = endTime - startTime;
      System.out.println( "Frequency map generated in " + elapsed + " ms");
      summary.append("\t"); summary.append(elapsed);

  
      Map<Color,Integer> sortedMap = Util.orderByValues(map);
      Util.showFrequencyCountLogarithmic(sortedMap, "Image " + i + " computed");
      

      summaries[i++] = summary.toString();     
    }
    
    System.out.println();
    for(String s : summaries) {
      System.out.println(s);
    }

  }

}
