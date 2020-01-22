package Ch11_Optional;

import java.util.Arrays;

public class S1_NullExample {

    static class Person {
        private Car car;

        public Person(Car car) {
            this.car = car;
        }

        public Person() {
        }

        public Car getCar() {
            return car;
        }
    }

    static class Car {
        private Insurance insurance;

        public Car(Insurance insurance) {
            this.insurance = insurance;
        }

        public Insurance getInsurance() {
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

    static String getCarInsuranceName(Person person) {
        return person.getCar().getInsurance().getName();
    }

    static String getCarInsuranceNameWithNullCheck(Person person) {
        if (person != null) {
            Car car = person.getCar();
            if (car != null) {
                Insurance insurance = car.getInsurance();
                if (insurance != null) {
                    return insurance.getName();
                }
            }
        }
        return "Unknown";
    }

    public static void main(String[] args) {
        Person person1 = new Person(new Car(new Insurance("kaka")));
        System.out.println(getCarInsuranceName(person1));

        Person person2 = new Person();

        try {
            System.out.println(getCarInsuranceName(person2));  // null point exception
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        // using null check version
        System.out.println(getCarInsuranceNameWithNullCheck(person1));
        System.out.println(getCarInsuranceNameWithNullCheck(person2));
    }
}
