package com.homates.wallet.controller;

import com.homates.wallet.dto.WalletBalanceDto;
import com.homates.wallet.dto.WalletDto;
import com.homates.wallet.model.Wallet;
import com.homates.wallet.repo.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {

    @Autowired
    WalletRepository walletRepository;

    @PostMapping("/create")
    public ResponseEntity<String> addItem(@RequestBody WalletDto walletDto) {
        System.out.println("Creating a new wallet...");

        Wallet wallet = new Wallet();
        wallet.setBalance(0);
        wallet.setUsername(walletDto.getUsername());
        wallet.setIdHouse(walletDto.getIdHouse());

        walletRepository.save(wallet);
        return new ResponseEntity<>("Wallet added.", HttpStatus.OK);
    }

    @GetMapping("/my-wallet")
    public ResponseEntity<WalletBalanceDto> getItem(@RequestBody WalletDto walletDto) {
        System.out.println("Getting wallet...");
        WalletBalanceDto myWallet = new WalletBalanceDto();

        Optional<Wallet> wallet = walletRepository.findByUsernameAndIdHouse(
                walletDto.getUsername(), walletDto.getIdHouse());
        if (wallet.isEmpty())
            return new ResponseEntity<>(myWallet, HttpStatus.NOT_FOUND);

        Wallet _currentWallet = wallet.get();
        myWallet.setBalance(_currentWallet.getBalance());
        myWallet.setUsername(_currentWallet.getUsername());
        myWallet.setIdHouse(_currentWallet.getIdHouse());

        return new ResponseEntity<>(myWallet, HttpStatus.OK);
    }


    @GetMapping("/balances/{id}")
    public ResponseEntity<List<WalletBalanceDto>> getItems(@PathVariable("id") int idHouse) {
        System.out.println("Getting house balances...");
        ArrayList<WalletBalanceDto> walletBalanceDtos = new ArrayList<>();

        List<Wallet> wallets = walletRepository.findByIdHouse(idHouse);
        if (wallets.isEmpty())
            return new ResponseEntity<>(walletBalanceDtos, HttpStatus.NOT_FOUND);

        for (Wallet w: wallets){
            WalletBalanceDto _currentWallet = new WalletBalanceDto();
            _currentWallet.setBalance(w.getBalance());
            _currentWallet.setUsername(w.getUsername());
            _currentWallet.setIdHouse(w.getIdHouse());
            walletBalanceDtos.add(_currentWallet);
        }

        return new ResponseEntity<>(walletBalanceDtos, HttpStatus.OK);
    }

    @PutMapping("/update-balance/{amount}")
    public ResponseEntity<String> updateItem(@PathVariable("amount") float amount, @RequestBody WalletDto walletDto) {
        System.out.println("Updating wallet...");

        Optional<Wallet> wallet = walletRepository.findByUsernameAndIdHouse(
                walletDto.getUsername(), walletDto.getIdHouse());
        if (wallet.isEmpty())
            return new ResponseEntity<>("Wallet not found.", HttpStatus.NOT_FOUND);

        Wallet _currentWallet = wallet.get();
        _currentWallet.setBalance(_currentWallet.getBalance() + amount);

        walletRepository.save(_currentWallet);
        return new ResponseEntity<>("Wallet balance updated.", HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> deleteItem(@RequestBody WalletDto walletDto) {
        System.out.println("Deleting wallet...");

        Optional<Wallet> wallet = walletRepository.findByUsernameAndIdHouse(
                walletDto.getUsername(), walletDto.getIdHouse());
        if (wallet.isEmpty())
            return new ResponseEntity<>("Wallet not found.", HttpStatus.NOT_FOUND);

        Wallet _currentWallet = wallet.get();
        walletRepository.delete(_currentWallet);
        return new ResponseEntity<>("Wallet has been deleted.", HttpStatus.OK);
    }

}
