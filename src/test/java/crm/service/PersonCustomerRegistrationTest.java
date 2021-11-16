package crm.service;

import crm.exceptions.CustomerAlreadyExistsException;
import crm.util.HibernateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonCustomerRegistrationTest {

    private final PersonCustomerRegistration registration =
            new PersonCustomerRegistration(HibernateUtil.getSessionFactory());

    @Test
    void shouldRegisterPersonCustomer() {
        // given
        final var form = new RegisterPersonForm("Jan", "Kowalski", "99827364712");

        // when
        final var registeredCustomerId = registration.registerPerson(form);

        // then
        assertNotNull(registeredCustomerId.getId());
    }

    @Test
    void shouldNotRegisterPersonIfLastNameAndPeselAlreadyExists() {
        // given
        final var form = new RegisterPersonForm("Jan", "Nowak", "99762938761");
        registration.registerPerson(form);

        // when & then
        assertThrows(CustomerAlreadyExistsException.class, () -> registration.registerPerson(form));
    }
}
