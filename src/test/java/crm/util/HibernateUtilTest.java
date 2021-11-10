package crm.util;

import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HibernateUtilTest {
    @Test
    // testuje tu konfiguracje sesji hibernatowej, polaczenie z baza h2, probe wykonania jakiegos zapytania
    void testConnection() {
        // given
        final var sessionFactory = HibernateUtil.getSessionFactory();
        final var session = sessionFactory.openSession();
        // when
        // tworze jakies proste zapytanie aby sprawdzic czy jest jakies polaczenie z baza
        final NativeQuery query = session.createSQLQuery("SELECT 1");
        final List resultList = query.getResultList();
        // oczekuje 1 rezultatu z 1 kolumna z 1 wartoscia: 1

        // then
        assertEquals(1,resultList.get(0));

    }

}