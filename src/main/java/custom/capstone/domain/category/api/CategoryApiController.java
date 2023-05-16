package custom.capstone.domain.category.api;

import custom.capstone.domain.category.application.CategoryService;
import custom.capstone.domain.category.domain.Category;
import custom.capstone.domain.category.dto.CategorySaveRequestDto;
import custom.capstone.domain.category.dto.CategoryUpdateRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryApiController {
    private final CategoryService categoryService;

    @PostMapping
    public Long saveCategory(@RequestBody CategorySaveRequestDto requestDto) {
        return categoryService.saveCategory(requestDto);
    }

    @PatchMapping("/{id}")
    public Long updateCategory(@PathVariable Long id, @RequestBody CategoryUpdateRequestDto requestDto) {
        return categoryService.updateCategory(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public Long deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return id;
    }

    @GetMapping
    public List<Category> findAllCategory() {
        return categoryService.findAll();
    }
}
