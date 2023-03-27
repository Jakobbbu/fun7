package project.game.fun7.controller.admin_rest_controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import project.game.fun7.controller.SecuredController;
import project.game.fun7.err.BadRequestException;
import project.game.fun7.err.checked.NullValueFoundException;
import project.game.fun7.model.dto.users.GameUserDetailsDTO;
import project.game.fun7.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This is AdminController, a controller only user with admin rights can access
 */
@Tag(name = "administration", description = "AdminController")
@RestController
@RequestMapping("/administration")
public class AdminController implements SecuredController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    private final UserService userService;

    private final ModelMapper modelMapper;

    public AdminController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    /**
     * This is the endpoint for getting all users.
     * @return All users.
     */
    @GetMapping("/user/get-all")
    @ResponseBody
    List<GameUserDetailsDTO> getAllUsers() {
        return userService.getAllUsers().stream().map(entity -> modelMapper.map(entity, GameUserDetailsDTO.class)).collect(Collectors.toList());
    }

    /**
     * This is the endpoint for getting details about user.
     * @param id of user
     * @return user details.
     */
    @GetMapping("/user")
    @ResponseBody
    GameUserDetailsDTO getUserDetails(@RequestParam Long id) {
        try {
            return modelMapper.map(userService.getUserById(id), GameUserDetailsDTO.class);
        } catch (NullValueFoundException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    /**
     * This is the endpoint for deleting user.
     * @param id of user
     */
    @DeleteMapping("/user/delete")
    @ResponseBody
    public String deleteUser(@RequestParam Long id) {
            userService.deleteUserById(id);
            return "{\"message\":\"user deleted\"}";
    }

}
