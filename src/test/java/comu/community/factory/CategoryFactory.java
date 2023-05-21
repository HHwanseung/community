package comu.community.factory;

import comu.community.entity.category.Category;

public class CategoryFactory {

    public static Category createCategory(){
        return new Category("name",null);
    }
}
