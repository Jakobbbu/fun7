package project.game.fun7.controller.rest_controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import project.game.fun7.controller.SecuredController;
import project.game.fun7.err.BadRequestException;
import project.game.fun7.facade.ServicesFacadeImpl;
import project.game.fun7.model.dto.services.ServicesInfoDTO;

import java.util.TimeZone;

/**
 * This is ServicesController, a controller that will return information about services
 */
@Tag(name = "services", description = "ServicesController")
@RestController
@RequestMapping("/services")
public class ServicesController implements SecuredController {

    private static final Logger log = LoggerFactory.getLogger(ServicesController.class);
    private final ServicesFacadeImpl servicesFacade;

    public ServicesController(ServicesFacadeImpl servicesFacade) {
        this.servicesFacade = servicesFacade;
    }

    /**
     * This is the endpoint that will return the status of the three services.
     * @param timeZone timezone of user e.g. Europe/Ljubljana
     * @param id id of user
     * @param cc users country code
     * @return status of services.
     */
    @GetMapping("/get-enabled")
    @ResponseBody
    public ServicesInfoDTO getServicesAvailabilityInfo(@RequestParam TimeZone timeZone, @RequestParam Long id, @RequestParam String cc) {
        try {
            return servicesFacade.getEnabledServicesForUser(timeZone, id, cc);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
