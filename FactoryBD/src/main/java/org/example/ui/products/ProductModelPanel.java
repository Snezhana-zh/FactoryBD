/*
package org.example.ui.products;

import org.example.dao.ProductModelDao;
import org.example.model.ProductModel;
import org.example.ui.products.ProductModelTable;

import javax.swing.*;
import java.awt.*;

public class ProductModelPanel extends JPanel {
    private ProductModelTable modelTable;
    private final ProductModelDao modelDao;
    private JFrame mainFrame;

    public ProductModelPanel(ProductModelDao modelDao, JFrame mainFrame) {
        this.modelDao = modelDao;
        this.mainFrame = mainFrame;
        initializeUI();
        loadModels();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Панель с кнопками
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Model");
        JButton editButton = new JButton("Edit Model");
        JButton deleteButton = new JButton("Delete Model");

        addButton.addActionListener(e -> showModelForm(null));
        editButton.addActionListener(e -> editSelectedModel());
        deleteButton.addActionListener(e -> deleteSelectedModel());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Таблица моделей
        modelTable = new ProductModelTable();
        JScrollPane scrollPane = new JScrollPane(modelTable);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadModels() {
        SwingUtilities.invokeLater(() -> {
            modelTable.setModels(modelDao.getAllModels());
            modelTable.revalidate();
            modelTable.repaint();
        });
    }

    // Остальные методы аналогично ProductsPanel
}*/
package org.example.ui.products;

import org.example.dao.BaseDao;
import org.example.model.BaseModel;
import org.example.model.ProductModel;
import org.example.ui.BasePanel;

import javax.swing.*;

public class ProductModelPanel extends BasePanel {
    private ProductModelTable table;
    //private final BaseDao<ProductModel> dao;
    private JFrame mainFrame;

    public ProductModelPanel(BaseDao<ProductModel> dao, JFrame mainFrame) {
        super(dao, mainFrame, new ProductModelTable());
//        this.dao = dao;
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
//            ProductModel selected = table.getSelected();
//            if (selected != null) {
//                showProductForm(selected);
//            } else {
//                JOptionPane.showMessageDialog(mainFrame, "Please select a element to edit");
//            }
//        });
//
//        deleteButton.addActionListener(e -> {
//            ProductModel selected = table.getSelected();
//            if (selected != null) {
//                int confirm = JOptionPane.showConfirmDialog(
//                        mainFrame,
//                        "Are you sure you want to delete this element?",
//                        "Confirm Delete",
//                        JOptionPane.YES_NO_OPTION);
//
//                if (confirm == JOptionPane.YES_OPTION) {
//                    dao.delete(selected.getId());
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
//        // Создаем таблицу
//        table = new ProductModelTable();
//        JScrollPane scrollPane = new JScrollPane(table);
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
//        java.util.List<ProductModel> elements = dao.getAll();
//        table.set(elements);
//    }

//    private void showProductForm(ProductModel element) {
//        ProductModelForm form = new ProductModelForm(mainFrame, element);
//        form.setVisible(true);
//    }
    @Override
    protected void showForm(BaseModel el) {
        ProductModelForm form = new ProductModelForm(mainFrame, (ProductModel) el);
        form.setVisible(true);
    }
}