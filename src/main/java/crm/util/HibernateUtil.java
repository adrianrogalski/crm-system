package crm.util;

import crm.util.entity.TestEntity;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    /* konstruktor statyczny sluzy do inicjalizacji pol statycznych jezeli te wymagaja wielu linijek kodu do
    wykonania tej czynnosci */
    static {
        /* jezeli loadHibProp nie zaladuje sie przechwytuje ten wyjatek zeby
        zakańczyc w tym momencie dzialanie programu bo oznacza to ze hibernate sie nie zaladuje */
        try {
            final var configuration = new Configuration();
            configuration.setProperties(loadHibernateProperties());
            configureEntities(configuration);
            sessionFactory = configuration.buildSessionFactory();
        } catch (IOException e) {
            e.printStackTrace();
            /* wyjatek wyrzucany w momencie kiedy aplikacja jest w nieprawidlowym stanie np. nie zaladowaly sie propertiesy
            i program nie powinnien dalej funkcjonowac w takim stanie gdyz reszta kodu jest od tego zalezna */
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private static void configureEntities(Configuration configuration) {
        configuration.addAnnotatedClass(TestEntity.class);
    }

    private static Properties loadHibernateProperties() throws IOException {
        final var properties = new Properties();
        /* metoda getClassLoader wczytuje klasy ale ma rowniez dostep do zasobow dzieki czemu bede mogl przypisac do
        obiektu properties*/
        properties.load(HibernateUtil.class.getClassLoader().getResourceAsStream("hibernate.properties"));
        return properties;
    }
}
