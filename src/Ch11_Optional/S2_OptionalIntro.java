package Ch11_Optional;

import java.util.Optional;

public class S2_OptionalIntro {
    static class Person {
        private Optional<Car> car;

        public Person(Car car) {
            this.car = Optional.of(car);
        }

        public Optional<Car> getCar() {
            return car;
        }
    }

    static class Car {
        private Optional<Insurance> insurance;

        public Car(Insurance insurance) {
            this.insurance = Optional.of(insurance);
        }

        public Optional<Insurance> getInsurance() {
            return insurance;
        }
    }

    static class Insurance {
        private String name;

        public Insurance(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) {

    }
}
