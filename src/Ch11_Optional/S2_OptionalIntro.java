package Ch11_Optional;

import java.util.Optional;

public class S2_OptionalIntro {
    static class Person {
        private Optional<Car> car;

        public Optional<Car> getCar() {
            return car;
        }
    }

    static class Car {
        private Optional<Insurance> insurance;

        public Optional<Insurance> getInsurance() {
            return insurance;
        }
    }

    static class Insurance {
        private String name;

        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) {

    }
}
