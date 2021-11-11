package crm.entity;

import crm.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    private  final SessionFactory factory = HibernateUtil.getSessionFactory();

    @Test
    void shouldSavePersonInDatabase() {
        // given
        final var person = new Person("Jan", "Kowalski", new Pesel("23465783776"));
        // when
        final var session = factory.openSession();
        final var transaction = session.beginTransaction();
        session.save(person);
        session.flush();
        session.clear();
        // then
        final var readPerson= session.get(Person.class, person.getId());
        assertEquals(person, readPerson);
        transaction.rollback();
    }

}