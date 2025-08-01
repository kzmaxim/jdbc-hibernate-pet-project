package model;

import javax.persistence.*;

@Entity
@Table(name="payment_system")
public class PaymentSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="payment_system_name")
    private String paymentSystemName;

    public PaymentSystem(String paymentSystemName) {
        this.paymentSystemName = paymentSystemName;
    }

    public PaymentSystem() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentSystemName() {
        return paymentSystemName;
    }

    public void setPaymentSystemName(String paymentSystemName) {
        this.paymentSystemName = paymentSystemName;
    }

    @Override
    public String toString() {
        return "PaymentSystem{" +
                "id=" + id +
                ", paymentSystemName='" + paymentSystemName + '\'' +
                '}';
    }
}
