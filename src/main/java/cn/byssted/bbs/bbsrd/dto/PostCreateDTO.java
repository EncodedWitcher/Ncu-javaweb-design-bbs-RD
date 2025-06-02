package cn.byssted.bbs.bbsrd.dto;

/**
 * 帖子创建数据传输对象
 */
public class PostCreateDTO {
    
    private Integer sectionId;
    private String title;
    private String content;
    private Integer isHelp;
    private Integer helpPoints;
    
    // 构造函数
    public PostCreateDTO() {}
    
    public PostCreateDTO(Integer sectionId, String title, String content) {
        this.sectionId = sectionId;
        this.title = title;
        this.content = content;
        this.isHelp = 0;
        this.helpPoints = 0;
    }
    
    // Getter 和 Setter 方法
    public Integer getSectionId() {
        return sectionId;
    }
    
    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public Integer getIsHelp() {
        return isHelp;
    }
    
    public void setIsHelp(Integer isHelp) {
        this.isHelp = isHelp;
    }
    
    public Integer getHelpPoints() {
        return helpPoints;
    }
    
    public void setHelpPoints(Integer helpPoints) {
        this.helpPoints = helpPoints;
    }
    
    @Override
    public String toString() {
        return "PostCreateDTO{" +
                "sectionId=" + sectionId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", isHelp=" + isHelp +
                ", helpPoints=" + helpPoints +
                '}';
    }
}
