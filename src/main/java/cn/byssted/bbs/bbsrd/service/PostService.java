package cn.byssted.bbs.bbsrd.service;

import cn.byssted.bbs.bbsrd.dto.PostCreateDTO;
import cn.byssted.bbs.bbsrd.entity.Post;
import cn.byssted.bbs.bbsrd.entity.User;
import cn.byssted.bbs.bbsrd.mapper.PostMapper;
import cn.byssted.bbs.bbsrd.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 帖子服务类
 */
@Service
public class PostService {
    
    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;
    
    /**
     * 创建帖子
     * @param postCreateDTO 帖子创建信息
     * @param userId 用户ID
     * @return 创建的帖子
     */
    public Post createPost(PostCreateDTO postCreateDTO, Long userId) {
        // 确保userId不为空
        if (userId == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        
        Post post = new Post();
        post.setUserId(userId);  // 设置用户ID
        post.setSectionId(postCreateDTO.getSectionId());
        post.setTitle(postCreateDTO.getTitle());
        post.setContent(postCreateDTO.getContent());
        post.setIsHelp(postCreateDTO.getIsHelp() != null ? postCreateDTO.getIsHelp() : 0);
        post.setHelpPoints(postCreateDTO.getHelpPoints() != null ? postCreateDTO.getHelpPoints() : 0);
        post.setIsPinned(0);
        post.setIsFeatured(0);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setViewCount(0);
        post.setCommentCount(0);
        post.setLikeCount(0);
        
        // 打印日志，确认userId已设置
        System.out.println("创建帖子，用户ID: " + userId);
        
        postMapper.insert(post);
        return post;
    }
    
    /**
     * 获取帖子列表（分页）
     * @param page 页码
     * @param size 每页大小
     * @param sectionId 板块ID（可选）
     * @return 帖子列表
     */
    public IPage<Post> getPostList(int page, int size, Integer sectionId) {
        Page<Post> pageObj = new Page<>(page, size);
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        
        if (sectionId != null) {
            queryWrapper.eq("section_id", sectionId);
        }
        
        // 置顶帖子优先，然后按创建时间倒序
        queryWrapper.orderByDesc("is_pinned", "created_at");
        
        return postMapper.selectPage(pageObj, queryWrapper);
    }
    
    /**
     * 根据ID获取帖子详情
     * @param postId 帖子ID
     * @return 帖子详情
     */
    public Post getPostById(Long postId) {
        Post post = postMapper.selectById(postId);
        if (post != null) {
            // 增加浏览次数
            postMapper.incrementViewCount(postId);
        }
        return post;
    }
    
    /**
     * 更新帖子
     * @param post 帖子信息
     * @return 更新结果
     */
    public boolean updatePost(Post post) {
        post.setUpdatedAt(LocalDateTime.now());
        return postMapper.updateById(post) > 0;
    }
    
    /**
     * 删除帖子
     * @param postId 帖子ID
     * @param userId 用户ID
     * @return 删除结果
     */
    public boolean deletePost(Long postId, Long userId) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            return false;
        }
        User user = userMapper.selectById(userId);
        if (!post.getUserId().equals(userId) && !user.getIsAdmin().equals("1")) {
            return false;
        }
        return postMapper.deleteById(postId) > 0;
    }
    
    /**
     * 点赞帖子
     * @param postId 帖子ID
     * @return 点赞结果
     */
    public boolean likePost(Long postId) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            return false;
        }
        postMapper.incrementLikeCount(postId);
        return true;
    }
    
    /**
     * 取消点赞
     * @param postId 帖子ID
     * @return 取消点赞结果
     */
    public boolean unlikePost(Long postId) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            return false;
        }
        postMapper.decrementLikeCount(postId);
        return true;
    }
    
    /**
     * 置顶帖子（管理员功能）
     * @param postId 帖子ID
     * @param isPinned 是否置顶
     * @return 置顶结果
     */
    public boolean pinPost(Long postId, Integer isPinned) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            return false;
        }
        post.setIsPinned(isPinned);
        post.setUpdatedAt(LocalDateTime.now());
        return postMapper.updateById(post) > 0;
    }
    
    /**
     * 加精帖子（管理员功能）
     * @param postId 帖子ID
     * @param isFeatured 是否加精
     * @return 加精结果
     */
    public boolean featurePost(Long postId, Integer isFeatured) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            return false;
        }
        post.setIsFeatured(isFeatured);
        post.setUpdatedAt(LocalDateTime.now());
        return postMapper.updateById(post) > 0;
    }
    
    /**
     * 获取用户发布的帖子
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 帖子列表
     */
    public IPage<Post> getUserPosts(Long userId, int page, int size) {
        Page<Post> pageObj = new Page<>(page, size);
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByDesc("created_at");
        
        return postMapper.selectPage(pageObj, queryWrapper);
    }
}
