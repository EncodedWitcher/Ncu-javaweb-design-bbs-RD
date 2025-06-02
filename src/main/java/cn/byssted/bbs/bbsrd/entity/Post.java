package cn.byssted.bbs.bbsrd.entity;

import com.baomidou.mybatisplus.annotation.*;
import java.time.LocalDateTime;

/**
 * 帖子实体类
 */
@TableName("post")
public class Post {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @TableField("user_id")
    private Long userId;
    
    @TableField("section_id")
    private Integer sectionId;
    
    @TableField("title")
    private String title;
    
    @TableField("content")
    private String content;
    
    @TableField("is_help")
    private Integer isHelp;
    
    @TableField("help_points")
    private Integer helpPoints;
    
    @TableField("is_pinned")
    private Integer isPinned;
    
    @TableField("is_featured")
    private Integer isFeatured;
    
    @TableField("created_at")
    private LocalDateTime createdAt;
    
    @TableField("updated_at")
    private LocalDateTime updatedAt;
    
    @TableField("view_count")
    private Integer viewCount;
    
    @TableField("comment_count")
    private Integer commentCount;
    
    @TableField("like_count")
    private Integer likeCount;
    
    @TableLogic
    @TableField("deleted")
    private Integer deleted;
    
    // 构造函数
    public Post() {}
    
    public Post(Long userId, Integer sectionId, String title, String content) {
        this.userId = userId;
        this.sectionId = sectionId;
        this.title = title;
        this.content = content;
        this.isHelp = 0;
        this.helpPoints = 0;
        this.isPinned = 0;
        this.isFeatured = 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.viewCount = 0;
        this.commentCount = 0;
        this.likeCount = 0;
        this.deleted = 0;
    }
    
    // Getter 和 Setter 方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
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
    
    public Integer getIsPinned() {
        return isPinned;
    }
    
    public void setIsPinned(Integer isPinned) {
        this.isPinned = isPinned;
    }
    
    public Integer getIsFeatured() {
        return isFeatured;
    }
    
    public void setIsFeatured(Integer isFeatured) {
        this.isFeatured = isFeatured;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Integer getViewCount() {
        return viewCount;
    }
    
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
    
    public Integer getCommentCount() {
        return commentCount;
    }
    
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
    
    public Integer getLikeCount() {
        return likeCount;
    }
    
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }
    
    public Integer getDeleted() {
        return deleted;
    }
    
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
    
    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userId=" + userId +
                ", sectionId=" + sectionId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", isHelp=" + isHelp +
                ", helpPoints=" + helpPoints +
                ", isPinned=" + isPinned +
                ", isFeatured=" + isFeatured +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", viewCount=" + viewCount +
                ", commentCount=" + commentCount +
                ", likeCount=" + likeCount +
                '}';
    }
}
