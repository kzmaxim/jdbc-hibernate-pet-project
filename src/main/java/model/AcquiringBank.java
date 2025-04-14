package model;


import javax.persistence.*;

@Entity
@Table(name="acquiring_bank")
public class AcquiringBank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String bic;
    @Column(name="abbreviated_name")
    private String abbreviatedName;

    public AcquiringBank(Long id, String bic, String abbreviatedName) {
        this.id = id;
        this.bic = bic;
        this.abbreviatedName = abbreviatedName;
    }

    public AcquiringBank() {

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

    public String getAbbreviatedName() {
        return abbreviatedName;
    }

    public void setAbbreviatedName(String abbreviatedName) {
        this.abbreviatedName = abbreviatedName;
    }

    @Override
    public String toString() {
        return "AcquiringBank{" +
                "id=" + id +
                ", bic='" + bic + '\'' +
                ", abbreviatedName='" + abbreviatedName + '\'' +
                '}';
    }
}
