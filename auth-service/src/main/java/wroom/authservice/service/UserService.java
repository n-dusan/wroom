package wroom.authservice.service;

import org.springframework.stereotype.Service;
import wroom.authservice.converter.AMQPUserConverter;
import wroom.authservice.converter.UserConverter;
import wroom.authservice.domain.User;
import wroom.authservice.dto.UserDTO;
import wroom.authservice.exception.GeneralException;
import wroom.authservice.producer.UserProducer;
import wroom.authservice.producer.messages.UserOperationEnum;
import wroom.authservice.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserProducer userProducer;

    public UserService(UserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
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

        //replicate it to search
        this.userProducer.send(AMQPUserConverter.toFeatureMessage(UserConverter.fromEntity(usr), UserOperationEnum.ACTIVATE));
        return UserConverter.fromEntity(usr);
    }

    public void delete(Long id) {
        User user = findById(id);
        user.setEnabled(false);
        userRepository.save(user);

        this.userProducer.send(AMQPUserConverter.toFeatureMessage(UserConverter.fromEntity(user), UserOperationEnum.DELETE));
    }


    public User lockAccount(Long id) {
        User user = findById(id);
        user.setNonLocked(false);

        this.userProducer.send(AMQPUserConverter.toFeatureMessage(UserConverter.fromEntity(user), UserOperationEnum.LOCK));

        return userRepository.save(user);
    }

    public User unlockAccount(Long id) {
        User user = findById(id);
        user.setNonLocked(true);

        this.userProducer.send(AMQPUserConverter.toFeatureMessage(UserConverter.fromEntity(user), UserOperationEnum.UNLOCK));

        return userRepository.save(user);
    }
}