package com.example.bank.services;

import com.example.bank.domain.user.User;
import com.example.bank.domain.user.UserType;
import com.example.bank.dtos.UserDTO;
import com.example.bank.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void validateTransaction (User sender, BigDecimal amount) throws Exception {
        if (sender.getUserType() == UserType.MERCHANT) {
            throw new Exception("Usuário do tipo LOJISTA não está autorizado a realizar transações");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Saldo insuficiente");
        }
    }

    public User getUserById (Long id) throws Exception{
        return this.repository.findUserById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));
    }

    public void saveUser (User user) {
        this.repository.save(user);
    }

    public User createUser (UserDTO user) {
        User newUser = new User(user);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        this.saveUser(newUser);

        log.info("User has been created! " + newUser.getDocument());
        return newUser;
    }

    @Cacheable("userList")
    public List<User> getAllUsers() {
        return this.repository.findAll();
    }
}
