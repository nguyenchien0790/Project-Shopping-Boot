package ra.shopping.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String name;
    private float price;
    private int quantity;
    private String description;
    private String image;
    private Long catalogId;
}
