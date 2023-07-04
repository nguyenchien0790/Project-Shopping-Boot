package ra.shopping.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);
@Autowired
private JwtProvider jwtProvider;
@Autowired
private UserDetailsService userDetailsService;

    private String getJwtFormRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        // kiem tra header co chua thong tun cua jwt ko?
        //cach dung StringUtils.hasText(bearerToken) = bearerToken!=null
        if(StringUtils.hasText(bearerToken)  && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return null;
    }

    //
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // lay JWT tu request
            String token = getJwtFormRequest(request);
            if(token !=null &&jwtProvider.validateToken(token)){
                // lay userNamr tu chuoi JWT bang pt getUerNameFromToken
                String username = jwtProvider.getUerNameFromToken(token);
                // lay thong tin nguoi dung tu userId
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // neu nguoi dung hop le set thong tin cho securitycontext de cap quyen truy cap
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e){
            logger.error("Can't set user authentication -> Message: {}",e);
        }
        // tat ca cac request den di qua fiterChain kiem tra token hop le khong roi kiem tra quyen va set cho security
        filterChain.doFilter(request,response);
    }

}
