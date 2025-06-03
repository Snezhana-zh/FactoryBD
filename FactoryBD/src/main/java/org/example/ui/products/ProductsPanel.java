package org.example.ui.products;

import org.example.dao.BaseDao;
import org.example.model.BaseModel;
import org.example.model.Product;
import org.example.ui.BasePanel;

import javax.swing.*;

public class ProductsPanel extends BasePanel {
    // private ProductTable productTable;
    //private final BaseDao<Product> productDao;
    //private JFrame mainFrame;

    public ProductsPanel(BaseDao<Product> productDao, JFrame mainFrame) {

        super(productDao, mainFrame, new ProductTable());
//        this.productDao = productDao;
//        this.mainFrame = mainFrame;
//        initializeUI();
//        loadProducts();
    }

//    private void initializeUI() {
//        setLayout(new BorderLayout());
//
//        // Создаем панель с кнопками
//        JPanel buttonPanel = new JPanel();
//        JButton addButton = new JButton("Add Product");
//        JButton editButton = new JButton("Edit Product");
//        JButton deleteButton = new JButton("Delete Product");
//
//        addButton.addActionListener(e -> showProductForm(null));
//        editButton.addActionListener(e -> {
//            Product selected = productTable.getSelectedProduct();
//            if (selected != null) {
//                showProductForm(selected);
//            } else {
//                JOptionPane.showMessageDialog(mainFrame, "Please select a product to edit");
//            }
//        });
//
//        deleteButton.addActionListener(e -> {
//            Product selected = productTable.getSelectedProduct();
//            if (selected != null) {
//                int confirm = JOptionPane.showConfirmDialog(
//                        mainFrame,
//                        "Are you sure you want to delete this product?",
//                        "Confirm Delete",
//                        JOptionPane.YES_NO_OPTION);
//
//                if (confirm == JOptionPane.YES_OPTION) {
//                    productDao.delete(selected.getId());
//                    loadProducts();
//                }
//            } else {
//                JOptionPane.showMessageDialog(mainFrame, "Please select a product to delete");
//            }
//        });
//
//        buttonPanel.add(addButton);
//        buttonPanel.add(editButton);
//        buttonPanel.add(deleteButton);
//
//        // Создаем таблицу продуктов
//        productTable = new ProductTable();
//        JScrollPane scrollPane = new JScrollPane(productTable);
//
//        JButton refreshButton = new JButton("Refresh");
//        refreshButton.addActionListener(e -> loadProducts());
//
//        buttonPanel.add(refreshButton);
//
//        // Добавляем компоненты на панель
//        add(buttonPanel, BorderLayout.NORTH);
//        add(scrollPane, BorderLayout.CENTER);
//    }
//
//    private void loadProducts() {
//        java.util.List<Product> products = productDao.getAll();
//        productTable.setProducts(products);
//    }
//
//    private void showProductForm(Product product) {
//        ProductForm form = new ProductForm(mainFrame, product);
//        form.setVisible(true);
//    }
    @Override
    protected void showForm(BaseModel product) {
        ProductForm form = new ProductForm(mainFrame, (Product) product);
        form.setVisible(true);
    }
}