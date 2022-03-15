package security.jwtwithdb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import security.jwtwithdb.entity.User;
import security.jwtwithdb.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findUserByUserFirstName(username);

        if (user != null){
            return new org.springframework.security.core.userdetails.User(
                    user.getUserFirstName(),
                    user.getUserPassword(),
                    getGrantedAuthorities(user)
            );
        } else{
            throw new UsernameNotFoundException("User not found with username " + username);
        }
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(User user){
        List<String> userAuthorities = Arrays.asList(user.getUserAuthorities().split("\\s*,\\s*"));

        return userAuthorities.stream()
                .map(s -> new SimpleGrantedAuthority(s))
                .collect(Collectors.toSet());
    }

    public List<User> getAllStudents(){

        return userRepo.findAll();
    }
}
