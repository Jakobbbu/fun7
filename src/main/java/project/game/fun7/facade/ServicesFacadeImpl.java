package project.game.fun7.facade;

import org.springframework.stereotype.Component;
import project.game.fun7.err.checked.NullValueFoundException;
import project.game.fun7.model.dto.services.ServicesInfoDTO;
import project.game.fun7.service.AdsService;
import project.game.fun7.service.CustomerSupportService;
import project.game.fun7.service.MultiplayerService;
import project.game.fun7.service.UserService;

import java.util.TimeZone;

@Component
public class ServicesFacadeImpl implements ServicesFacade{

    private final AdsService adsService;
    private final CustomerSupportService customerSupportService;
    private final MultiplayerService multiplayerService;

    private final UserService userService;

    public ServicesFacadeImpl(AdsService adsService, CustomerSupportService customerSupportService, MultiplayerService multiplayerService, UserService userService) {
        this.adsService = adsService;
        this.customerSupportService = customerSupportService;
        this.multiplayerService = multiplayerService;
        this.userService = userService;
    }

    @Override
    public ServicesInfoDTO getEnabledServicesForUser(TimeZone timeZone, Long userId, String countryCode) throws NullValueFoundException {

        userService.incrementUserUsageCount(userId);

        ServicesInfoDTO infoDTO = new ServicesInfoDTO();

        infoDTO.setMultiplayer(boolToString(multiplayerService.isEnabled(userId, countryCode)));
        infoDTO.setUserSupport(boolToString(customerSupportService.isEnabled(timeZone)));
        infoDTO.setAds(boolToString(adsService.isEnabled(countryCode)));

        return infoDTO;
    }

    private String boolToString(boolean bool) {
        return bool ? "enabled" : "disabled";
    }
}
