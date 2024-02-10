package liubomyr.stepanenko.bookstore.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import liubomyr.stepanenko.bookstore.model.type.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "orders")
@SQLDelete(sql = "UPDATE orders SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(nullable = false)
    private BigDecimal total;
    @Column(nullable = false)
    private LocalDateTime orderDate;
    @Column(nullable = false)
    private String shippingAddress;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @EqualsAndHashCode.Exclude
    private Set<OrderItem> orderItems = new LinkedHashSet<>();
    @Column(nullable = false)
    private boolean isDeleted = false;
}
