package dsa_cw.student_4011322;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/** DO NOT CHANGE THIS CLASS */
public class LoadedImage {

  private final String name;
  private final BufferedImage image;
  
  public LoadedImage(final File file) throws IOException {
    this.image = ImageIO.read(file);
    this.name = file.getName();
  }
  
  public Color getColor(final int x, final int y) {
    return new CColor(this.image.getRGB(x, y));
  }

  public int getWidth() {
    return this.image.getWidth();
  }

  public int getHeight() {
    return this.image.getHeight();
  }

  @Override
  public String toString() {
    return this.name + " - \t" + (getWidth() * getHeight());// + "\t pixels";
  }
}
