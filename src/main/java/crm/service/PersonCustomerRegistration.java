package crm.service;

import crm.entity.Person;
import crm.exception.CustomerAlreadyExistsException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import static java.util.Objects.requireNonNull;

public class PersonCustomerRegistration {
    // dostep do sesji hibernate dla klasy
    private final SessionFactory sessionFactory;

    public PersonCustomerRegistration(SessionFactory sessionFactory) {
        // sesja nie moze byc nullem
        this.sessionFactory = requireNonNull(sessionFactory);
    }

    public RegisteredCustomerId registerPerson(RegisterPersonForm form) {

        // Otworzenie sesji
        final var session = sessionFactory.openSession();
        final var transaction = session.beginTransaction();

        // Walidacja last name + pesel
        if (personExists(form, session)) {
            throw new CustomerAlreadyExistsException("Customer exists, check data: " + form);
        }

        // zamiana form w encje Person
        final var person = Person.from(form);

        // zapisanie Person do DB
        session.save(person);

        // commit i zwrocenie id
        transaction.commit();
        session.close();
        return new RegisteredCustomerId(person.getId());
    }
    private Boolean personExists(RegisterPersonForm form, Session session) {
        return session.createQuery("select count(p) > 0 " + "from Person p " + "where p.lastName = ?1 and p.pesel.value = ?2", Boolean.class)
                .setParameter(1, form.getLastName())
                .setParameter(2, form.getPesel())
                .getSingleResult().;
    }
}

