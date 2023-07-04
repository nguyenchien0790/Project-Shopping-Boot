package ra.shopping.service;

import ra.shopping.model.CartItem;
import ra.shopping.model.Product;
import ra.shopping.model.User;
import java.util.List;
import java.util.Optional;


public interface ICartItemService extends IGenericService<CartItem, Long> {
    List<CartItem> getAllByUser(Long userId);
    void deleteAllByUserId(Long userId);
    void deleteCartItemByProductId(Long id);
    Optional<CartItem> findByUserAndProduct(User u, Product p);
    List<CartItem> getCartItemsByUserId(Long userId);
    void deleteCartItemByUserAndProduct(User user, Product product);

    List<CartItem> findAllCartItemByUser(User u);
}
