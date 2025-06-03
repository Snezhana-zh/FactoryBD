package org.example.ui.products;

import org.example.model.BaseModel;
import org.example.model.Product;
import org.example.ui.BaseTable;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProductTable extends BaseTable {
    private ProductTableModel model;

    public ProductTable() {
        model = new ProductTableModel();
        setModel(model);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void setProducts(List<Product> products) {
        model.setProducts(products);
    }

    public Product getSelectedProduct() {
        int selectedRow = getSelectedRow();
        if (selectedRow >= 0) {
            return model.getProductAt(selectedRow);
        }
        return null;
    }

    @Override
    public void set(List products) {
        model.setProducts(products);
    }

    @Override
    public BaseModel getSelected() {
        int selectedRow = getSelectedRow();
        if (selectedRow >= 0) {
            return model.getProductAt(selectedRow);
        }
        return null;
    }

    private static class ProductTableModel extends AbstractTableModel {
        // private final String[] columnNames = {"ID", "Name", "Price", "Description"};
        private final String[] columnNames = {"ID", "Model"};
        private List<Product> products = new ArrayList<>();

        public void setProducts(List<Product> products) {
            this.products = products;
            fireTableDataChanged();
        }

        public Product getProductAt(int row) {
            return products.get(row);
        }

        @Override
        public int getRowCount() {
            return products.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int row, int column) {
            Product product = products.get(row);
            switch (column) {
                case 0: return product.getId();
                case 1: return product.getModel().getTitle();
                default: return null;
                /*case 0: return product.getId();
                case 1: return product.getName();
                case 2: return String.format("$%.2f", product.getPrice());
                case 3: return product.getDescription();
                default: return null;*/
            }
        }
    }
}
