package wroom.authservice.controller;

import java.util.List;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
import wroom.authservice.util.RequestCounter;

@RestController
@RequestMapping(value = EndpointConfig.USER_BASE_URL)
@Log4j2
public class UserController {

    private static final String LOG_ACTIVATE = "action=activate user=%s times=%s details=%s";
    private static final String LOG_LOCK_USER = "action=lock user=%s times=%s details=%s";
    private static final String LOG_UNLOCK_USER = "action=unlock user=%s times=%s details=%s";

    private final UserService userService;
    private final RequestCounter requestCounter;

    public UserController(UserService userService, RequestCounter requestCounter) {
        this.userService = userService;
        this.requestCounter = requestCounter;
    }

   // @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @PutMapping(value="/activate/{id}")
    public ResponseEntity<UserDTO> activate(@PathVariable Long id, Authentication auth) {
        String logContent = String.format(LOG_ACTIVATE, auth.getName(), requestCounter.get(EndpointConfig.USER_BASE_URL), "Activating user id " + id);
        try {
            log.info(logContent);
            UserDTO user = this.userService.activate(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
        }
        log.error(logContent);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * GET /api/user
     * @return all enabled (who activated their email) users
     */
    //@PreAuthorize("hasAuthority('MANAGE_USERS')")
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
    public ResponseEntity<UserDTO> lockUser(@PathVariable("id") Long id, Authentication auth) {
        String logContent = String.format(LOG_LOCK_USER, auth.getName(), requestCounter.get(EndpointConfig.USER_BASE_URL), "Locking user id " + id);
        log.info(logContent);
        return new ResponseEntity<>(UserConverter.fromEntity(this.userService.lockAccount(id)),
                HttpStatus.OK);
    }

    /**
     * PUT /api/user/lock
     * @param id
     * @return
     */
    //@PreAuthorize("hasAuthority('MANAGE_USERS')")
    @PutMapping("/unlock/{id}")
    public ResponseEntity<UserDTO> unlockUser(@PathVariable("id") Long id, Authentication auth) {
        String logContent = String.format(LOG_UNLOCK_USER, auth.getName(), requestCounter.get(EndpointConfig.USER_BASE_URL), "Unlocking user id " + id);
        log.info(logContent);
        return new ResponseEntity<>(UserConverter.fromEntity(this.userService.unlockAccount(id)),
                HttpStatus.OK);
    }

    //@PreAuthorize("hasAuthority('MANAGE_USERS')")
    @DeleteMapping(value = "/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUser(@PathVariable("id") Long id) {
        return new ResponseEntity<>(UserConverter.fromEntity(this.userService.findById(id)), HttpStatus.OK);
    }

}