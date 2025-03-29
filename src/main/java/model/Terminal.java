package main.java.model;

public class Terminal {
    private Long id;
    private String terminalId;
    private Long mccId;
    private Long posId;

    public Terminal(Long id, String terminalId, Long mccId, Long posId) {
        this.id = id;
        this.terminalId = terminalId;
        this.mccId = mccId;
        this.posId = posId;
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

    public Long getMccId() {
        return mccId;
    }

    public void setMccId(Long mccId) {
        this.mccId = mccId;
    }

    public Long getPosId() {
        return posId;
    }

    public void setPosId(Long posId) {
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
