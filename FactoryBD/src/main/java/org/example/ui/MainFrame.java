package org.example.ui;

import org.example.model.Product;
import org.example.model.ProductDao;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    private final ProductDao productDao;
    private ProductTable productTable;

    public MainFrame() {
        this.productDao = new ProductDao();
        initializeUI();
        loadProducts();
    }

    private void initializeUI() {
        setTitle("Product Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Создаем панель с кнопками
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Product");
        JButton editButton = new JButton("Edit Product");
        JButton deleteButton = new JButton("Delete Product");

        addButton.addActionListener(e -> showProductForm(null));
        editButton.addActionListener(e -> {
            Product selected = productTable.getSelectedProduct();
            if (selected != null) {
                showProductForm(selected);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a product to edit");
            }
        });

        deleteButton.addActionListener(e -> {
            Product selected = productTable.getSelectedProduct();
            if (selected != null) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this product?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    productDao.deleteProduct(selected.getId());
                    loadProducts();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a product to delete");
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Создаем таблицу продуктов
        productTable = new ProductTable();
        JScrollPane scrollPane = new JScrollPane(productTable);

        // Добавляем компоненты на форму
        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadProducts() {
        List<Product> products = productDao.getAllProducts();
        productTable.setProducts(products);
    }

    private void showProductForm(Product product) {
        ProductForm form = new ProductForm(this, product);
        form.setVisible(true);
    }

    public void saveProduct(Product product) {
        if (product.getId() == null) {
            productDao.saveProduct(product);
        } else {
            productDao.updateProduct(product);
        }
        loadProducts();
    }
}