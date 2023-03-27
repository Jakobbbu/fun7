package project.game.fun7.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * Include this interface in controller that should be protected from unauthorized access.
 */
@SecurityRequirement(name = "bearerAuth")
public interface SecuredController {
}
