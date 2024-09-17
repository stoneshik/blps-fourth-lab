package lab.blps.security.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lab.blps.security.bd.entities.user.User;
import lab.blps.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User loadUserById(String userId) throws UsernameNotFoundException {
        return userRepository
            .findByUserId(userId)
            .orElseThrow(
                () -> new UsernameNotFoundException("Id пользователя не найден: " + userId)
            );
    }

    public Integer loadAmountRequest(String userId) {
        User user = loadUserById(userId);
        return user.getAmountRequest();
    }

    public boolean isAmountRequestEnough(String userId) {
        User user = loadUserById(userId);
        return user.getAmountRequest() > 0;
    }

    public void addAmountRequest(String userId, int addNumberRequest) {
        userRepository.addAmountRequest(userId, addNumberRequest);
    }

    public void subAmountRequest(String userId, int subNumberRequest) {
        userRepository.subAmountRequest(userId, subNumberRequest);
    }
}
