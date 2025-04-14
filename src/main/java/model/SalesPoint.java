package model;

public class SalesPoint {
    private Long id;
    private String posName;
    private String posAddress;
    private String posInn;
    private Long acquiringBankId;

    public SalesPoint(Long id, String posName, String posAddress, String posInn, Long acquiringBankId) {
        this.id = id;
        this.posName = posName;
        this.posAddress = posAddress;
        this.posInn = posInn;
        this.acquiringBankId = acquiringBankId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public String getPosAddress() {
        return posAddress;
    }

    public void setPosAddress(String posAddress) {
        this.posAddress = posAddress;
    }

    public String getPosInn() {
        return posInn;
    }

    public void setPosInn(String posInn) {
        this.posInn = posInn;
    }

    public Long getAcquiringBankId() {
        return acquiringBankId;
    }

    public void setAcquiringBankId(Long acquiringBankId) {
        this.acquiringBankId = acquiringBankId;
    }

    @Override
    public String toString() {
        return "SalesPoint{" +
                "id=" + id +
                ", posName='" + posName + '\'' +
                ", posAddress='" + posAddress + '\'' +
                ", posInn='" + posInn + '\'' +
                ", acquiringBankId=" + acquiringBankId +
                '}';
    }
}
