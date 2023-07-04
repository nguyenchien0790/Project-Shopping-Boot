package ra.shopping.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogDTO {
    private String name;
    private String description;
    private String image;
    private String country;
}
