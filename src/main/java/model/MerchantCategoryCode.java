package main.java.model;

public class MerchantCategoryCode {
    private Long id;
    private String mcc;
    private String mccName;

    public MerchantCategoryCode(Long id, String mcc, String mccName) {
        this.id = id;
        this.mcc = mcc;
        this.mccName = mccName;
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
