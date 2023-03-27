package project.game.fun7.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import project.game.fun7.model.GameUser;

import java.util.List;

public interface UserRepository extends JpaRepository<GameUser, Long> {

    GameUser findGameUserById(Long id);

    GameUser findGameUserByUserName(String username);

    List<GameUser> findAll();

    void deleteById(Long id);

}
