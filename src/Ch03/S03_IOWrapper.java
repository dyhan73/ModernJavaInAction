package Ch03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * S03_IOWrapper
 */
public class S03_IOWrapper {

  public String processFile(String fName) throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(fName))) {
      return br.readLine();
    }
  }

  public static void main(String[] args) {
    S03_IOWrapper proc = new S03_IOWrapper();
    try {
      System.out.println(proc.processFile("/etc/hosts"));
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}