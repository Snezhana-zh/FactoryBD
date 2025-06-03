package org.example.ui.products;

import org.example.dao.BaseDao;
import org.example.model.BaseModel;
import org.example.model.ProductCategory;
import org.example.ui.BasePanel;

import javax.swing.*;

public class ProductCategoryPanel extends BasePanel {

    public ProductCategoryPanel(BaseDao<ProductCategory> dao, JFrame mainFrame) {
        super(dao, mainFrame, new ProductCategoryTable());
    }

    @Override
    protected void showForm(BaseModel el) {
        ProductCategoryForm form = new ProductCategoryForm(mainFrame, (ProductCategory) el);
        form.setVisible(true);
    }
}