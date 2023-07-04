package ra.shopping.repository;

import ra.shopping.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("select p from Product as p where p.name like concat('%',?1,'%') ")
    List<Product> findProductByNameIgnoreCase(String name);
    @Query(value = "select * from Product where catalogId = ?1", nativeQuery = true)
    List<Product> findProductByCatalog_Id(Long id);
    @Query("select p from Product as p where p.status = true and p.quantity <> 0 order  by p.star desc")
    Page<Product> getAllByStar(Pageable pageable);
    @Modifying
    @Query("update Product p set p.status = false where p.id = ?1")
    void changeStatusProduct(Long id);
    @Modifying
    @Query("update Product p set p.star = p.star + 1 where p.id = ?1")
    void updateStar(Long id);
    @Modifying
    @Query("update Product p set p.quantity = p.quantity - ?1 where p.id = ?2")
    void setQuantityProduct(int quantity,Long id);

    Product findProductById(Long id);
}
