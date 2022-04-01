package com.tindev.tindevapi.controller.userAPI;

import com.tindev.tindevapi.dto.user.UserCreateDTO;
import com.tindev.tindevapi.dto.user.UserDTO;
import com.tindev.tindevapi.dto.user.UserDTOCompleto;
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
    public ResponseEntity<UserDTO> postUser(@Valid @RequestBody UserCreateDTO userCreateDTO) throws Exception{
        return ResponseEntity.ok(userService.createUser(userCreateDTO));
    }


    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updatedUser(@PathVariable("userId") Integer id,
                                                    @Valid @RequestBody UserCreateDTO userCreateDTO) throws Exception {
        return ResponseEntity.ok(userService.updateUser(id, userCreateDTO));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Integer id) throws Exception {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted!");
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

}
