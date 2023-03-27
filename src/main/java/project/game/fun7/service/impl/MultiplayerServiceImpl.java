package project.game.fun7.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project.game.fun7.err.checked.NullValueFoundException;
import project.game.fun7.service.MultiplayerService;
import project.game.fun7.service.UserService;

import java.util.Arrays;

@Service
public class MultiplayerServiceImpl implements MultiplayerService {

    private final Logger log = LoggerFactory.getLogger(MultiplayerServiceImpl.class);
    private final UserService userService;

    @Value("${multiplayer.availability.ISOCodes}")
    private String ISOCodes;

    public MultiplayerServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isEnabled(Long userId, String countryISOCode) throws NullValueFoundException {
        log.info("checking if multiplayer is enabled for user " + userId);
        String[] isoCodesArray = ISOCodes.split(",");

        return userService.getUserById(userId).getUsageCount() > 5 && Arrays.asList(isoCodesArray).contains(countryISOCode);
    }
}
