package main.java.model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Transaction {
    private Long id;
    private LocalDate transactionDate;
    private Double sum;
    private String transactionName;
    private Long accountId;
    private Long transactionTypeId;
    private Long cardId;
    private Long terminalId;
    private Long responseCodeId;
    private String authorizationCode;
    private Timestamp receivedFromIssuingBank;
    private Timestamp sentToIssuingBank;

    public Transaction(Long id, LocalDate transactionDate, Double sum, String transactionName, Long accountId, Long transactionTypeId, Long cardId, Long terminalId, Long responseCodeId, String authorizationCode, Timestamp receivedFromIssuingBank, Timestamp sentToIssuingBank) {
        this.id = id;
        this.transactionDate = transactionDate;
        this.sum = sum;
        this.transactionName = transactionName;
        this.accountId = accountId;
        this.transactionTypeId = transactionTypeId;
        this.cardId = cardId;
        this.terminalId = terminalId;
        this.responseCodeId = responseCodeId;
        this.authorizationCode = authorizationCode;
        this.receivedFromIssuingBank = receivedFromIssuingBank;
        this.sentToIssuingBank = sentToIssuingBank;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(Long transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Long terminalId) {
        this.terminalId = terminalId;
    }

    public Long getResponseCodeId() {
        return responseCodeId;
    }

    public void setResponseCodeId(Long responseCodeId) {
        this.responseCodeId = responseCodeId;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public Timestamp getReceivedFromIssuingBank() {
        return receivedFromIssuingBank;
    }

    public void setReceivedFromIssuingBank(Timestamp receivedFromIssuingBank) {
        this.receivedFromIssuingBank = receivedFromIssuingBank;
    }

    public Timestamp getSentToIssuingBank() {
        return sentToIssuingBank;
    }

    public void setSentToIssuingBank(Timestamp sentToIssuingBank) {
        this.sentToIssuingBank = sentToIssuingBank;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", transactionDate=" + transactionDate +
                ", sum=" + sum +
                ", transactionName='" + transactionName + '\'' +
                ", accountId=" + accountId +
                ", transactionTypeId=" + transactionTypeId +
                ", cardId=" + cardId +
                ", terminalId=" + terminalId +
                ", responseCodeId=" + responseCodeId +
                ", authorizationCode='" + authorizationCode + '\'' +
                ", receivedFromIssuingBank=" + receivedFromIssuingBank +
                ", sentToIssuingBank=" + sentToIssuingBank +
                '}';
    }
}
