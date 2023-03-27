package project.game.fun7.controller.admin_rest_controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import project.game.fun7.err.BadRequestException;
import project.game.fun7.err.checked.NullValueFoundException;
import project.game.fun7.model.dto.users.GameUserDetailsDTO;
import project.game.fun7.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/administration")
public class AdminController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    public AdminController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/user/get-all")
    @ResponseBody
    List<GameUserDetailsDTO> getAllUsers() {
        return userService.getAllUsers().stream().map(entity -> modelMapper.map(entity, GameUserDetailsDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/user")
    @ResponseBody
    GameUserDetailsDTO getUserDetails(@RequestParam Long id) {
        try {
            return modelMapper.map(userService.getUserById(id), GameUserDetailsDTO.class);
        } catch (NullValueFoundException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @DeleteMapping("/user/delete")
    @ResponseBody
    public String deleteUser(@RequestParam Long id) {
            userService.deleteUserById(id);
            return "{\"message\":\"user deleted\"}";
    }

}
