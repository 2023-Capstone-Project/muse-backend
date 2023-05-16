package custom.capstone.domain.category.application;

import custom.capstone.domain.category.dao.CategoryRepository;
import custom.capstone.domain.category.domain.Category;
import custom.capstone.domain.category.dto.CategorySaveRequestDto;
import custom.capstone.domain.category.dto.CategoryUpdateRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
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

    /**
     * 카테고리 초기화
     */
    @PostConstruct
    public void initCategory() {
        List<Category> categories = categoryRepository.findAll();

        if(!categories.isEmpty())
            return;

        Category female = Category.builder().title("여성의류").build();
        Category male = Category.builder().title("남성의류").build();
        Category shoes = Category.builder().title("가방").build();
        Category bag = Category.builder().title("가방").build();
        Category acc = Category.builder().title("악세사리").build();
        Category tech = Category.builder().title("폰케이스/테크").build();

        categoryRepository.save(female);
        categoryRepository.save(male);
        categoryRepository.save(shoes);
        categoryRepository.save(bag);
        categoryRepository.save(acc);
        categoryRepository.save(tech);
    }
}
