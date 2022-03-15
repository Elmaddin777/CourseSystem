package security.jwtwithdb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import security.jwtwithdb.entity.JwtRequest;
import security.jwtwithdb.entity.JwtResponse;
import security.jwtwithdb.entity.User;
import security.jwtwithdb.util.JwtUtil;
import security.jwtwithdb.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class JwtService{
    private final UserRepository userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        String username = jwtRequest.getUserName();
        String password = jwtRequest.getUserPassword();
        User user = userRepo.findUserByUserFirstName(username);
        Authentication auhtenticatedUser = authenticate(username, password);
        String generatedToken = jwtUtil.generateToken(auhtenticatedUser);
        return new JwtResponse(user, generatedToken);
    }

    private Authentication authenticate(String username, String password) throws Exception {
        try{
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (DisabledException e){
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
