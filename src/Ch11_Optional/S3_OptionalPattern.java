package Ch11_Optional;

import org.w3c.dom.ls.LSOutput;

import javax.swing.text.html.Option;
import java.sql.SQLOutput;
import java.util.Optional;

public class S3_OptionalPattern {
    public static void main(String[] args) {
        s1131_createOptional();

        // Legacy style usage
        S1_NullExample.Insurance lInsurance = new S1_NullExample.Insurance("Purumi");
        S1_NullExample.Car lCar = new S1_NullExample.Car(lInsurance);
        S1_NullExample.Person lPerson = new S1_NullExample.Person(lCar);
        System.out.println(lPerson);

        // Optional style usage
        S2_OptionalIntro.Insurance insurance = new S2_OptionalIntro.Insurance("DongBu");
        S2_OptionalIntro.Car car = new S2_OptionalIntro.Car(insurance);
        S2_OptionalIntro.Person person = new S2_OptionalIntro.Person(car);
        System.out.println(person);

        s11321_nullChecking(lPerson);
        s11322_extractUsingMap(insurance);
        s11331_flatMap(person);


    }

    private static String getCarInsuranceName(Optional<S2_OptionalIntro.Person> person) {
        return person.flatMap(S2_OptionalIntro.Person::getCar)
                .flatMap(S2_OptionalIntro.Car::getInsurance)
                .flatMap(S2_OptionalIntro.Insurance::getName)
                .orElse("Unknown");
    }

    private static void s1131_createOptional() {
        // empty Optional
        Optional<S2_OptionalIntro.Car> optCar = Optional.empty();
        System.out.println(optCar);

        // Optional with object
        S2_OptionalIntro.Insurance insurance = new S2_OptionalIntro.Insurance("DongBu");
        S2_OptionalIntro.Car car = new S2_OptionalIntro.Car(insurance);
        System.out.println(car);

        optCar = Optional.of(car);
        System.out.println(optCar);

        // try Optional.of(null)
        S2_OptionalIntro.Car nullCar = null;
        try {
            optCar = Optional.of(nullCar);  // Occur NullPointerException
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(optCar);
    }

    private static void s11321_nullChecking(S1_NullExample.Person lPerson) {
        // extract value
        System.out.println(lPerson.getCar().getInsurance().getName());
        // have to null check
        S1_NullExample.Person p2 = new S1_NullExample.Person(null);
        System.out.println(p2.getCar()); // null
        try {
            System.out.println(p2.getCar().getInsurance()); // null pointer exception
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void s11322_extractUsingMap(S2_OptionalIntro.Insurance insurance) {
        // extract using Optional.map()
        Optional<S2_OptionalIntro.Insurance> optionalInsurance = Optional.ofNullable(null);
        try {
            System.out.println(optionalInsurance.get().getName());
        } catch (Exception e) {
            System.out.println(e);
        }

        optionalInsurance = Optional.ofNullable(insurance);
        Optional<Optional<String>> name = optionalInsurance.map(S2_OptionalIntro.Insurance::getName);
        System.out.println(name);

        S2_OptionalIntro.Car car1 = new S2_OptionalIntro.Car(null);
        S2_OptionalIntro.Person person1 = new S2_OptionalIntro.Person(car1);
//        System.out.println(person1.getCar().getInsurance().getName());

        person1.getCar()
                .map(S2_OptionalIntro.Car::getInsurance)
                .ifPresent(System.out::println);
    }

    private static void s11331_flatMap(S2_OptionalIntro.Person person) {
        System.out.println(person);
        // problem of .map()
        Optional<S2_OptionalIntro.Person> optionalPerson = Optional.of(person);
        /// compile error because ::getCar returns Optional<> not Car object
//        Optional<Optional<String>> name = optionalPerson.map(S2_OptionalIntro.Person::getCar)
//                .map(S2_OptionalIntro.Car::getInsurance)
//                .map(S2_OptionalIntro.Insurance::getName);

        // using lambda to using Optional.get() - but, return type is multi level Optional due to using .map()
        Optional<Optional<String>> name = optionalPerson.map(S2_OptionalIntro.Person::getCar)
                .map(o -> {
                    S2_OptionalIntro.Car car1 = o.get();
                    return car1.getInsurance();
                })
                .map(o -> {
                    S2_OptionalIntro.Insurance ins = o.get();
                    return ins.getName();
                });
        System.out.println(name);

        // Solution! using flatMap()
        Optional<String> name2 = optionalPerson.flatMap(S2_OptionalIntro.Person::getCar)
                .flatMap(S2_OptionalIntro.Car::getInsurance)
                .flatMap(S2_OptionalIntro.Insurance::getName);
        System.out.println(name2);
        name2.ifPresent(System.out::println);

        // find insurance company name with Optional
        System.out.println(getCarInsuranceName(Optional.of(person)));
    }
}
