package ra.shopping.security.userprinciple;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ra.shopping.model.User;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPrinciple implements UserDetails {
    private Long id;
    private String name;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private String avatar;
    private Date dateCreate;
    private boolean userStatus;
    private Collection<? extends GrantedAuthority> authorities;

    // chuyen thong tin tu USER sang thong tin USERPRINCIPLE
    public static UserPrinciple build(User user){
        //lay cac quyen dung stream.map de duyet listroles
        List<GrantedAuthority> listAuthoritie = user.getListRoles().stream().
                map(role-> new SimpleGrantedAuthority(role.getRoleName().name())).collect(Collectors.toList());
        // collect dung sau khi duyet xong thi gan lai cho list authoritie

        // cach viet tren tuong tu duyet for
//        List<GrantedAuthority> listAuthoritie = new ArrayList<>();
//        for (Roles roles : user.getListRoles()) {
//            SimpleGrantedAuthority simga = new SimpleGrantedAuthority(roles.getRoleName().name());
//            listAuthoritie.add(simga);
//        }
//        listAuthoritie = listAuthoritie
        return new UserPrinciple(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getAvatar(),
                user.getDateCreate(),
                user.isUserStatus(),
                listAuthoritie
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


