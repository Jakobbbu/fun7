package project.game.fun7.web.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.game.fun7.model.GameUser;
import project.game.fun7.model.authorities.Privilege;
import project.game.fun7.model.authorities.Role;
import project.game.fun7.persistance.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Fun7User loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<GameUser> gameUserOpt = Optional.ofNullable(userRepository.findGameUserByUserName(username));
        if (gameUserOpt.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        GameUser gameUser = gameUserOpt.get();

        return new Fun7User(gameUser.getUserName(), gameUser.getPasswordHash(), getAuthorities(gameUser.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        List<GrantedAuthority> authoritiesList = new ArrayList<>();
        for(Role role : roles) {
            authoritiesList.add(new SimpleGrantedAuthority(role.getName()));
            for(Privilege privilege : role.getPrivileges()) {
                authoritiesList.add(new SimpleGrantedAuthority(privilege.getName()));
            }
        }
        return authoritiesList;
    }
}
