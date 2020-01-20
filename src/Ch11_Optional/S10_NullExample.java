package Ch11_Optional;

public class S10_NullExample {

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

    public static void main(String[] args) {
        Person person = new Person(new Car(new Insurance("kaka")));
        System.out.println(getCarInsuranceName(person));

        person = new Person();
        System.out.println(getCarInsuranceName(person));  // null point exception
    }
}
