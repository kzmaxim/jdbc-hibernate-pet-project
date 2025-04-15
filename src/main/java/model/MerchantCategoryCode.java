package model;


import javax.persistence.*;

@Entity
@Table(name="merchant_category_code")
public class MerchantCategoryCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mcc;
    @Column(name="mcc_name")
    private String mccName;

    public MerchantCategoryCode(String mcc, String mccName) {
        //this.id = id;
        this.mcc = mcc;
        this.mccName = mccName;
    }

    public MerchantCategoryCode() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMccName() {
        return mccName;
    }

    public void setMccName(String mccName) {
        this.mccName = mccName;
    }

    @Override
    public String toString() {
        return "MerchantCategoryCode{" +
                "id=" + id +
                ", mcc='" + mcc + '\'' +
                ", mccName='" + mccName + '\'' +
                '}';
    }
}
