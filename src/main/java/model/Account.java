package model;

import javax.persistence.*;

@Entity
@Table(name="account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="account_number")
    private String accountNumber;
    @Column(name="balance")
    private Double balance;
    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currencyId;
    @ManyToOne
    @JoinColumn(name = "issuing_bank_id")
    private IssuingBank issuingBankId;

    public Account(String accountNumber, Double balance, Currency currencyId, IssuingBank issuingBankId) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.currencyId = currencyId;
        this.issuingBankId = issuingBankId;
    }

    public Account() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Currency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Currency currencyId) {
        this.currencyId = currencyId;
    }

    public IssuingBank getIssuingBankId() {
        return issuingBankId;
    }

    public void setIssuingBankId(IssuingBank issuingBankId) {
        this.issuingBankId = issuingBankId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", currencyId=" + currencyId +
                ", issuingBankId=" + issuingBankId +
                '}';
    }
}
