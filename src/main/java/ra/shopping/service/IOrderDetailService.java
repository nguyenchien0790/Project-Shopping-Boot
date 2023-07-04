package ra.shopping.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.shopping.model.OrderDetail;

import java.util.List;

public interface IOrderDetailService extends IGenericService<OrderDetail,Long> {
    Page<OrderDetail> getAll(Pageable pageable);
    List<OrderDetail> getAllByUserId(Long id);
    List<OrderDetail> getAllByOrderId(Long id);
    float sum();
}
