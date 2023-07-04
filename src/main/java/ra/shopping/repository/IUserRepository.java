package ra.shopping.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.shopping.model.User;

import java.util.Optional;


@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Query("select u from User as u")
    Page<User> getAll(Pageable pageable);
    @Modifying
    @Query(value = "update User u set u.status = !u.status where u.id = ?1", nativeQuery = true)
    void changeStatusUser(Long id);
    @Modifying
    @Query("update User u set u.password = ?1 where u.id = ?2")
    void changePassword(String newPassword,Long userId);


}
