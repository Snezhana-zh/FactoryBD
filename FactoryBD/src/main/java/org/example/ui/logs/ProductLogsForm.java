package org.example.ui.logs;

import org.example.dao.BaseDao;
import org.example.model.Product;
import org.example.model.ProductLog;
import org.example.model.TestLab;
import org.example.model.Workshop;
import org.example.ui.BaseForm;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class ProductLogsForm extends BaseForm {
    private ProductLog model;
    private final JComboBox<Product> productCombo;
    private final JComboBox<Workshop> workshopCombo;
    private final JComboBox<TestLab> testLabCombo;
    private final JSpinner startWorkSpinner;
    private final JSpinner endWorkSpinner;

    public ProductLogsForm(JFrame parent, ProductLog model_) {
        super(parent, "ProductLogs Form", true);
        this.model = model_ != null ? model_ : new ProductLog();

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        setSize(400, 300);

        BaseDao<Product> productDao = new BaseDao<>(Product.class);
        BaseDao<Workshop> workshopDao = new BaseDao<>(Workshop.class);
        BaseDao<TestLab> testLabDao = new BaseDao<>(TestLab.class);

        // Product selection
        panel.add(new JLabel("Product:"));
        productCombo = new JComboBox<>();
        for (Product pr : productDao.getAll()) {
            productCombo.addItem(pr);
        }
        productCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Product) {
                    setText(((Product) value).getId().toString());
                } else {
                    setText("None");
                }
                return this;
            }
        });
        panel.add(productCombo);

        // Workshop selection
        panel.add(new JLabel("Workshop:"));
        workshopCombo = new JComboBox<>();
        workshopCombo.addItem(null);
        for (Workshop ws : workshopDao.getAll()) {
            workshopCombo.addItem(ws);
        }
        workshopCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Workshop) {
                    setText(((Workshop) value).getName());
                } else {
                    setText("None");
                }
                return this;
            }
        });
        panel.add(workshopCombo);

        // Test Lab selection
        panel.add(new JLabel("Test Lab:"));
        testLabCombo = new JComboBox<>();
        testLabCombo.addItem(null);
        for (TestLab tl : testLabDao.getAll()) {
            testLabCombo.addItem(tl);
        }
        testLabCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof TestLab) {
                    setText(((TestLab) value).getTitle());
                } else {
                    setText("None");
                }
                return this;
            }
        });
        panel.add(testLabCombo);

        // Start Work time
        SpinnerDateModel model_spinner2 = new SpinnerDateModel() {
            private Date value = null;

            @Override
            public Object getValue() {
                return value;
            }

            @Override
            public void setValue(Object value) {
                this.value = (Date) value;
            }
        };
        panel.add(new JLabel("Start Work:"));
        startWorkSpinner = new JSpinner(model_spinner2);
        startWorkSpinner.setEditor(new JSpinner.DateEditor(startWorkSpinner, "yyyy-MM-dd HH:mm"));

        if (model.getStartWork() != null) {
            model_spinner2.setValue(Date.from(
                    model.getStartWork().atZone(ZoneId.systemDefault()).toInstant()));
        } else {
            LocalDateTime ldt = LocalDateTime.now();
            Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
            model_spinner2.setValue(date);
        }

        panel.add(startWorkSpinner);

        // End Work time
        panel.add(new JLabel("End Work:"));
        SpinnerDateModel model_spinner = new SpinnerDateModel() {
            private Date value = null;

            @Override
            public Object getValue() {
                return value;
            }

            @Override
            public void setValue(Object value) {
                this.value = (Date) value;
            }
        };

        if (model.getEndWork() != null) {
            model_spinner.setValue(Date.from(
                    model.getEndWork().atZone(ZoneId.systemDefault()).toInstant()));
        } else {
            model_spinner.setValue(null);
        }
        endWorkSpinner = new JSpinner(model_spinner);

        endWorkSpinner.setEditor(new JSpinner.DateEditor(endWorkSpinner, "yyyy-MM-dd HH:mm"));
        panel.add(endWorkSpinner);


        // Buttons
        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> saveLog());
        panel.add(saveBtn);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> dispose());
        panel.add(cancelBtn);

        loadData();
        add(panel);
        pack();
        setLocationRelativeTo(parent);
    }
    private void loadData() {
        if (model.getId() != null) {
            productCombo.setSelectedItem(model.getProduct());
            workshopCombo.setSelectedItem(model.getWorkshop());
            testLabCombo.setSelectedItem(model.getTestlab());
        }
    }

    private void saveLog() {
        model.setProduct((Product) productCombo.getSelectedItem());
        model.setWorkshop((Workshop) workshopCombo.getSelectedItem());
        model.setTestlab((TestLab) testLabCombo.getSelectedItem());

        if (endWorkSpinner.getValue() != null) {
            Date endDate = (Date) endWorkSpinner.getValue();
            model.setEndWork(endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        else {
            model.setEndWork(null);
        }

        if (startWorkSpinner.getValue() != null) {
            Date date = (Date) startWorkSpinner.getValue();
            model.setStartWork(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        else {
            model.setStartWork(null);
        }

        BaseDao<ProductLog> dao = new BaseDao<>(ProductLog.class);

        if (model.getId() == null) {
            dao.save(model);
        } else {
            dao.update(model);
        }

        dispose();
    }
}