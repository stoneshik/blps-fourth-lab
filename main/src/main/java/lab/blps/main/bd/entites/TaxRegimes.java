package lab.blps.main.bd.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "tax_regimes")
public class TaxRegimes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotBlank
    @Column(name = "title", length = 100)
    private String title;
    @NotBlank
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Min(value = 100L)
    @Column(name = "max_annual_income_thousands")
    private Long maxAnnualIncomeThousands;
    @Min(value = 0L)
    @Column(name = "max_number_employees")
    private Long maxNumberEmployees;
}
