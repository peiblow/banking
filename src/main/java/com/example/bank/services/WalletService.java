package com.example.bank.services;

import com.example.bank.domain.Wallet;
import com.example.bank.domain.user.User;
import com.example.bank.dtos.WalletDTO;
import com.example.bank.repositories.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class WalletService {

    @Autowired
    private WalletRepository repository;

    @Autowired
    private UserService userService;

    public Wallet findUserWallet (Long userId) throws RuntimeException {
        return this.repository.findByWalletOwnerId(userId)
                .orElseThrow(() -> new RuntimeException("Não encontramos nenhuma carteira para esse usuario."));
    }

    public Wallet updateWallet(WalletDTO walletDTO, Long walletId) throws RuntimeException {
        return this.repository.findById(walletId)
                .map(wallet -> {
                    wallet.setBrl(walletDTO.brl());
                    wallet.setUsd(walletDTO.usd());
                    wallet.setBtc(walletDTO.btc());
                    wallet.setUpdatedAt(LocalDateTime.now());
                    return repository.save(wallet);
                })
                .orElseThrow(() -> new RuntimeException("Carteira não encontrada"));
    }

    public Wallet createWallet(WalletDTO walletDTO) throws RuntimeException {
        try {
            User owner = this.userService.getUserById(walletDTO.userId());

            Wallet newWallet = new Wallet();
            newWallet.setWalletOwner(owner);
            newWallet.setBrl(0.0);
            newWallet.setUsd(0.0);
            newWallet.setBtc(0.0);
            newWallet.setCreatedAt(LocalDateTime.now());
            newWallet.setUpdatedAt(LocalDateTime.now());

            this.repository.save(newWallet);

            log.info("Nova carteira de moedas criada com sucesso! " + newWallet.getId());
            return newWallet;
        } catch (RuntimeException e) {
            log.error("error: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
