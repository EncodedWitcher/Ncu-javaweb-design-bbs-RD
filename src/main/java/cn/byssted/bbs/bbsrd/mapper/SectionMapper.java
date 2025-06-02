package cn.byssted.bbs.bbsrd.mapper;

import cn.byssted.bbs.bbsrd.entity.Section;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 板块数据访问层
 */
@Mapper
public interface SectionMapper extends BaseMapper<Section> {
}
