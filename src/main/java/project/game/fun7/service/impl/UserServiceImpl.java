package project.game.fun7.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import project.game.fun7.err.checked.NullValueFoundException;
import project.game.fun7.model.GameUser;
import project.game.fun7.persistance.UserRepository;
import project.game.fun7.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<GameUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public GameUser getUserById(Long id) throws NullValueFoundException {

        Optional<GameUser> user = Optional.ofNullable(userRepository.findGameUserById(id));
        if(user.isPresent()) {
            return user.get();
        } else {
            log.error("User with id: " + id + " does not exists");
            throw new NullValueFoundException("Bad request");
        }

    }

    @Override
    public GameUser getUserByUserName(String username) throws NullValueFoundException {

        Optional<GameUser> user = Optional.ofNullable(userRepository.findGameUserByUserName(username));
        if(user.isPresent()) {
            return user.get();
        } else {
            log.error("User with username: " + username + " does not exists");
            throw new NullValueFoundException("Bad request");
        }

    }

    @Override
    public void deleteUserById(Long id) {
            userRepository.deleteById(id);
    }

    @Override
    public void incrementUserUsageCount(Long id) throws NullValueFoundException {
        GameUser gameUser = this.getUserById(id);
        gameUser.setUsageCount(gameUser.getUsageCount() + 1);
        userRepository.save(gameUser);
    }
}
