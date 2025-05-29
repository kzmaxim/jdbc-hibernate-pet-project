package model;


import javax.persistence.*;

@Entity
@Table(name="response_code")
public class ResponseCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="error_code")
    private String errorCode;
    @Column(name="error_description")
    private String errorDescription;
    @Column(name="error_level")
    private String errorLevel;

    public ResponseCode(String errorCode, String errorDescription, String errorLevel) {
        //this.id = id;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.errorLevel = errorLevel;
    }

    public ResponseCode() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorLevel() {
        return errorLevel;
    }

    public void setErrorLevel(String errorLevel) {
        this.errorLevel = errorLevel;
    }

    @Override
    public String toString() {
        return "ResponseCode{" +
                "id=" + id +
                ", errorCode='" + errorCode + '\'' +
                ", errorDescription='" + errorDescription + '\'' +
                ", errorLevel='" + errorLevel + '\'' +
                '}';
    }
}
