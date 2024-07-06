package lab.blps.main.bd.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lab.blps.main.bd.entites.enums.TaxpayerCategoryEnum;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Table(name = "taxpayer_category")
public class TaxpayerCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tax_regime_id", nullable = false)
    private TaxRegimes taxRegimes;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "taxpayer_category")
    private TaxpayerCategoryEnum taxpayerCategoryEnum;
}
