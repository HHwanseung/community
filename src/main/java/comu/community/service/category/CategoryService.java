package comu.community.service.category;

import comu.community.dto.category.CategoryCreateRequest;
import comu.community.dto.category.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> findAllCategory();
    void createAtFirst();
    void createCategory(CategoryCreateRequest req);
    void deleteCategory(Long id);

}
