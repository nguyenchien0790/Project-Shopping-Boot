package ra.shopping.repository;

import ra.shopping.model.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
@Query("select o from OrderDetail as o where o.orders.status = true")
    Page<OrderDetail> getAll(Pageable pageable);
@Query("select o from OrderDetail as o where o.orders.user.id = ?1 and o.orders.status = true ")
    List<OrderDetail> getAllByUserId(Long id);
@Query("select o from OrderDetail as o where o.orders.id = ?1")
    List<OrderDetail> getAllByOrderId(Long id);
@Query("select coalesce(sum(o.quantity * o.price), 0) from OrderDetail as o")
    float sum();
}
