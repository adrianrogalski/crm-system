package crm.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "customers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "customer_type") // okresla nazwe kolumny okreslajca typ obiektu ktory dziedziczy po klasie
public abstract class Customer {
    @Id
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private List<Address> addresses;

    public Customer() {
        this.id = UUID.randomUUID();
        this.addresses = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }


    public void addAddress(Address address) {
        if(!addresses.contains(address)) {
            addresses.add(address);
        }
    }

    public List<Address> getAddress() {
        return new ArrayList<>(addresses);
    }

    // potrzebne do porownywania 2 obiektow na podstawie UUID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id.equals(customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
