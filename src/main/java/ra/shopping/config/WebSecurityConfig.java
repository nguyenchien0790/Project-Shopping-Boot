package ra.shopping.config;

import org.springframework.security.config.BeanIds;
//import ra.shopping.security.jwt.JwtEntryPoint;
import ra.shopping.security.jwt.JwtTokenFilter;
import ra.shopping.security.userprinciple.CustomerUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    CustomerUserDetailService customerUserDetailService;

    @Bean
    public JwtTokenFilter jwtTokenFilter(){
        return new JwtTokenFilter();
    }
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customerUserDetailService) // cung cap customerServicer cho spring security
                .passwordEncoder(passwordEncoder()); // cung cap password cho encoder
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        // ma hoa mk luu trong CSDL
        return new BCryptPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors()// ngan chan request tu mot domain khac
                .and().csrf().disable()
                .authorizeRequests()
//                .antMatchers("/**").permitAll() // cho phep tat ca moi nguoi vao dia chi nay
                .antMatchers("/api/v1/auth/**").permitAll()// cho phep tat ca moi nguoi vao dia chi nay
                .antMatchers("/api/v1/users/update/**").permitAll()

                .antMatchers("/api/v1/users/password").permitAll()
//                .antMatchers("/api/v1/users/block/**").hasAnyAuthority("ADMIN")

                .antMatchers("/api/v1/users/block/**").permitAll()

//                .antMatchers("/api/v1/users").hasAnyAuthority("ADMIN")
                .antMatchers("/api/v1/users").permitAll()

                .antMatchers("/api/v1/products").permitAll()
//                .antMatchers("/api/v1/products/create").hasAnyAuthority("ADMIN","PM")

                .antMatchers("/api/v1/products/create").permitAll()
//                .antMatchers("/api/v1/products/delete/**").hasAnyAuthority("ADMIN","PM")

                .antMatchers("/api/v1/products/delete/**").permitAll()
//                .antMatchers("/api/v1/products/update/**").hasAnyAuthority("ADMIN","PM")

                .antMatchers("/api/v1/products/update/**").permitAll()
                .antMatchers("/api/v1/products/byName/**").permitAll()
                .antMatchers("/api/v1/products/byCatalog/**").permitAll()

//                .antMatchers("/api/v1/orderDetails").hasAnyAuthority("ADMIN","PM")
                .antMatchers("/api/v1/orderDetails").permitAll()

                .antMatchers("/api/v1/orderDetails/**").permitAll()
                .antMatchers("/api/v1/orderDetails/create").permitAll()
                .antMatchers("/api/v1/orders").permitAll()

//                .antMatchers("/api/v1/orders/checkOrder/**").hasAnyAuthority("ADMIN","PM")
                .antMatchers("/api/v1/orders/checkOrder/**").permitAll()

//                .antMatchers("/api/v4/orders/total").hasAnyAuthority("ADMIN","PM")
                .antMatchers("/api/v4/orders/total").permitAll()

//                .antMatchers("/api/v4/orders/totalByMonth").hasAnyAuthority("ADMIN","PM")
                .antMatchers("/api/v4/orders/totalByMonth").permitAll()

//                .antMatchers("/api/v4/orders/totalThisMonth").hasAnyAuthority("ADMIN","PM")
                .antMatchers("/api/v4/orders/totalThisMonth").permitAll()

//                .antMatchers("/api/v4/orders/numberOfOrders").hasAnyAuthority("ADMIN","PM")
                .antMatchers("/api/v4/orders/numberOfOrders").permitAll()

//                .antMatchers("/api/v4/orders/numberOfOrderFalse").hasAnyAuthority("ADMIN","PM")
                .antMatchers("/api/v4/orders/numberOfOrderFalse").permitAll()

                .antMatchers("/api/v1/catalogs").permitAll()

//                .antMatchers("/api/v1/catalogs/create").hasAnyAuthority("ADMIN","PM")
                .antMatchers("/api/v1/catalogs/create").permitAll()

//                .antMatchers("/api/v1/catalogs/update/**").hasAnyAuthority("ADMIN","PM")
                .antMatchers("/api/v1/catalogs/update/**").permitAll()

//                .antMatchers("/api/v1/catalogs/delete/**").hasAnyAuthority("ADMIN","PM")
                .antMatchers("/api/v1/catalogs/delete/**").permitAll()

                .antMatchers("/api/v1/cartItems/**").permitAll()


                .anyRequest().authenticated();// tat ca cac request khac du can phai xac thuc moi duoc truy cap
        // them lop filter kiem tra JWT
        httpSecurity.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
