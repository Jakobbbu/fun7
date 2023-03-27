package project.game.fun7.service;

import project.game.fun7.err.checked.NullValueFoundException;
import project.game.fun7.model.GameUser;

import java.util.List;

public interface UserService {

    List<GameUser> getAllUsers();

    GameUser getUserById(Long id) throws NullValueFoundException;

    void deleteUserById(Long id);

    void incrementUserUsageCount(Long id) throws NullValueFoundException;
}
