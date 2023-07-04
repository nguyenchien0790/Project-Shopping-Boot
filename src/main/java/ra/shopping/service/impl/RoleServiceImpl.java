package ra.shopping.service.impl;

import ra.shopping.model.Roles;
import ra.shopping.model.RoleName;
import ra.shopping.repository.IRoleRepository;
import ra.shopping.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    IRoleRepository roleRepository;
    @Override
    public Optional<Roles> findByName(RoleName name) {
        return roleRepository.findByRoleName(name);
    }

    @Override
    public Iterable<Roles> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public boolean save(Roles roles) {
        roleRepository.save(roles);
        return true;
    }

    @Override
    public boolean delete(Long id) {
        roleRepository.deleteById(id);
        return true;
    }

    @Override
    public Roles findById(Long aLong) {
        return roleRepository.findById(aLong).get();
    }

    @Override
    public Roles findByRoleName(RoleName roleName) {
        return roleRepository.findByRoleName(roleName).get();
    }
}
