package ra.shopping.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ra.shopping.model.User;
import ra.shopping.repository.IUserRepository;
import ra.shopping.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserRepository userRepository;

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean save(User user) {
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }

    @Override
    public User findById(Long aLong) {

        return userRepository.findById(aLong).get();
    }

    @Override
    public Optional<User> findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void changeStatusUser(Long id) {
        userRepository.changeStatusUser(id);
    }

    @Override
    public void changePassword(String newPassword, Long userId) {
        userRepository.changePassword(newPassword,userId);
    }

    @Override
    public Page<User> getAll(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), 10);
        return userRepository.getAll(pageable);
    }

}
