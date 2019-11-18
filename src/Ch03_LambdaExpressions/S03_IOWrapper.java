package Ch03_LambdaExpressions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * S03_IOWrapper
 */
public class S03_IOWrapper {

  private String processFile(String fName) throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(fName))) {
      return br.readLine();
    }
  }

  @FunctionalInterface
  private interface BufferedReaderProcessor {
    String process(BufferedReader b) throws IOException;
  }
  
  private String processFile02(String fName, BufferedReaderProcessor p) throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(fName))) {
      return p.process(br);
    }
  }

  public static void main(String[] args) {
    S03_IOWrapper proc = new S03_IOWrapper();
    String fName = "/etc/hosts";
    try {
      String result = proc.processFile(fName);
      System.out.println(result);

      // behavior parameterization
      result = proc.processFile02(fName, (BufferedReader br) -> br.readLine() + "\n" + br.readLine());
      System.out.println(result);

      result = proc.processFile02(fName, (BufferedReader br) -> {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i< 10; i++) {
          sb.append(br.readLine());
          sb.append("\n");
        }
        return sb.toString();
      });
      System.out.println(result);

    } catch (Exception e) {
      System.out.println(e);
    }
  }
}