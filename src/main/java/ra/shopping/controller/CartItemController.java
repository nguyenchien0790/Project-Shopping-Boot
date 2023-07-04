package ra.shopping.controller;

import ra.shopping.dto.request.CartItemDTO;
import ra.shopping.dto.response.ResponseMessage;
import ra.shopping.model.CartItem;
import ra.shopping.model.Product;
import ra.shopping.model.User;
import ra.shopping.service.ICartItemService;
import ra.shopping.service.IProductService;
import ra.shopping.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/cartItems")
public class CartItemController {
    @Autowired
    ICartItemService cartItemService;
    @Autowired
    IUserService userService;
    @Autowired
    IProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<List<CartItem>> getCartItemÒUser(@PathVariable("id") Long id) {
        List<CartItem> list = cartItemService.getCartItemsByUserId(id);
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    @PostMapping("create")
    public ResponseEntity<ResponseMessage> createOrderDetail(@RequestBody CartItemDTO cartItemDTO) {
        User user = userService.findById(cartItemDTO.getUserId());
        Product product = productService.findById(cartItemDTO.getProductId());
        Optional<CartItem> cartItemOptional = cartItemService.findByUserAndProduct(user, product);
        if(cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cartItem.setQuantity(cartItem.getQuantity() + cartItemDTO.getQuantity());
            cartItemService.save(cartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUser(userService.findById(cartItemDTO.getUserId()));
            cartItem.setProduct(productService.findById(cartItemDTO.getProductId()));
            cartItem.setProductName(productService.findById(cartItemDTO.getProductId()).getName());
            cartItem.setPrice(productService.findById(cartItemDTO.getProductId()).getPrice());
            cartItem.setQuantity(cartItemDTO.getQuantity());
            cartItemService.save(cartItem);
        }
        return new ResponseEntity<>(new ResponseMessage("create is success!"), HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<ResponseMessage> deleteByProductId(@RequestParam("userId") Long userId,@RequestParam("productId") Long productId) {
        User user = userService.findById(userId);
        Product product = productService.findById(productId);
        cartItemService.deleteCartItemByUserAndProduct(user,product);
        return new ResponseEntity<>(new ResponseMessage("delete is success!"), HttpStatus.OK);
    }
}
