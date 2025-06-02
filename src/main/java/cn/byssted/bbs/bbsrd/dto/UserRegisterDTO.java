package cn.byssted.bbs.bbsrd.dto;

/**
 * 用户注册数据传输对象
 */
public class UserRegisterDTO {
    
    private String email;
    private String password;
    private String name;
    
    // 构造函数
    public UserRegisterDTO() {}
    
    public UserRegisterDTO(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
    
    // Getter 和 Setter 方法
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "UserRegisterDTO{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
