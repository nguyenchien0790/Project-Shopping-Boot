package ra.shopping.controller;


import org.springframework.transaction.annotation.Transactional;
import ra.shopping.dto.request.OrderDetailDTO;
import ra.shopping.dto.response.ResponseMessage;
import ra.shopping.model.*;
import ra.shopping.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/orderDetails")
public class OrderDetailController {
    @Autowired
    IOrderDetailService orderDetailService;
    @Autowired
    IUserService userService;
    @Autowired
    IProductService productService;
    @Autowired
    ICartItemService cartItemService;
    @Autowired
    IOrdersService ordersService;

    @GetMapping
//    @PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<?> showListOrder(Pageable pageable) {
        Page<OrderDetail> list = orderDetailService.getAll(pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
//    @PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<List<OrderDetail>> getAllByUser(@PathVariable("userId") Long userId) {
        List<OrderDetail> list = orderDetailService.getAllByUserId(userId);
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    @PostMapping("create")
    public ResponseEntity<ResponseMessage> createOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO) {
        User user = userService.findById(orderDetailDTO.getUserId());
        Product product = productService.findById(orderDetailDTO.getProductId());
        Orders orders = ordersService.getOrdersByDateBuy(orderDetailDTO.getUserId());
        CartItem cartItem = cartItemService.findByUserAndProduct(user, product).get();
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrders(orders);
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setProductName(cartItem.getProductName());
            orderDetail.setPrice(cartItem.getPrice());
            orderDetail.setQuantity(cartItem.getQuantity());
            productService.setQuantityProduct(cartItem.getQuantity(), product.getId());
            orderDetailService.save(orderDetail);
            productService.updateStar(orderDetailDTO.getProductId());


        ordersService.setTotal(orderDetailService.sum(), orders.getId());
        cartItemService.deleteAllByUserId(orderDetailDTO.getUserId());
        return new ResponseEntity<>(new ResponseMessage("create is success"), HttpStatus.OK);
    }

}
