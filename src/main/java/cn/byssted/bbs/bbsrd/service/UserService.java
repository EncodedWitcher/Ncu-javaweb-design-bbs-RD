package cn.byssted.bbs.bbsrd.service;

import cn.byssted.bbs.bbsrd.dto.UserLoginDTO;
import cn.byssted.bbs.bbsrd.dto.UserRegisterDTO;
import cn.byssted.bbs.bbsrd.entity.User;
import cn.byssted.bbs.bbsrd.mapper.UserMapper;
import cn.byssted.bbs.bbsrd.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务类
 */
@Service
public class UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    /**
     * 用户注册
     * @param registerDTO 注册信息
     * @return 注册结果
     */
    public Map<String, Object> register(UserRegisterDTO registerDTO) {
        Map<String, Object> result = new HashMap<>();
        
        // 检查邮箱是否已存在
        if (userMapper.countByEmail(registerDTO.getEmail()) > 0) {
            result.put("success", false);
            result.put("message", "邮箱已被注册");
            return result;
        }
        
        // 创建用户
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setPassword(DigestUtils.md5DigestAsHex(registerDTO.getPassword().getBytes()));
        user.setName(registerDTO.getName());
        user.setCreatedAt(LocalDateTime.now());
        user.setIsAdmin("0");
        user.setUserPoint(50); // 注册赠送50积分
        
        int rows = userMapper.insert(user);
        if (rows > 0) {
            result.put("success", true);
            result.put("message", "注册成功");
            result.put("user", user);
        } else {
            result.put("success", false);
            result.put("message", "注册失败");
        }
        
        return result;
    }
    
    /**
     * 用户登录
     * @param loginDTO 登录信息
     * @return 登录结果
     */
    public Map<String, Object> login(UserLoginDTO loginDTO) {
        Map<String, Object> result = new HashMap<>();
        
        // 查找用户
        User user = userMapper.findByEmail(loginDTO.getEmail());
        if (user == null) {
            result.put("success", false);
            result.put("message", "用户不存在");
            return result;
        }
        
        // 验证密码
        String encryptedPassword = DigestUtils.md5DigestAsHex(loginDTO.getPassword().getBytes());
        if (!encryptedPassword.equals(user.getPassword())) {
            result.put("success", false);
            result.put("message", "密码错误");
            return result;
        }
        
        // 生成JWT Token
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getIsAdmin());
        
        result.put("success", true);
        result.put("message", "登录成功");
        result.put("token", token);
        result.put("user", user);
        
        return result;
    }
    
    /**
     * 根据ID获取用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    public User getUserById(Long userId) {
        return userMapper.selectById(userId);
    }
    
    /**
     * 根据邮箱获取用户信息
     * @param email 邮箱
     * @return 用户信息
     */
    public User getUserByEmail(String email) {
        return userMapper.findByEmail(email);
    }
    
    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 更新结果
     */
    public boolean updateUser(User user) {
        return userMapper.updateById(user) > 0;
    }
    
    /**
     * 用户签到
     * @param userId 用户ID
     * @return 签到结果
     */
    public Map<String, Object> checkIn(Long userId) {
        Map<String, Object> result = new HashMap<>();
        
        User user = userMapper.selectById(userId);
        if (user == null) {
            result.put("success", false);
            result.put("message", "用户不存在");
            return result;
        }
        
        // 获取当前日期
        LocalDate today = LocalDate.now();
        
        // 检查今天是否已经签到
        if (user.getLastCheckInDate() != null && user.getLastCheckInDate().equals(today)) {
            result.put("success", false);
            result.put("message", "今天已经签到过了");
            return result;
        }
        
        // 增加签到积分
        user.setUserPoint(user.getUserPoint() + 10);
        // 更新最后签到日期
        user.setLastCheckInDate(today);
        int rows = userMapper.updateById(user);
        
        if (rows > 0) {
            result.put("success", true);
            result.put("message", "签到成功，获得10积分");
            result.put("points", user.getUserPoint());
        } else {
            result.put("success", false);
            result.put("message", "签到失败");
        }
        
        return result;
    }
    
    /**
     * 调整用户积分
     * @param userId 用户ID
     * @param points 积分变化量
     * @return 调整结果
     */
    public boolean adjustUserPoints(Long userId, Integer points) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            return false;
        }
        
        user.setUserPoint(user.getUserPoint() + points);
        return userMapper.updateById(user) > 0;
    }
}
