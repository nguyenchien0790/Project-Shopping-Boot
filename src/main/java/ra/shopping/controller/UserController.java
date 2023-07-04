package ra.shopping.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import ra.shopping.dto.request.UserUpdateDTO;
import ra.shopping.dto.response.ResponseMessage;
import ra.shopping.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ra.shopping.service.impl.UserServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<?> findAll(Pageable pageable) {
        Page<User> list = userService.getAll(pageable);
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    @PutMapping("block/{id}")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> blockUser(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            userService.changeStatusUser(id);
            return new ResponseEntity<>(new ResponseMessage("block is success!"), HttpStatus.OK);
        }
    }

    @PutMapping("password")
    public ResponseEntity<ResponseMessage> changePassword(@RequestParam("password") String password,
                                                          @RequestParam("userId") Long userId) {
        User user = userService.findById(userId);
        String newPass = passwordEncoder.encode(password);
        if (user.getPassword().equals(newPass)) {
            return new ResponseEntity<>(new ResponseMessage("old password is match"), HttpStatus.BAD_REQUEST);
        }
        userService.changePassword(newPass, userId);
        return new ResponseEntity<>(new ResponseMessage("change password is success"), HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<User> updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        User user = userService.findById(userUpdateDTO.getId());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setName(userUpdateDTO.getName());
        user.setAvatar(userUpdateDTO.getAvatar());
        user.setDateCreate(userUpdateDTO.getDateCreate());
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
