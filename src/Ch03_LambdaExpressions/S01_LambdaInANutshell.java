package Ch03_LambdaExpressions;

import java.util.*;
import java.util.function.Predicate;

/**
 * P01_LambdaInANutshell
 */
public class S01_LambdaInANutshell {
  enum COLOR {GREEN, RED};

  class Apple {
    COLOR color;
    Integer weight;

    Apple(COLOR color, int weight) {
      this.color = color;
      this.weight = weight;
    }

    /**
     * @return the color
     */
    public COLOR getColor() {
      return color;
    }

    /**
     * @return the weight
     */
    public Integer getWeight() {
      return weight;
    }

    @Override
    public String toString() {
      return this.color + ":" + this.weight;
    }
  }

  // abstract to list
  public static <T> List<T> filter(List<T> list, Predicate<T> p) {
    List<T> result = new ArrayList<>();
    for (T e: list) {
        if (p.test(e)) {
            result.add(e);
        }
    }
    return result;
}

  public static void main(String[] args) {
    S01_LambdaInANutshell proc = new S01_LambdaInANutshell();

    ArrayList<Apple> appleList = new ArrayList<>();
    appleList.add(proc.new Apple(COLOR.RED, 58));
    appleList.add(proc.new Apple(COLOR.GREEN, 129));

    // Comparator constructed by anonymous function
    Comparator<Apple> byWeight = new Comparator<Apple>(){
      @Override
      public int compare(Apple a1, Apple a2) {
        return a1.getWeight().compareTo(a2.getWeight());
      }
    };

    // Comparator with lambda
    Comparator<Apple> byWeight_byLambda = (Apple a1, Apple a2) -> a2.getWeight().compareTo(a1.getWeight());

    Collections.sort(appleList, byWeight);
    System.out.println(appleList);

    Collections.sort(appleList, byWeight_byLambda);
    System.out.println(appleList);

    List<Apple> filteredAppleList = filter(appleList, (Apple a) -> COLOR.GREEN.equals(a.getColor()));
    System.out.println(filteredAppleList);
  }
  
}