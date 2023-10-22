package custom.capstone.domain.category.api;

import custom.capstone.domain.category.application.CategoryService;
import custom.capstone.domain.category.domain.Category;
import custom.capstone.domain.category.dto.request.CategorySaveRequestDto;
import custom.capstone.domain.category.dto.request.CategoryUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "카테고리 API")
@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryApiController {
    private final CategoryService categoryService;

    @Operation(summary = "카테고리 설정")
    @PostMapping
    public Long saveCategory(
            @AuthenticationPrincipal final String loginEmail,
            @RequestBody final CategorySaveRequestDto requestDto
    ) {
        return categoryService.saveCategory(loginEmail, requestDto);
    }

    @Operation(summary = "카테고리 수정")
    @PatchMapping("/{categoryId}")
    public Long updateCategory(
            @AuthenticationPrincipal final String loginEmail,
            @PathVariable("categoryId") final Long id,
            @RequestBody final CategoryUpdateRequestDto requestDto
    ) {
        return categoryService.updateCategory(loginEmail, id, requestDto);
    }

    @Operation(summary = "카테고리 삭제")
    @DeleteMapping("/{categoryId}")
    public Long deleteCategory(
            @AuthenticationPrincipal final String loginEmail,
            @PathVariable("categoryId") final Long id
    ) {
        categoryService.deleteCategory(loginEmail, id);
        return id;
    }

    @Operation(summary = "카테고리 조회")
    @GetMapping
    public List<Category> findAllCategory() {
        return categoryService.findAll();
    }
}
