package com.ntt.spring.security.login.security.services.itp;



import com.ntt.spring.security.login.models.entity.Wallet;
import org.springframework.stereotype.Service;

@Service
public interface WalletService {
    public Wallet getByAccountNum(String id);
    public Wallet createWallet();
//    public String chargeWallet(TransferWalletDTO transferWalletDTO);
    public boolean deleteWallet(String id);
    public String updateWallet(Wallet walletDTO);
    public double getmoney(Long id);
}
