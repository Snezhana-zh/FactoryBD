package org.example.ui.products;

import org.example.dao.BaseDao;
import org.example.model.ProductCategory;
import org.example.ui.BaseTable;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProductCategoryTable extends BaseTable<ProductCategory> {
    private DefaultTableModel model;

    public ProductCategoryTable() {
        model = new DefaultTableModel(
                new Object[]{"ID", "Title", "Attr"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        setModel(model);
    }

    public void set(List<ProductCategory> models) {
        model.setRowCount(0); // Очищаем таблицу

        for (ProductCategory pm : models) {

            model.addRow(new Object[]{
                    pm.getId(),
                    pm.getTitle(),
                    pm.getAttributes() != null ? pm.getAttributes() : "N/A"
            });
        }
    }

    public ProductCategory getSelected() {
        int selectedRow = getSelectedRow();
        if (selectedRow >= 0) {
            long id = (long) model.getValueAt(selectedRow, 0);

            return new BaseDao<ProductCategory>(ProductCategory.class).getById(id);
        }
        return null;
    }
}