package project.game.fun7.service;

import project.game.fun7.err.checked.NullValueFoundException;

public interface MultiplayerService {

    boolean isEnabled(Long userId, String countryISOCode) throws NullValueFoundException;

}
