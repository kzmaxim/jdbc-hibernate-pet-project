package model;


import javax.persistence.*;

@Entity
@Table(name="sales_point")
public class SalesPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="pos_name")
    private String posName;
    @Column(name="pos_address")
    private String posAddress;
    @Column(name="pos_inn")
    private String posInn;

    @ManyToOne
    @JoinColumn(name = "acquiring_bank_id")
    private AcquiringBank acquiringBankId;

    public SalesPoint(Long id, String posName, String posAddress, String posInn, AcquiringBank acquiringBankId) {
        this.id = id;
        this.posName = posName;
        this.posAddress = posAddress;
        this.posInn = posInn;
        this.acquiringBankId = acquiringBankId;
    }

    public SalesPoint() {

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

    public AcquiringBank getAcquiringBankId() {
        return acquiringBankId;
    }

    public void setAcquiringBankId(AcquiringBank acquiringBankId) {
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
