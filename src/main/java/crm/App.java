package crm;

import crm.service.PersonCustomerRegistration;
import crm.service.RegisterPersonForm;
import crm.service.RegisteredCustomerId;
import crm.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        final SessionFactory factory = HibernateUtil.getSessionFactory();
        PersonCustomerRegistration registration = new PersonCustomerRegistration(factory);
        Scanner scanner = new Scanner(System.in);
        System.out.println("\tCRM System");
        System.out.println("---------------");
        while (true) {
            System.out.println("Options: ");
            System.out.println("1. Add business client");
            System.out.println("2. Quit");
            int userInput;
            if (scanner.hasNextInt()) {
                userInput = scanner.nextInt();
                scanner.nextLine();
            }
            else {
                System.out.println("wrong input");
                break;
            }
            if (userInput == 1) {
                System.out.println("first name: ");
                String name = scanner.next();
                System.out.println("last name: ");
                String lastName = scanner.next();
                System.out.println("personal identity number: ");
                String pesel = scanner.next();
                RegisterPersonForm newClientForm = new RegisterPersonForm(name, lastName, pesel);
                RegisteredCustomerId registeredCustomerId = registration.registerPerson(newClientForm);
                successfulRegistrationPrompt(registeredCustomerId);
            }
            if (userInput == 2) {
                System.exit(0);
            }
        }
    }

    public static void successfulRegistrationPrompt(RegisteredCustomerId id) {
        System.out.println("\n---------------");
        System.out.println("New client was successfully registered");
        System.out.println("Client id number in the database system: " + id);
        System.out.println("---------------\n");
    }
}
