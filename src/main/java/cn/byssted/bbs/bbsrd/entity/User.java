package cn.byssted.bbs.bbsrd.entity;

import com.baomidou.mybatisplus.annotation.*;
import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * 用户实体类
 */
@TableName("user")
public class User {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @TableField("email")
    private String email;
    
    @TableField("password")
    private String password;
    
    @TableField("name")
    private String name;
    
    @TableField("contact")
    private String contact;
    
    @TableField("job_type")
    private String jobType;
    
    @TableField("job_location")
    private String jobLocation;
    
    @TableField("created_at")
    private LocalDateTime createdAt;
    
    @TableField("is_admin")
    private String isAdmin;
    
    @TableField("user_point")
    private Integer userPoint;
    
    @TableLogic
    @TableField("deleted")
    private Integer deleted;
    
    // 添加上次签到日期字段
    private LocalDate lastCheckInDate;
    
    // 构造函数
    public User() {}
    
    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.isAdmin = "0";
        this.userPoint = 50; // 注册赠送50积分
        this.deleted = 0;
    }
    
    // Getter 和 Setter 方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public String getContact() {
        return contact;
    }
    
    public void setContact(String contact) {
        this.contact = contact;
    }
    
    public String getJobType() {
        return jobType;
    }
    
    public void setJobType(String jobType) {
        this.jobType = jobType;
    }
    
    public String getJobLocation() {
        return jobLocation;
    }
    
    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getIsAdmin() {
        return isAdmin;
    }
    
    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    public Integer getUserPoint() {
        return userPoint;
    }
    
    public void setUserPoint(Integer userPoint) {
        this.userPoint = userPoint;
    }
    
    public Integer getDeleted() {
        return deleted;
    }
    
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
    
    public LocalDate getLastCheckInDate() {
        return lastCheckInDate;
    }

    public void setLastCheckInDate(LocalDate lastCheckInDate) {
        this.lastCheckInDate = lastCheckInDate;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", jobType='" + jobType + '\'' +
                ", jobLocation='" + jobLocation + '\'' +
                ", createdAt=" + createdAt +
                ", isAdmin='" + isAdmin + '\'' +
                ", userPoint=" + userPoint +
                '}';
    }
}
