package model;


import javax.persistence.*;

@Entity
@Table(name="transaction_type")
public class TransactionType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="transaction_type_name")
    private String transactionTypeName;
    @Column(name="operator")
    private String operator;

    public TransactionType(Long id, String transactionTypeName, String operator) {
        this.id = id;
        this.transactionTypeName = transactionTypeName;
        this.operator = operator;
    }

    public TransactionType() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionTypeName() {
        return transactionTypeName;
    }

    public void setTransactionTypeName(String transactionTypeName) {
        this.transactionTypeName = transactionTypeName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "TransactionType{" +
                "id=" + id +
                ", transactionTypeName='" + transactionTypeName + '\'' +
                ", operator='" + operator + '\'' +
                '}';
    }
}
