package wroom.authservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wroom.authservice.config.EndpointConfig;
import wroom.authservice.converter.UserConverter;
import wroom.authservice.dto.UserDTO;
import wroom.authservice.service.UserService;

@RestController
@RequestMapping(value = EndpointConfig.USER_BASE_URL)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @PutMapping(value="/activate/{id}")
    public ResponseEntity<UserDTO> activate(@PathVariable Long id) {
        try {
            UserDTO user = this.userService.activate(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * GET /api/user
     * @return all enabled (who activated their email) users
     */
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        return new ResponseEntity<>(UserConverter.fromEntityList(this.userService.findAllEnabled(), UserConverter::fromEntity),
                HttpStatus.OK);
    }

    /**
     * GET /api/user
     * @return all enabled (who activated their email) users
     */
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @PutMapping("/lock/{id}")
    public ResponseEntity<UserDTO> lockUser(@PathVariable("id") Long id) {
        return new ResponseEntity<>(UserConverter.fromEntity(this.userService.lockAccount(id)),
                HttpStatus.OK);
    }

    /**
     * PUT /api/user/lock
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @PutMapping("/unlock/{id}")
    public ResponseEntity<UserDTO> unlockUser(@PathVariable("id") Long id) {
        return new ResponseEntity<>(UserConverter.fromEntity(this.userService.unlockAccount(id)),
                HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @DeleteMapping(value = "/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
    }

}