package ra.shopping.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.shopping.model.Product;

import java.util.List;

public interface IProductService extends IGenericService<Product,Long>{
    List<Product> findProductByName(String name);
    List<Product> findProductByCatalog_Id(Long id);
    Page<Product> getAllByStar(Pageable pageable);
    void changeStatusProduct(Long id);
    void updateStar(Long id);
    void setQuantityProduct(int quantity,Long id);
}
