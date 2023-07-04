package ra.shopping.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;
    @DateTimeFormat(pattern = "yyyy-MM-dd") private String dateBuy ;
    private float total;
    private boolean status;
    @OneToMany(mappedBy = "orders",targetEntity = OrderDetail.class)
    private List<OrderDetail> orderDetails;
}
