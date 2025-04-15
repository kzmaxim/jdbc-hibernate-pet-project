package model;

import javax.persistence.*;

@Entity
@Table(name="card_status")
public class CardStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="card_status_name")
    private String cardStatusName;

    public CardStatus(String cardStatusName) {
        //this.id = id;
        this.cardStatusName = cardStatusName;
    }

    public CardStatus() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardStatusName() {
        return cardStatusName;
    }

    public void setCardStatusName(String cardStatusName) {
        this.cardStatusName = cardStatusName;
    }

    @Override
    public String toString() {
        return "CardStatus{" +
                "id=" + id +
                ", cardStatusName='" + cardStatusName + '\'' +
                '}';
    }
}
