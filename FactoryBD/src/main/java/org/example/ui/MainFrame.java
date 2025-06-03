package org.example.ui;

import org.example.dao.BaseDao;
import org.example.dao.ProductDao;
import javax.swing.*;
import java.awt.*;

import org.example.model.Product;
import org.example.model.ProductCategory;
import org.example.model.ProductModel;
import org.example.ui.products.*;

public class MainFrame extends JFrame {
    // private final ProductDao productDao;
    private final BaseDao<Product> productDao;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    public MainFrame() {
        this.productDao = new BaseDao<Product>(Product.class);
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Management System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Создаем главное меню
        JMenuBar menuBar = new JMenuBar();

        // Меню "Модули"
        JMenu modulesMenu = new JMenu("Modules");
        JMenuItem productsItem = new JMenuItem("Products");
        JMenuItem staffItem = new JMenuItem("Staff");
        JMenuItem ordersItem = new JMenuItem("Orders");
        JMenuItem productModelsItem = new JMenuItem("Product Models");
        JMenuItem productCatItem = new JMenuItem("Product Category");

        // Добавляем меню в менюбар
        menuBar.add(modulesMenu);
        modulesMenu.add(productCatItem);
        modulesMenu.add(productModelsItem);
        modulesMenu.add(productsItem);
        modulesMenu.add(staffItem);
        modulesMenu.add(ordersItem);

        setJMenuBar(menuBar);

        // Создаем панель с карточками для переключения между формами
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Создаем разные формы
        StaffPanel staffPanel = new StaffPanel();
        OrdersPanel ordersPanel = new OrdersPanel();

        // Добавляем формы на contentPanel
        contentPanel.add(new ProductsPanel(productDao, MainFrame.this), "Products");
        contentPanel.add(staffPanel, "Staff");
        contentPanel.add(ordersPanel, "Orders");
        contentPanel.add(new ProductModelPanel(new BaseDao<>(ProductModel.class), this), "ProductModels");
        contentPanel.add(new ProductCategoryPanel(new BaseDao<>(ProductCategory.class), this), "ProductCategory");

        // Обработчики для меню
        productsItem.addActionListener(e -> cardLayout.show(contentPanel, "Products"));
        staffItem.addActionListener(e -> cardLayout.show(contentPanel, "Staff"));
        ordersItem.addActionListener(e -> cardLayout.show(contentPanel, "Orders"));
        productModelsItem.addActionListener(e ->
                cardLayout.show(contentPanel, "ProductModels"));
        productCatItem.addActionListener(e ->
                cardLayout.show(contentPanel, "ProductCategory"));

        // Добавляем компоненты на форму
        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        // Показываем панель продуктов по умолчанию
        cardLayout.show(contentPanel, "Products");
    }

    // Внутренний класс для панели продуктов


    // Заглушки для других панелей
    private class StaffPanel extends JPanel {
        public StaffPanel() {
            setLayout(new BorderLayout());
            add(new JLabel("Staff Management Panel (to be implemented)", SwingConstants.CENTER), BorderLayout.CENTER);

            // Здесь можно добавить кнопки и таблицу для работы с персоналом
        }
    }

    private class OrdersPanel extends JPanel {
        public OrdersPanel() {
            setLayout(new BorderLayout());
            add(new JLabel("Orders Management Panel (to be implemented)", SwingConstants.CENTER), BorderLayout.CENTER);

            // Здесь можно добавить кнопки и таблицу для работы с заказами
        }
    }
}

/*
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
}*/
