package cn.byssted.bbs.bbsrd.controller;

import cn.byssted.bbs.bbsrd.annotation.AdminRequired;
import cn.byssted.bbs.bbsrd.common.Result;
import cn.byssted.bbs.bbsrd.service.PostService;
import cn.byssted.bbs.bbsrd.service.UserService;
import cn.byssted.bbs.bbsrd.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 管理员控制器
 */
@Tag(name = "管理员功能", description = "管理员专用接口")
@RestController
@RequestMapping("/api/admin")
@AdminRequired  // 整个控制器都需要管理员权限
public class AdminController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    /**
     * 置顶帖子
     */
    @Operation(summary = "置顶帖子", description = "设置或取消帖子置顶状态（管理员权限）")
    @PutMapping("/posts/{id}/pin")
    public Result<String> pinPost(@Parameter(description = "帖子ID") @PathVariable Long id, @Parameter(description = "置顶状态信息") @RequestBody Map<String, Integer> request) {
        try {
            Integer isPinned = request.get("isPinned");
            if (isPinned == null) {
                return Result.badRequest("置顶状态不能为空");
            }

            boolean success = postService.pinPost(id, isPinned);
            if (success) {
                String message = isPinned == 1 ? "置顶成功" : "取消置顶成功";
                return Result.success(message);
            } else {
                return Result.error("操作失败");
            }
        } catch (Exception e) {
            return Result.error("置顶操作失败：" + e.getMessage());
        }
    }

    /**
     * 加精帖子
     */
    @Operation(summary = "加精帖子", description = "设置或取消帖子加精状态（管理员权限）")
    @PutMapping("/posts/{id}/feature")
    public Result<String> featurePost(@Parameter(description = "帖子ID") @PathVariable Long id, @Parameter(description = "加精状态信息") @RequestBody Map<String, Integer> request) {
        try {
            Integer isFeatured = request.get("isFeatured");
            if (isFeatured == null) {
                return Result.badRequest("加精状态不能为空");
            }

            boolean success = postService.featurePost(id, isFeatured);
            if (success) {
                String message = isFeatured == 1 ? "加精成功" : "取消加精成功";

                // 如果是加精，给帖子作者奖励100积分
                if (isFeatured == 1) {
                    // 这里需要获取帖子作者ID并奖励积分
                    // 为了简化，暂时不实现，可以后续添加
                }

                return Result.success(message);
            } else {
                return Result.error("操作失败");
            }
        } catch (Exception e) {
            return Result.error("加精操作失败：" + e.getMessage());
        }
    }

    /**
     * 调整用户积分
     */
    @Operation(summary = "调整用户积分", description = "增加或减少用户积分（管理员权限）")
    @PutMapping("/users/{id}/points")
    public Result<String> adjustUserPoints(@Parameter(description = "用户ID") @PathVariable Long id, @Parameter(description = "积分调整信息") @RequestBody Map<String, Integer> request) {
        try {
            Integer points = request.get("points");
            if (points == null) {
                return Result.badRequest("积分变化量不能为空");
            }

            boolean success = userService.adjustUserPoints(id, points);
            if (success) {
                String message = points > 0 ? "积分奖励成功" : "积分扣除成功";
                return Result.success(message);
            } else {
                return Result.error("操作失败");
            }
        } catch (Exception e) {
            return Result.error("积分调整失败：" + e.getMessage());
        }
    }
}
