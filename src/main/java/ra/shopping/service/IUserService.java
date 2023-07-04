package ra.shopping.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.shopping.model.User;

import java.util.Optional;

public interface IUserService extends IGenericService<User, Long>{
    Optional<User> findByUsername(String name); //Tim kiem User co ton tai trong DB khong?
    Boolean existsByUsername(String username); //username da co trong DB chua, khi tao du lieu
    Boolean existsByEmail(String email); //email da co trong DB chua

    void changeStatusUser(Long id);
    void changePassword(String newPassword,Long userId);
    Page<User> getAll(Pageable pageable);
}
