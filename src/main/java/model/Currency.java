package model;


import javax.persistence.*;

@Entity
@Table(name="currency")
public class Currency {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name="currency_digital_code")
    private String currencyDigitalCode;
    @Column(name="currency_letter_code")
    private String currencyLetterCode;
    @Column(name="currency_name")
    private String currencyName;

    public Currency(Long id, String currencyDigitalCode, String currencyLetterCode, String currencyName) {
        this.id = id;
        this.currencyDigitalCode = currencyDigitalCode;
        this.currencyLetterCode = currencyLetterCode;
        this.currencyName = currencyName;
    }

    public Currency() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyDigitalCode() {
        return currencyDigitalCode;
    }

    public void setCurrencyDigitalCode(String currencyDigitalCode) {
        this.currencyDigitalCode = currencyDigitalCode;
    }

    public String getCurrencyLetterCode() {
        return currencyLetterCode;
    }

    public void setCurrencyLetterCode(String currencyLetterCode) {
        this.currencyLetterCode = currencyLetterCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", currencyDigitalCode='" + currencyDigitalCode + '\'' +
                ", currencyLetterCode='" + currencyLetterCode + '\'' +
                ", currencyName='" + currencyName + '\'' +
                '}';
    }
}
