package com.Project.trading.model;

import java.time.LocalDateTime;

import com.Project.trading.domain.WalletTransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Transaction {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;  // ✅ Corrected

    private Long transfer_id;
    
    private double amount;
    
    private String purpose;
    
    private WalletTransactionType walletTransactionType;

    private LocalDateTime dateTime = LocalDateTime.now();

    // Getters and Setters
    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) { // ✅ Accept Wallet entity
        this.wallet = wallet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public WalletTransactionType getWalletTransactionType() {
        return walletTransactionType;
    }

    public void setWalletTransactionType(WalletTransactionType walletTransactionType) {
        this.walletTransactionType = walletTransactionType;
    }

    public Long getTransfer_id() {
        return transfer_id;
    }

    public void setTransfer_id(Long transfer_id) {
        this.transfer_id = transfer_id;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	
}
