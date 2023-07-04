package ra.shopping.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/test")
public class ZTestController {

    // Tất cả các role được truy cập
    @GetMapping("/all")
    public String allAccess(){
        return "Access all";
    }

    // 3 Role USER/PM/ADMIN được truy cập vào USER
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('PM') or hasRole('ADMIN') ")
    public String userAccess(){
        return "Access role USER/PM/ADMIN";
    }

    // 2 Role PM/ADMIN được truy cập vào PM
    @GetMapping("/pm")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
    public String pmAccess(){
        return "Access role PM/ADMIN";
    }

    // Chỉ Role ADMIN được truy cập vào ADMIN
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess(){
        return "Access role ADMIN";
    }
}
