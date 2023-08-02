package com.ntt.spring.security.login.security.services;


import com.ntt.spring.security.login.models.entity.Cash;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.entity.Wallet;
import com.ntt.spring.security.login.repository.UserRepository;
import com.ntt.spring.security.login.repository.WalletRepository;
import com.ntt.spring.security.login.security.services.itp.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class WalletServiceImpl implements WalletService {
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Wallet getByAccountNum(String accountNum) {
        return walletRepository.findByAccountNum(accountNum);
    }
    @Override
    public double getmoney(Long id){
        User user = userRepository.findById(id).get();
        Wallet cash = user.getWallet();
        return cash.getBalance();
    }
    @Override
    public Wallet createWallet() {
        String account = generate();
        if (walletRepository.findByAccountNum(account) != null) {
            createWallet();
        }
            Wallet wallet1 = new Wallet();
            wallet1.setAccountNum(account);
            walletRepository.save(wallet1);
            return wallet1;
    }

    public String generate() {

        Random random = new Random();
        int firstFourDigits = random.nextInt(100000);
        int secondFourDigits = random.nextInt(100000);

        LocalDate today = LocalDate.now();
        String monthAndYear = today.format(DateTimeFormatter.ofPattern("MMyy"));

        String accountNumber = String.format("%05d %05d %s", firstFourDigits, secondFourDigits, monthAndYear);
        return accountNumber;
    }

    @Override
    public boolean deleteWallet(String accNum) {
        Wallet wallet = walletRepository.findByAccountNum(accNum);
        if (wallet != null) {
            walletRepository.delete(wallet);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String updateWallet(Wallet walletDTO) {
        Wallet wallet = walletRepository.findByAccountNum(walletDTO.getAccountNum());

        wallet.setBalance(wallet.getBalance()+walletDTO.getBalance());
        walletRepository.save(wallet);
        return "update thành công";
    }
}
