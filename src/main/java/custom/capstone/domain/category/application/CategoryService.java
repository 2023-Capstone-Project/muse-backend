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

    /**
     * 카테고리 생성
     */
    @Transactional
    public Long saveCategory(final CategorySaveRequestDto requestDto) {
        final Category category = Category.builder().title(requestDto.title()).build();

        return categoryRepository.save(category).getId();
    }

    /**
     * 카테고리 조회
     */
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    /**
     * 카테고리명 변경
     */
    @Transactional
    public Long updateCategory(final Long id, final CategoryUpdateRequestDto requestDto) {
        final Category category = categoryRepository.findById(id)
                .orElseThrow();

        category.updateTitle(requestDto.title());

        return id;
    }

    @Transactional
    public void deleteCategory(final Long id) {
        final Category category = categoryRepository.findById(id)
                .orElseThrow();

        categoryRepository.delete(category);
    }
}
