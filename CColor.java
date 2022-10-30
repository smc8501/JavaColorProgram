package dsa_cw.student_4011322;

import java.awt.Color;

/** DO NOT CHANGE THIS CLASS */
public class CColor extends Color implements Comparable<CColor>{
  private static final long serialVersionUID = 1L;

  public CColor(int rgb) {
    super(rgb);
  }

  private int compare(int v1, int v2) {
    if ( v1 < v2) {
      return -1;
    } else if ( v1 > v2) {
      return 1;
    } else {
      return 0;
    }
  }
  
  @Override
  public int compareTo(CColor o) { 
    int c = compare(getRed(), o.getRed());
    if (c == 0) {
      c = compare(getGreen(), o.getGreen());
      if (c == 0) {
        c = compare(getBlue(), o.getBlue());
      }
    }
    return c;
  }

  @Override
  public String toString() {
    return "RGB[" + this.getRed() + "," + this.getGreen() + "," + this.getBlue() +"]";
  }
  
}
