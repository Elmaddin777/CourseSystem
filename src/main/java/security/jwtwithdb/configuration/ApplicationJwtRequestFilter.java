package security.jwtwithdb.configuration;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import security.jwtwithdb.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ApplicationJwtRequestFilter extends OncePerRequestFilter {
    private final ApplicationJwtConfig applicationJwtConfig;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(applicationJwtConfig.getAuthorizationHeader());
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorizationHeader.replace(applicationJwtConfig.getTokenPrefix(), "");
        if (jwtUtil.isTokenExpired(token)){
            filterChain.doFilter(request, response);
            return;
        }

        try{
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    jwtUtil.getUsernameFromToken(token),
                    null,
                    jwtUtil.getGrantedAuthoritiesFromToken(token)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException e){
            throw new IllegalStateException(
                    String.format("Token %s cannot be trusted, error is \n %s", token, e.getMessage()));
        }

        filterChain.doFilter(request, response);
    }
}
