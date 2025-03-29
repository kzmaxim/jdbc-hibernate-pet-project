package main.java.model;

public class AcquiringBank {
    private Long id;
    private String bic;
    private String abbreviatedName;

    public AcquiringBank(Long id, String bic, String abbreviatedName) {
        this.id = id;
        this.bic = bic;
        this.abbreviatedName = abbreviatedName;
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
