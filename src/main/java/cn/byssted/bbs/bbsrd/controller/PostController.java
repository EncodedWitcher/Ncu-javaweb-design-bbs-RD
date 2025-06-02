package cn.byssted.bbs.bbsrd.controller;

import cn.byssted.bbs.bbsrd.annotation.AuthRequired;
import cn.byssted.bbs.bbsrd.common.Result;
import cn.byssted.bbs.bbsrd.dto.PostCreateDTO;
import cn.byssted.bbs.bbsrd.entity.Post;
import cn.byssted.bbs.bbsrd.service.PostService;
import cn.byssted.bbs.bbsrd.util.JwtUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 帖子控制器
 */
@Tag(name = "帖子管理", description = "帖子相关接口")
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * 获取帖子列表
     */
    @Operation(summary = "获取帖子列表", description = "分页获取帖子列表，支持按板块筛选")
    @GetMapping
    public Result<IPage<Post>> getPostList(@Parameter(description = "页码，默认为1") @RequestParam(defaultValue = "1") int page,
                                          @Parameter(description = "每页大小，默认为10") @RequestParam(defaultValue = "10") int size,
                                          @Parameter(description = "板块ID，可选") @RequestParam(required = false) Integer sectionId) {
        try {
            IPage<Post> postList = postService.getPostList(page, size, sectionId);
            return Result.success(postList);
        } catch (Exception e) {
            return Result.error("获取帖子列表失败：" + e.getMessage());
        }
    }

    /**
     * 获取帖子详情
     */
    @Operation(summary = "获取帖子详情", description = "根据帖子ID获取帖子详细信息")
    @GetMapping("/{id}")
    public Result<Post> getPostById(@Parameter(description = "帖子ID") @PathVariable Long id) {
        try {
            Post post = postService.getPostById(id);
            if (post == null) {
                return Result.notFound("帖子不存在");
            }
            return Result.success(post);
        } catch (Exception e) {
            return Result.error("获取帖子详情失败：" + e.getMessage());
        }
    }

    /**
     * 发布帖子
     */
    @Operation(summary = "发布帖子", description = "发布新帖子（需要登录）")
    @AuthRequired
    @PostMapping
    public Result<Post> createPost(HttpServletRequest request, @Parameter(description = "帖子信息") @RequestBody PostCreateDTO postCreateDTO) {
        try {
            Long userId = (Long) request.getAttribute("userId");

            // 参数验证
            if (postCreateDTO.getTitle() == null || postCreateDTO.getTitle().trim().isEmpty()) {
                return Result.badRequest("标题不能为空");
            }
            if (postCreateDTO.getContent() == null || postCreateDTO.getContent().trim().isEmpty()) {
                return Result.badRequest("内容不能为空");
            }
            if (postCreateDTO.getSectionId() == null) {
                return Result.badRequest("板块不能为空");
            }

            Post post = postService.createPost(postCreateDTO, userId);
            return Result.success("发布成功", post);
        } catch (Exception e) {
            return Result.error("发布帖子失败：" + e.getMessage());
        }
    }

    /**
     * 更新帖子
     */
    @Operation(summary = "更新帖子", description = "更新帖子信息（仅作者和管理员可操作）")
    @AuthRequired
    @PutMapping("/{id}")
    public Result<String> updatePost(HttpServletRequest request, @Parameter(description = "帖子ID") @PathVariable Long id, @Parameter(description = "帖子信息") @RequestBody PostCreateDTO postCreateDTO) {
        try {
            Long userId = (Long) request.getAttribute("userId");

            Post post = postService.getPostById(id);
            if (post == null) {
                return Result.notFound("帖子不存在");
            }

            // 只有作者可以修改帖子
            if (!post.getUserId().equals(userId)) {
                return Result.forbidden("无权限修改此帖子");
            }

            // 更新帖子信息
            if (postCreateDTO.getTitle() != null) {
                post.setTitle(postCreateDTO.getTitle());
            }
            if (postCreateDTO.getContent() != null) {
                post.setContent(postCreateDTO.getContent());
            }
            if (postCreateDTO.getSectionId() != null) {
                post.setSectionId(postCreateDTO.getSectionId());
            }

            boolean success = postService.updatePost(post);
            if (success) {
                return Result.success("更新成功");
            } else {
                return Result.error("更新失败");
            }
        } catch (Exception e) {
            return Result.error("更新帖子失败：" + e.getMessage());
        }
    }

    /**
     * 删除帖子
     */
    @Operation(summary = "删除帖子", description = "删除帖子（仅作者可操作）")
    @AuthRequired
    @DeleteMapping("/{id}")
    public Result<String> deletePost(HttpServletRequest request, @Parameter(description = "帖子ID") @PathVariable Long id) {
        try {
            Long userId = (Long) request.getAttribute("userId");

            boolean success = postService.deletePost(id, userId);
            if (success) {
                return Result.success("删除成功");
            } else {
                return Result.error("删除失败或无权限");
            }
        } catch (Exception e) {
            return Result.error("删除帖子失败：" + e.getMessage());
        }
    }

    /**
     * 点赞帖子
     */
    @Operation(summary = "点赞帖子", description = "为帖子点赞（需要登录）")
    @AuthRequired
    @PostMapping("/{id}/like")
    public Result<String> likePost(@Parameter(description = "帖子ID") @PathVariable Long id) {
        try {
            boolean success = postService.likePost(id);
            if (success) {
                return Result.success("点赞成功");
            } else {
                return Result.error("点赞失败");
            }
        } catch (Exception e) {
            return Result.error("点赞失败：" + e.getMessage());
        }
    }

    /**
     * 获取用户发布的帖子
     */
    @Operation(summary = "获取用户发布的帖子", description = "分页获取指定用户发布的帖子列表")
    @GetMapping("/user/{userId}")
    public Result<IPage<Post>> getUserPosts(@Parameter(description = "用户ID") @PathVariable Long userId,
                                           @Parameter(description = "页码，默认为1") @RequestParam(defaultValue = "1") int page,
                                           @Parameter(description = "每页大小，默认为10") @RequestParam(defaultValue = "10") int size) {
        try {
            IPage<Post> postList = postService.getUserPosts(userId, page, size);
            return Result.success(postList);
        } catch (Exception e) {
            return Result.error("获取用户帖子失败：" + e.getMessage());
        }
    }
}
