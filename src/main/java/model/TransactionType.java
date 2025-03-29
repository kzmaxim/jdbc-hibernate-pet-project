package main.java.model;

public class TransactionType {
    private Long id;
    private String transactionTypeName;
    private String operator;

    public TransactionType(Long id, String transactionTypeName, String operator) {
        this.id = id;
        this.transactionTypeName = transactionTypeName;
        this.operator = operator;
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
