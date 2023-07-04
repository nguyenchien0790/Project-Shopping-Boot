package ra.shopping.controller;

import ra.shopping.dto.request.SignInForm;
import ra.shopping.dto.request.SignUpForm;
import ra.shopping.dto.response.JwtResponse;
import ra.shopping.dto.response.ResponseMessage;
import ra.shopping.model.Roles;
import ra.shopping.model.RoleName;
import ra.shopping.model.User;
import ra.shopping.security.jwt.JwtProvider;
import ra.shopping.security.userprinciple.UserPrinciple;
import ra.shopping.service.impl.RoleServiceImpl;
import ra.shopping.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/auth")
@RestController
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signup")

    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpForm) {
        if (userService.existsByUsername(signUpForm.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("Error: Username is Exited !"), HttpStatus.OK);
        }
        if (userService.existsByEmail(signUpForm.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Error: Email is Exited !"), HttpStatus.OK);
        }
        User user = new User();
        user.setName(signUpForm.getName());
        user.setUsername(signUpForm.getUsername());
        user.setEmail(signUpForm.getEmail());
        user.setPassword(passwordEncoder.encode(signUpForm.getPassword()));
        user.setUserStatus(true);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dateNow = new Date();
        String strNow = sdf.format(dateNow);
        try {
            user.setDateCreate(sdf.parse(strNow));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        Set<String> strRoles = signUpForm.getListRoles();
        Set<Roles> listRoles = new HashSet<>();

        if (strRoles==null){
            //User quyen mac dinh
            Roles userRole = roleService.findByName(RoleName.ROLE_USER).orElseThrow(()->new RuntimeException("Error: Role is not found"));
            listRoles.add(userRole);
        }else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Roles adminRole = roleService.findByName(RoleName.ROLE_ADMIN).orElseThrow(
                                () -> new RuntimeException("Role not found")
                        );
                        listRoles.add(adminRole);
                        break;
                    case "pm":
                        Roles pmRole = roleService.findByName(RoleName.ROLE_PM).orElseThrow(() -> new RuntimeException("Role not found"));
                        listRoles.add(pmRole);
                        break;
                    default:
                        Roles userRole = roleService.findByName(RoleName.ROLE_USER).orElseThrow(() -> new RuntimeException("Role not found"));
                        listRoles.add(userRole);
                }
            });
        }

        user.setListRoles(listRoles);
        userService.save(user);
        return new ResponseEntity<>(new ResponseMessage("Register success !"), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@Valid @RequestBody SignInForm signInForm) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //Sinh JWT tra ve client
        String jwt = jwtProvider.createToken(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        //Lay cac quyen cua user
        List<String> listRoles = userPrinciple.getAuthorities().stream()
                .map(item -> item.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, userPrinciple.getUsername(), userPrinciple.getEmail(), listRoles));
    }
}
