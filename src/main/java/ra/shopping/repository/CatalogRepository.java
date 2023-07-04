package ra.shopping.repository;

import ra.shopping.model.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {
    @Query("select c from Catalog as c where c.status = true ")
    List<Catalog> getAll();
    @Modifying
    @Query("update Catalog c set c.status = 0 where c.id = ?1")
    void changeStatusCatalog(Long id);
}
