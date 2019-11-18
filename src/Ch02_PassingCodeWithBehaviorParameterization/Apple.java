package Ch02_PassingCodeWithBehaviorParameterization;

public class Apple {
    AppleColor color;
    int weight;

    public Apple(AppleColor color, int weight) {
        this.color = color;
        this.weight = weight;
    }

    public AppleColor getColor() {
        return color;
    }

    public Apple setColor(AppleColor color) {
        this.color = color;
        return this;
    }

    public int getWeight() {
        return weight;
    }

    public Apple setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "color=" + color +
                ", weight=" + weight +
                '}';
    }
}
