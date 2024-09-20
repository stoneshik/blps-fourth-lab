package lab.blps.services;

import lab.blps.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void addAllUserAmountRequest(int addNumberRequest) {
        userRepository.addAllUserAmountRequest(addNumberRequest);
    }

    public void subAllUserAmountRequest(int subNumberRequest) {
        userRepository.subAllUserAmountRequest(subNumberRequest);
    }

    public void addAmountRequest(String userId, int addNumberRequest) {
        userRepository.addAmountRequest(userId, addNumberRequest);
    }

    public void subAmountRequest(String userId, int subNumberRequest) {
        userRepository.subAmountRequest(userId, subNumberRequest);
    }
}
