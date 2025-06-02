package cn.byssted.bbs.bbsrd.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 板块创建数据传输对象
 */
@Schema(description = "板块创建请求")
public class SectionCreateDTO {
    
    @Schema(description = "板块名称", example = "技术讨论", required = true)
    private String name;
    
    @Schema(description = "板块描述", example = "技术相关话题讨论区")
    private String description;
    
    // 构造函数
    public SectionCreateDTO() {}
    
    public SectionCreateDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    // Getter 和 Setter 方法
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return "SectionCreateDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
