package custom.capstone.domain.category.application;

import custom.capstone.domain.category.dao.CategoryRepository;
import custom.capstone.domain.category.domain.Category;
import custom.capstone.domain.category.dto.request.CategorySaveRequestDto;
import custom.capstone.domain.category.dto.request.CategoryUpdateRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public Long saveCategory(CategorySaveRequestDto requestDto) {
        Category category = Category.builder().title(requestDto.title()).build();

        return categoryRepository.save(category).getId();
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Long updateCategory(Long id, CategoryUpdateRequestDto requestDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow();

        category.updateTitle(requestDto.changeTitle());

        return id;
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow();

        categoryRepository.delete(category);
    }
}
