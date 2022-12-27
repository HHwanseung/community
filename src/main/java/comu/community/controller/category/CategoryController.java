package comu.community.controller.category;

import comu.community.dto.category.CategoryCreateRequest;
import comu.community.response.Response;
import comu.community.service.category.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Category Controller", tags = "Category")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperation(value = "모든 카테고리 조회")
    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    public Response findAll() {
        return Response.success(categoryService.findAll());
    }

    @ApiOperation(value = "카테고리 생성")
    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public Response createCategory(@Valid @RequestBody CategoryCreateRequest req) {
        categoryService.create(req);
        return Response.success();
    }

    @ApiOperation(value = "카테고리 첫 생성")
    @PostMapping("/categories/start")
    @ResponseStatus(HttpStatus.CREATED)
    public Response createCategoryAtFirst() {
        categoryService.createAtFirst();
        return Response.success();
    }

    @ApiOperation(value = "카테고리 삭제")
    @DeleteMapping("/categories/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response deleteCategory(@ApiParam(value = "카테고리 id", required = true) @PathVariable Long id) {
        categoryService.delete(id);
        return Response.success();
    }
}
