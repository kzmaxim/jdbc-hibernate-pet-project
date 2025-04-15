package model;


import javax.persistence.*;

@Entity
@Table(name="issuing_bank")
public class IssuingBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="bic")
    private String bic;
    @Column(name="bin")
    private String bin;
    @Column(name="abbreviated_name")
    private String abbreviatedName;

    public IssuingBank(String bic, String bin, String abbreviatedName) {
        this.bic = bic;
        this.bin = bin;
        this.abbreviatedName = abbreviatedName;
    }

    public IssuingBank() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getAbbreviatedName() {
        return abbreviatedName;
    }

    public void setAbbreviatedName(String abbreviatedName) {
        this.abbreviatedName = abbreviatedName;
    }

    @Override
    public String toString() {
        return "IssuingBank{" +
                "id=" + id +
                ", bic='" + bic + '\'' +
                ", bin='" + bin + '\'' +
                ", abbreviatedName='" + abbreviatedName + '\'' +
                '}';
    }
}
