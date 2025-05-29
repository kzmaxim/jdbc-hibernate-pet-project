package model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;



@Entity
@Table(name="transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="transaction_date")
    private LocalDate transactionDate;
    @Column(name="sum")
    private Double sum;
    @Column(name="transaction_name")
    private String transactionName;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account accountId;
    @ManyToOne
    @JoinColumn(name = "transaction_type_id")
    private TransactionType transactionTypeId;
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card cardId;
    @ManyToOne
    @JoinColumn(name = "terminal_id")
    private Terminal terminalId;
    @ManyToOne
    @JoinColumn(name = "response_code_id")
    private ResponseCode responseCodeId;
    @Column(name="authorization_code")
    private String authorizationCode;
    @Column(name="received_from_issuing_bank")
    private Timestamp receivedFromIssuingBank;
    @Column(name="sent_to_issuing_bank")
    private Timestamp sentToIssuingBank;

    public Transaction(LocalDate transactionDate, Double sum, String transactionName, Account accountId, TransactionType transactionTypeId, Card cardId, Terminal terminalId, ResponseCode responseCodeId, String authorizationCode, Timestamp receivedFromIssuingBank, Timestamp sentToIssuingBank) {
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

    public Transaction() {

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

    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
        this.accountId = accountId;
    }

    public TransactionType getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(TransactionType transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public Card getCardId() {
        return cardId;
    }

    public void setCardId(Card cardId) {
        this.cardId = cardId;
    }

    public Terminal getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Terminal terminalId) {
        this.terminalId = terminalId;
    }

    public ResponseCode getResponseCodeId() {
        return responseCodeId;
    }

    public void setResponseCodeId(ResponseCode responseCodeId) {
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
