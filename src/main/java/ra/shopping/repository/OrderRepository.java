package ra.shopping.repository;

import ra.shopping.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    @Query("select count(o.id) from Orders as o where o.status = true and o.total <> 0")
    int getCountTrue();
    @Query("select count(o.id) from Orders as o where o.status = false and o.total <> 0")
    int getCountFalse();
//    int countById();
    @Modifying
    @Query("update Orders o set o.status = true where o.id = ?1")
    void order(Long id);
    @Query("select sum(o.total) from Orders o where o.status = true ")
    float getTotal();
    @Query("select sum(o.total) from Orders o where o.status = true and month(o.dateBuy) = month(?1)")
    float getTotalByMonth(Date date);
    @Query("select sum(o.total) from Orders o where o.status = true and month(o.dateBuy) = month(now())")
    float getTotalThisMonth();
    @Modifying
    @Query("update Orders o set o.total = ?1 where o.id = ?2")
    void setTotal(float total, Long id);
@Query(value = "select * from Orders where user_Id = ?1 order by id desc limit 1", nativeQuery = true)
    Orders getOrdersByDateBuy(Long id);


}
