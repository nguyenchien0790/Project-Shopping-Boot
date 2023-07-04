package ra.shopping.repository;

import ra.shopping.model.CartItem;
import ra.shopping.model.Product;
import ra.shopping.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query(value = "select * from cartitem where user_id = ?1", nativeQuery = true)
    List<CartItem> getAllByUser(Long userId);
    List<CartItem> getCartItemsByUserId(Long userId);
    void deleteCartItemByUserAndProduct(User user, Product product);

    @Query(value = "delete from CartItem where product_Id = ?1", nativeQuery = true)
    void deleteCartItemByProductId(Long id);

    @Modifying
    @Query(value = "delete from CartItem where user_Id = ?1", nativeQuery = true)
    void deleteAllByUserId(Long userid);

    Optional<CartItem> findByUserAndProduct(User u, Product p);

//    List<CartItem> findAllByUser(User u);
}
