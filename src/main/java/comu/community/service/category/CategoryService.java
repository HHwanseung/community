package comu.community.service.category;

import comu.community.dto.category.CategoryCreateRequest;
import comu.community.dto.category.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> findAll();
    void createAtFirst();
    void create(CategoryCreateRequest req);
    void delete(Long id);

}
