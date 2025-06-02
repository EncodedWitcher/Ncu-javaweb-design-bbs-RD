package cn.byssted.bbs.bbsrd.controller;

import cn.byssted.bbs.bbsrd.annotation.AdminRequired;
import cn.byssted.bbs.bbsrd.common.Result;
import cn.byssted.bbs.bbsrd.dto.SectionCreateDTO;
import cn.byssted.bbs.bbsrd.dto.SectionUpdateDTO;
import cn.byssted.bbs.bbsrd.entity.Section;
import cn.byssted.bbs.bbsrd.service.SectionService;
import cn.byssted.bbs.bbsrd.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 板块控制器
 */
@Tag(name = "板块管理", description = "板块相关接口")
@RestController
@RequestMapping("/api/sections")
public class SectionController {

    @Autowired
    private SectionService sectionService;

    /**
     * 获取所有板块
     */
    @Operation(summary = "获取所有板块", description = "获取系统中所有板块的列表")
    @GetMapping
    public Result<List<Section>> getAllSections() {
        try {
            List<Section> sections = sectionService.getAllSections();
            return Result.success(sections);
        } catch (Exception e) {
            return Result.error("获取板块列表失败：" + e.getMessage());
        }
    }

    /**
     * 根据ID获取板块
     */
    @Operation(summary = "根据ID获取板块", description = "根据板块ID获取板块详细信息")
    @GetMapping("/{id}")
    public Result<Section> getSectionById(@Parameter(description = "板块ID") @PathVariable Integer id) {
        try {
            Section section = sectionService.getSectionById(id);
            if (section == null) {
                return Result.notFound("板块不存在");
            }
            return Result.success(section);
        } catch (Exception e) {
            return Result.error("获取板块信息失败：" + e.getMessage());
        }
    }

    /**
     * 创建板块（管理员功能）
     */
    @Operation(summary = "创建板块", description = "创建新的板块（需要管理员权限）")
    @AdminRequired
    @PostMapping
    public Result<Section> createSection(@Parameter(description = "板块创建信息") @RequestBody SectionCreateDTO request) {
        try {
            String name = request.getName();
            String description = request.getDescription();

            if (name == null || name.trim().isEmpty()) {
                return Result.badRequest("板块名称不能为空");
            }

            Section section = sectionService.createSection(name, description);
            return Result.success("创建成功", section);
        } catch (Exception e) {
            return Result.error("创建板块失败：" + e.getMessage());
        }
    }

    /**
     * 更新板块（管理员功能）
     */
    @Operation(summary = "更新板块", description = "更新指定板块的信息（需要管理员权限）")
    @AdminRequired
    @PutMapping("/{id}")
    public Result<String> updateSection(@Parameter(description = "板块ID") @PathVariable Integer id,
                                       @Parameter(description = "板块更新信息") @RequestBody SectionUpdateDTO request) {
        try {
            Section section = sectionService.getSectionById(id);
            if (section == null) {
                return Result.notFound("板块不存在");
            }

            String name = request.getName();
            String description = request.getDescription();

            if (name != null) {
                section.setName(name);
            }
            if (description != null) {
                section.setDescription(description);
            }

            boolean success = sectionService.updateSection(section);
            if (success) {
                return Result.success("更新成功");
            } else {
                return Result.error("更新失败");
            }
        } catch (Exception e) {
            return Result.error("更新板块失败：" + e.getMessage());
        }
    }

    /**
     * 删除板块（管理员功能）
     */
    @Operation(summary = "删除板块", description = "删除指定的板块（需要管理员权限）")
    @AdminRequired
    @DeleteMapping("/{id}")
    public Result<String> deleteSection(@Parameter(description = "板块ID") @PathVariable Integer id) {
        try {
            boolean success = sectionService.deleteSection(id);
            if (success) {
                return Result.success("删除成功");
            } else {
                return Result.error("删除失败");
            }
        } catch (Exception e) {
            return Result.error("删除板块失败：" + e.getMessage());
        }
    }
}
