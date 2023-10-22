package custom.capstone.domain.category.application;

import custom.capstone.domain.category.dao.CategoryRepository;
import custom.capstone.domain.category.domain.Category;
import custom.capstone.domain.category.dto.request.CategorySaveRequestDto;
import custom.capstone.domain.category.dto.request.CategoryUpdateRequestDto;
import custom.capstone.domain.members.dao.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    /**
     * 카테고리 생성
     */
    @Transactional
    public Long saveCategory(final String loginEmail, final CategorySaveRequestDto requestDto) {
        getValidAdmin(loginEmail);

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
    public Long updateCategory(
            final String loginEmail,
            final Long categoryId,
            final CategoryUpdateRequestDto requestDto
    ) {
        getValidAdmin(loginEmail);

        final Category category = categoryRepository.findById(categoryId)
                .orElseThrow();

        category.updateTitle(requestDto.title());

        return categoryId;
    }

    /**
     * 카테고리 삭제
     */
    @Transactional
    public void deleteCategory(final String loginEmail, final Long categoryId) {
        getValidAdmin(loginEmail);

        final Category category = categoryRepository.findById(categoryId)
                .orElseThrow();

        categoryRepository.delete(category);
    }

    // 관리자인지 확인
    private void getValidAdmin(final String loginEmail) {
        memberRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
