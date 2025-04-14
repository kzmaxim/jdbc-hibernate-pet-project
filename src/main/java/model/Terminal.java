package model;


import javax.persistence.*;

@Entity
@Table(name="terminal")
public class Terminal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="terminal_id")
    private String terminalId;
    @ManyToOne
    @JoinColumn(name="mcc_id")
    private MerchantCategoryCode mccId;
    @ManyToOne
    @JoinColumn(name="pos_id")
    private SalesPoint posId;

    public Terminal(Long id, String terminalId, MerchantCategoryCode mccId, SalesPoint posId) {
        this.id = id;
        this.terminalId = terminalId;
        this.mccId = mccId;
        this.posId = posId;
    }

    public Terminal() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public MerchantCategoryCode getMccId() {
        return mccId;
    }

    public void setMccId(MerchantCategoryCode mccId) {
        this.mccId = mccId;
    }

    public SalesPoint getPosId() {
        return posId;
    }

    public void setPosId(SalesPoint posId) {
        this.posId = posId;
    }

    @Override
    public String toString() {
        return "Terminal{" +
                "id=" + id +
                ", terminalId='" + terminalId + '\'' +
                ", mccId=" + mccId +
                ", posId=" + posId +
                '}';
    }
}
