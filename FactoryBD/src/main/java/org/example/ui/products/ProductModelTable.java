package org.example.ui.products;

import org.example.dao.BaseDao;
import org.example.model.Department;
import org.example.model.ProductCategory;
import org.example.model.ProductModel;
import org.example.ui.BaseTable;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProductModelTable extends BaseTable<ProductModel> {
    private DefaultTableModel model;

    public ProductModelTable() {
        model = new DefaultTableModel(
                new Object[]{"ID", "Title", "Category", "Department"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        setModel(model);
    }

    public void set(List<ProductModel> models) {
        model.setRowCount(0); // Очищаем таблицу

        for (ProductModel pm : models) {
            ProductCategory category = pm.getCategory();
            Department department = pm.getDepartment();

            model.addRow(new Object[]{
                    pm.getId(),
                    pm.getTitle(),
                    category != null ? category.getTitle() : "N/A",
                    department != null ? department.getName() : "N/A"
            });
        }
    }

    public ProductModel getSelected() {
        int selectedRow = getSelectedRow();
        if (selectedRow >= 0) {
            long id = (long) model.getValueAt(selectedRow, 0);

            return new BaseDao<ProductModel>(ProductModel.class).getById(id);
        }
        return null;
    }
}