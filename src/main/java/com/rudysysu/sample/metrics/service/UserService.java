package com.rudysysu.sample.metrics.service;

import com.rudysysu.sample.metrics.model.User;
import com.rudysysu.sample.metrics.repository.UserRepository;
import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
        return userRepository.findById(id);
    }

    public void save(User user) {
        user.setCdate(new Date());
        user.setUdate(null);
        userRepository.save(user);
    }
}
