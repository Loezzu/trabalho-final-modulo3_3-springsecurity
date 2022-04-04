package com.tindev.tindevapi.controller.userAPI;

import com.tindev.tindevapi.dto.user.UserCreateDTO;
import com.tindev.tindevapi.dto.user.UserUpdateDTO;
import com.tindev.tindevapi.dto.user.UserDTO;
import com.tindev.tindevapi.dto.user.UserDTOCompleto;
import com.tindev.tindevapi.enums.Roles;
import com.tindev.tindevapi.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
@RequiredArgsConstructor
@Api(value = "3 - User API", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"3 - User API"})
public class UserController implements UserAPI{

    private final UserService userService;

    @GetMapping ("/list")
    public ResponseEntity<List<UserDTO>> listUser(@RequestParam(required = false) Integer id) throws Exception {
        return ResponseEntity.ok(userService.listUsers(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> postUser(@Valid @RequestBody UserCreateDTO userCreateDTO, @RequestParam Roles role) throws Exception{
        return ResponseEntity.ok(userService.createUser(userCreateDTO, role));
    }

    @GetMapping("/loged-user/getMyUser")
    public ResponseEntity<UserDTOCompleto> getLogedUser() throws Exception {
        return ResponseEntity.ok(userService.getUserLoged());
    }

    @PutMapping("/loged-user/update")
    public ResponseEntity<UserDTO> updatedLogedUser(@Valid @RequestBody UserUpdateDTO userUpdateDTO) throws Exception {
        return ResponseEntity.ok(userService.updateLogedUser(userUpdateDTO));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updatedUser(@PathVariable("userId") Integer id,
                                               @Valid @RequestBody UserUpdateDTO userUpdateDTO) throws Exception {
        return ResponseEntity.ok(userService.updateUser(id, userUpdateDTO));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Integer id) throws Exception {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted!");
    }

    @DeleteMapping("/loged-user/delete")
    public ResponseEntity<String> deleteLogedUser() throws Exception {
        userService.deleteUserLoged();
        return ResponseEntity.ok("User deleted");
    }

    @GetMapping("/list-likes-by-id")
    public ResponseEntity<List<UserDTOCompleto>> listLikesById(@RequestParam("id") Integer id) throws Exception {
        return ResponseEntity.ok(userService.listLikesOfTheUserById(id));
    }

    @GetMapping("/list-received-likes-by-id")
    public ResponseEntity<List<UserDTOCompleto>> listReceivedLikesById(@RequestParam("id") Integer id) throws Exception {
        return ResponseEntity.ok(userService.listReceivedLikesOfTheUserById(id));
    }

    @GetMapping("/getComplete")
    public ResponseEntity<List<UserDTOCompleto>> listUserComplete(@RequestParam(value = "id", required = false) Integer id) throws Exception {
        return ResponseEntity.ok(userService.listUserDTOComplete(id));
    }

    @GetMapping("/get-matches-by-id")
    public ResponseEntity<List<UserDTOCompleto>> listMatchesById(@RequestParam("id") Integer id) throws Exception {
        return ResponseEntity.ok(userService.listMatchesOfTheUser(id));
    }

    @GetMapping("/loged-user/get-available-users")
    public ResponseEntity<List<UserDTO>> listAvailableUsersByLogedUser() throws Exception {
        return ResponseEntity.ok(userService.listAvailableLogedUser());
    }

}
