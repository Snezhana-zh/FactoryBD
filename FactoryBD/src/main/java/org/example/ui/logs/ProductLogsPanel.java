package org.example.ui.logs;

import org.example.dao.BaseDao;
import org.example.model.BaseModel;
import org.example.model.ProductLog;
import org.example.ui.BasePanel;

import javax.swing.*;

public class ProductLogsPanel extends BasePanel {


    public ProductLogsPanel(BaseDao<ProductLog> dao, JFrame mainFrame) {

        super(dao, mainFrame, new ProductLogsTable());

    }
    @Override
    protected void showForm(BaseModel el) {
        ProductLogsForm form = new ProductLogsForm(mainFrame, (ProductLog) el);
        form.setVisible(true);
    }
}