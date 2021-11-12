package crm.entity;

import crm.service.RegisterPersonForm;
import crm.util.ArgumentValidator;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import static crm.util.ArgumentValidator.validate;
import static java.util.Objects.*;

@Entity
@DiscriminatorValue("PERSON") // ustala wartosc w kolumnie customer_table
public class Person extends Customer {
    private String firstName;
    private String lastName;
    // wzorzec sprawdzania zgodny z OOP
    @Embedded // pola z klasy pesel beda zapisane wraz z polami w tej samej tabeli
    private Pesel pesel;

    // only for hibernate use
    private Person() {
    }

    // Zalezy mi na tym aby w momencie utworzenia obiektu tej klasy wszystkie jego pola byly zgodne z zalozeniami
    // biznesowymi wiec okreslam jakie pola sa wymagane
    public Person(String firstName, String lastName, Pesel pesel) {
        // dlatego waliduje
        // walidacja
        validate(firstName != null || !firstName.isBlank(), "first name is invalid " + firstName);
        validate(lastName != null || !lastName.isBlank(), "first name is invalid " + lastName);
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = requireNonNull(pesel, "pesel is null");
    }


    public static Person from(RegisterPersonForm form) {
        return new Person(form.getFirstName(), form.getLastName(), new Pesel(form.getPesel()));
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Pesel getPesel() {
        return pesel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Person person = (Person) o;
        return firstName.equals(person.firstName) && lastName.equals(person.lastName) && pesel.equals(person.pesel);
    }

    @Override
    public int hashCode() {
        return hash(super.hashCode(), firstName, lastName, pesel);
    }
}
