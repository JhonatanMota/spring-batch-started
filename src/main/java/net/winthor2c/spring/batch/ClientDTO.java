package net.winthor2c.spring.batch;

public class ClientDTO {

    private Integer codCli;
    private String cliente;
    private String enderCod;
    private String bairroCob;
    private String telCob;
    private String estCob;
    private String cepCob;
    private String cgcEnt;
    
    public ClientDTO() {

    }

    public Integer getCodCli() {
        return this.codCli;
    }

    public void setCodCli(Integer codCli) {
        this.codCli = codCli;
    }

    public String getCliente() {
        return this.cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEnderCod() {
        return this.enderCod;
    }

    public void setEnderCod(String enderCod) {
        this.enderCod = enderCod;
    }

    public String getBairroCob() {
        return this.bairroCob;
    }

    public void setBairroCob(String bairroCob) {
        this.bairroCob = bairroCob;
    }

    public String getTelCob() {
        return this.telCob;
    }

    public void setTelCob(String telCob) {
        this.telCob = telCob;
    }

    public String getEstCob() {
        return this.estCob;
    }

    public void setEstCob(String estCob) {
        this.estCob = estCob;
    }

    public String getCepCob() {
        return this.cepCob;
    }

    public void setCepCob(String cepCob) {
        this.cepCob = cepCob;
    }

    public String getCgcEnt() {
        return this.cgcEnt;
    }

    public void setCgcEnt(String cgcEnt) {
        this.cgcEnt = cgcEnt;
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
                "codCli='" + codCli + '\'' +
                ", cliente='" + cliente + '\'' +
                ", cgcEnt='" + cgcEnt + '\'' +
                '}';
    }

}
