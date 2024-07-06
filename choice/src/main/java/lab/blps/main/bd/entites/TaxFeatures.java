package lab.blps.main.bd.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lab.blps.main.bd.entites.enums.TaxFeatureEnum;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Table(name = "tax_features")
public class TaxFeatures {
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
    @Column(name = "tax_feature")
    private TaxFeatureEnum taxFeatureEnum;
}
