package cn.byssted.bbs.bbsrd.dto;

/**
 * 用户登录数据传输对象
 */
public class UserLoginDTO {
    
    private String email;
    private String password;
    
    // 构造函数
    public UserLoginDTO() {}
    
    public UserLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
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
    
    @Override
    public String toString() {
        return "UserLoginDTO{" +
                "email='" + email + '\'' +
                '}';
    }
}
