package model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;


@Entity
@Table(name="card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long cardId;
    @Column(name="card_number")
    private String cardNumber;
    @Column(name="expiration_date")
    private LocalDate expirationDate;
    @Column(name="holder_name")
    private String holderName;
    @ManyToOne
    @JoinColumn(name = "card_status_id")
    private CardStatus cardStatusId;
    @ManyToOne
    @JoinColumn(name = "payment_system_id")
    private PaymentSystem paymentSystemId;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account accountId;
    @Column(name="received_from_issuing_bank")
    private Timestamp receivedFromIssuingBank;
    @Column(name="sent_to_issuing_bank")
    private Timestamp sentToIssuingBank;

    public Card(String cardNumber, LocalDate expirationDate, String holderName, CardStatus cardStatusId, PaymentSystem paymentSystemId, Account accountId, Timestamp receivedFromIssuingBank, Timestamp sentToIssuingBank) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.holderName = holderName;
        this.cardStatusId = cardStatusId;
        this.paymentSystemId = paymentSystemId;
        this.accountId = accountId;
        this.receivedFromIssuingBank = receivedFromIssuingBank;
        this.sentToIssuingBank = sentToIssuingBank;
    }

    public Card() {

    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public CardStatus getCardStatusId() {
        return cardStatusId;
    }

    public void setCardStatusId(CardStatus cardStatusId) {
        this.cardStatusId = cardStatusId;
    }

    public PaymentSystem getPaymentSystemId() {
        return paymentSystemId;
    }

    public void setPaymentSystemId(PaymentSystem paymentSystemId) {
        this.paymentSystemId = paymentSystemId;
    }

    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
        this.accountId = accountId;
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
        return "Card{" +
                "cardId=" + cardId +
                ", cardNumber='" + cardNumber + '\'' +
                ", expirationDate=" + expirationDate +
                ", holderName='" + holderName + '\'' +
                ", cardStatusId=" + cardStatusId +
                ", paymentSystemId=" + paymentSystemId +
                ", accountId=" + accountId +
                ", receivedFromIssuingBank=" + receivedFromIssuingBank +
                ", sentToIssuingBank=" + sentToIssuingBank +
                '}';
    }
}
