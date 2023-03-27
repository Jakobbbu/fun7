package project.game.fun7.controller.rest_controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import project.game.fun7.err.BadRequestException;
import project.game.fun7.facade.ServicesFacadeImpl;
import project.game.fun7.model.dto.services.ServicesInfoDTO;

import java.util.TimeZone;

@RestController
@RequestMapping("/services")
public class ServicesController {

    //TODO: (swagger, admin, docs, jwt, exceptions, testi)

    private final Logger log = LoggerFactory.getLogger(ServicesController.class);
    private final ServicesFacadeImpl servicesFacade;

    public ServicesController(ServicesFacadeImpl servicesFacade) {
        this.servicesFacade = servicesFacade;
    }

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
