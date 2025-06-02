package cn.byssted.bbs.bbsrd.service;

import cn.byssted.bbs.bbsrd.entity.Section;
import cn.byssted.bbs.bbsrd.mapper.SectionMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 板块服务类
 */
@Service
public class SectionService {
    
    @Autowired
    private SectionMapper sectionMapper;
    
    /**
     * 获取所有板块
     * @return 板块列表
     */
    public List<Section> getAllSections() {
        QueryWrapper<Section> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        return sectionMapper.selectList(queryWrapper);
    }
    
    /**
     * 根据ID获取板块
     * @param id 板块ID
     * @return 板块信息
     */
    public Section getSectionById(Integer id) {
        return sectionMapper.selectById(id);
    }
    
    /**
     * 创建板块
     * @param name 板块名称
     * @param description 板块描述
     * @return 创建的板块
     */
    public Section createSection(String name, String description) {
        Section section = new Section();
        section.setName(name);
        section.setDescription(description);
        section.setCreatedAt(LocalDateTime.now());
        
        sectionMapper.insert(section);
        return section;
    }
    
    /**
     * 更新板块
     * @param section 板块信息
     * @return 更新结果
     */
    public boolean updateSection(Section section) {
        return sectionMapper.updateById(section) > 0;
    }
    
    /**
     * 删除板块
     * @param id 板块ID
     * @return 删除结果
     */
    public boolean deleteSection(Integer id) {
        return sectionMapper.deleteById(id) > 0;
    }
}
