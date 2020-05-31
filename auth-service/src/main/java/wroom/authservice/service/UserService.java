package wroom.authservice.service;

import org.springframework.stereotype.Service;
import wroom.authservice.converter.UserConverter;
import wroom.authservice.domain.User;
import wroom.authservice.dto.UserDTO;
import wroom.authservice.exception.GeneralException;
import wroom.authservice.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAllEnabled() {
        return this.userRepository.findAllEnabled();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new GeneralException("Unable to find reference to " + id.toString() + " user"));
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email).get();
    }

    public UserDTO activate(Long id) {
        User usr = userRepository.getOne(id);
        usr.setNonLocked(true);
        userRepository.saveAndFlush(usr);

        return UserConverter.fromEntity(usr);
    }

    public void delete(Long id) {
        User user = findById(id);
        user.setEnabled(false);
        userRepository.save(user);
    }


    public User lockAccount(Long id) {
        User user = findById(id);
        user.setNonLocked(false);
        return userRepository.save(user);
    }

    public User unlockAccount(Long id) {
        User user = findById(id);
        user.setNonLocked(true);
        return userRepository.save(user);
    }
}