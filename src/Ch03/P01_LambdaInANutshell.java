package Ch03;

import java.util.*;

/**
 * P01_LambdaInANutshell
 */
public class P01_LambdaInANutshell {
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

  public static void main(String[] args) {
    P01_LambdaInANutshell proc = new P01_LambdaInANutshell();

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
  }
  
}