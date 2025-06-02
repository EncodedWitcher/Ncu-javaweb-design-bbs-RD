package cn.byssted.bbs.bbsrd.mapper;

import cn.byssted.bbs.bbsrd.entity.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * 帖子数据访问层
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {
    
    /**
     * 增加浏览次数
     * @param postId 帖子ID
     */
    @Update("UPDATE post SET view_count = view_count + 1 WHERE id = #{postId}")
    void incrementViewCount(Long postId);
    
    /**
     * 增加点赞数
     * @param postId 帖子ID
     */
    @Update("UPDATE post SET like_count = like_count + 1 WHERE id = #{postId}")
    void incrementLikeCount(Long postId);
    
    /**
     * 减少点赞数
     * @param postId 帖子ID
     */
    @Update("UPDATE post SET like_count = like_count - 1 WHERE id = #{postId} AND like_count > 0")
    void decrementLikeCount(Long postId);
    
    /**
     * 增加评论数
     * @param postId 帖子ID
     */
    @Update("UPDATE post SET comment_count = comment_count + 1 WHERE id = #{postId}")
    void incrementCommentCount(Long postId);
    
    /**
     * 减少评论数
     * @param postId 帖子ID
     */
    @Update("UPDATE post SET comment_count = comment_count - 1 WHERE id = #{postId} AND comment_count > 0")
    void decrementCommentCount(Long postId);
}
