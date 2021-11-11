package crm.entity;

import com.neovisionaries.i18n.CountryCode;
import crm.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    private  final SessionFactory factory = HibernateUtil.getSessionFactory();

    private Session session;
    private Transaction transaction;

    @BeforeEach
    void before() {
        session = factory.openSession();
        transaction = session.beginTransaction();
    }

    @AfterEach
    void after() {
        transaction.rollback();
        session.close();
    }

    // TESTY

    @Test
    void shouldSavePersonInDatabase() {
        // given
        final var person = new Person("Jan", "Kowalski", new Pesel("23465783776"));
        // when
        saveAndFlush(person);
        // then
        final var readPerson= session.get(Person.class, person.getId());
        assertEquals(person, readPerson);
        transaction.rollback();
    }
    @Test
    void shouldAddAddress() {
        // given
        final var customer = new Person("Jan", "Kowalski", new Pesel("23465783776"));
        final var address = new Address("str", "Wawa", "11-300", CountryCode.PL);
        customer.addAddress(address);

        // when
        saveAndFlush(customer);

        // then
        final var readCustomer = session.get(Customer.class, customer.getId());
        assertEquals(customer.getAddress(), readCustomer.getAddress());
    }

    // METODY POMOCNICZE
    private void saveAndFlush(Person person) {
        session.save(person);
        session.flush();
        session.clear();
    }
}