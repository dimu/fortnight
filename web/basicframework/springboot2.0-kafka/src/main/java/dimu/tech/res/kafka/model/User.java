package dimu.tech.res.kafka.model;

import java.io.Serializable;

/**
 * user domain
 * @author dwx
 *
 */
public class User implements Serializable{

    private static final long serialVersionUID = -4787773422434404792L;

    private Long id;
    
    private String account;
    
    private String password;
    
    private String salt;
    
    private String mobile;
    
    private String mail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    
}
