package org.example.ui;

import org.example.model.Product;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProductTable extends JTable {
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

    private static class ProductTableModel extends AbstractTableModel {
        private final String[] columnNames = {"ID", "Name", "Price", "Description"};
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
                case 1: return product.getName();
                case 2: return String.format("$%.2f", product.getPrice());
                case 3: return product.getDescription();
                default: return null;
            }
        }
    }
}
