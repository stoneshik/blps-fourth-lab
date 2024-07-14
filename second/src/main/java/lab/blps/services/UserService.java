package lab.blps.services;

import lab.blps.bd.entites.user.*;
import lab.blps.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User loadUserById(Long userId) throws UsernameNotFoundException {
        return userRepository
            .findById(userId)
            .orElseThrow(
                () -> new UsernameNotFoundException("Id пользователя не найден: " + userId)
            );
    }

    public Integer loadAmountRequest(Long userId) {
        User user = loadUserById(userId);
        return user.getAmountRequest();
    }

    public boolean isAmountRequestEnough(Long userId) {
        User user = loadUserById(userId);
        return user.getAmountRequest() > 0;
    }

    public void addAmountRequest(Long userId, int addNumberRequest) {
        userRepository.addAmountRequest(userId, addNumberRequest);
    }

    public void subAmountRequest(Long userId, int subNumberRequest) {
        userRepository.subAmountRequest(userId, subNumberRequest);
    }
}
