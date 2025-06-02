package cn.byssted.bbs.bbsrd.controller;

import cn.byssted.bbs.bbsrd.annotation.AuthRequired;
import cn.byssted.bbs.bbsrd.common.Result;
import cn.byssted.bbs.bbsrd.dto.UserLoginDTO;
import cn.byssted.bbs.bbsrd.dto.UserRegisterDTO;
import cn.byssted.bbs.bbsrd.entity.User;
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
 * 用户控制器
 */
@Tag(name = "用户管理", description = "用户相关接口")
@RestController
@RequestMapping("/api/users")
// 移除 @CrossOrigin 注解，使用全局配置
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户注册
     */
    @Operation(summary = "用户注册", description = "新用户注册接口")
    @PostMapping("/register")
    public Result<Object> register(@Parameter(description = "用户注册信息") @RequestBody UserRegisterDTO registerDTO) {
        try {
            // 参数验证
            if (registerDTO.getEmail() == null || registerDTO.getEmail().trim().isEmpty()) {
                return Result.badRequest("邮箱不能为空");
            }
            if (registerDTO.getPassword() == null || registerDTO.getPassword().trim().isEmpty()) {
                return Result.badRequest("密码不能为空");
            }
            if (registerDTO.getName() == null || registerDTO.getName().trim().isEmpty()) {
                return Result.badRequest("昵称不能为空");
            }

            Map<String, Object> result = userService.register(registerDTO);
            if ((Boolean) result.get("success")) {
                return Result.success(result.get("message").toString(), result.get("user"));
            } else {
                return Result.badRequest(result.get("message").toString());
            }
        } catch (Exception e) {
            return Result.error("注册失败：" + e.getMessage());
        }
    }

    /**
     * 用户登录
     */
    @Operation(summary = "用户登录", description = "用户登录接口")
    @PostMapping("/login")
    public Result<Object> login(@Parameter(description = "用户登录信息") @RequestBody UserLoginDTO loginDTO) {
        try {
            // 参数验证
            if (loginDTO.getEmail() == null || loginDTO.getEmail().trim().isEmpty()) {
                return Result.badRequest("邮箱不能为空");
            }
            if (loginDTO.getPassword() == null || loginDTO.getPassword().trim().isEmpty()) {
                return Result.badRequest("密码不能为空");
            }

            Map<String, Object> result = userService.login(loginDTO);
            if ((Boolean) result.get("success")) {
                return Result.success(result.get("message").toString(), result);
            } else {
                return Result.badRequest(result.get("message").toString());
            }
        } catch (Exception e) {
            return Result.error("登录失败：" + e.getMessage());
        }
    }

    /**
     * 获取用户信息
     */
    @Operation(summary = "获取用户信息", description = "获取当前登录用户的个人信息")
    @AuthRequired
    @GetMapping("/profile")
    public Result<User> getProfile(HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");

            User user = userService.getUserById(userId);
            if (user == null) {
                return Result.notFound("用户不存在");
            }

            // 隐藏密码
            user.setPassword(null);
            return Result.success(user);
        } catch (Exception e) {
            return Result.error("获取用户信息失败：" + e.getMessage());
        }
    }

    /**
     * 更新用户信息
     */
    @Operation(summary = "更新用户信息", description = "更新当前登录用户的个人信息")
    @AuthRequired
    @PutMapping("/profile")
    public Result<String> updateProfile(HttpServletRequest request, @Parameter(description = "用户信息") @RequestBody User userInfo) {
        try {
            Long userId = (Long) request.getAttribute("userId");

            User user = userService.getUserById(userId);
            if (user == null) {
                return Result.notFound("用户不存在");
            }

            // 只允许更新部分字段
            if (userInfo.getName() != null) {
                user.setName(userInfo.getName());
            }
            if (userInfo.getContact() != null) {
                user.setContact(userInfo.getContact());
            }
            if (userInfo.getJobType() != null) {
                user.setJobType(userInfo.getJobType());
            }
            if (userInfo.getJobLocation() != null) {
                user.setJobLocation(userInfo.getJobLocation());
            }

            boolean success = userService.updateUser(user);
            if (success) {
                return Result.success("更新成功");
            } else {
                return Result.error("更新失败");
            }
        } catch (Exception e) {
            return Result.error("更新用户信息失败：" + e.getMessage());
        }
    }

    /**
     * 用户签到
     */
    @Operation(summary = "用户签到", description = "用户每日签到获取积分")
    @AuthRequired
    @PostMapping("/checkin")
    public Result<Object> checkIn(HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");

            Map<String, Object> result = userService.checkIn(userId);
            if ((Boolean) result.get("success")) {
                return Result.success(result.get("message").toString(), result);
            } else {
                return Result.error(result.get("message").toString());
            }
        } catch (Exception e) {
            return Result.error("签到失败：" + e.getMessage());
        }
    }
}
