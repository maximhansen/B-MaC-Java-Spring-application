package be.kdg.client.domain.account;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "company_tbl")
@Getter @Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany
    private List<Client> client;

    @Pattern(regexp = "^[A-Z]{2}\\d{10}$", message = "VAT number must match the specified pattern.")
    @Column(nullable = false, unique = true)
    private String VATNumber;

    @OneToMany(mappedBy = "company")
    private List<Address> address;

    public Company() {
    }

    public Company(String name, String VATNumber, Address address) {
        this.name = name;
        this.VATNumber = VATNumber;
        this.address = new ArrayList<>();
        this.address.add(address);
    }
}
