package project.game.fun7.facade;

import project.game.fun7.model.dto.services.ServicesInfoDTO;

import java.util.TimeZone;

public interface ServicesFacade {

    ServicesInfoDTO getEnabledServicesForUser(TimeZone timeZone, Long userId, String countryCode) throws Exception;

}
