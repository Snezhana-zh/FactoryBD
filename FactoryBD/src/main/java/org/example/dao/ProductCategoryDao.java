package org.example.dao;

import org.example.model.ProductCategory;

public class ProductCategoryDao extends BaseDao<ProductCategory> {
    public ProductCategoryDao(Class<ProductCategory> entityClass) {
        super(entityClass);
    }
}
