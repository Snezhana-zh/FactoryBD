package org.example.ui.employee;

import org.example.dao.BaseDao;
import org.example.model.BaseModel;
import org.example.model.Engineer;
import org.example.ui.BasePanel;

import javax.swing.*;

public class EngineerPanel extends BasePanel {


    public EngineerPanel(BaseDao<Engineer> dao, JFrame mainFrame) {

        super(dao, mainFrame, new EngineerTable());

    }
    @Override
    protected void showForm(BaseModel el) {
        EngineerForm form = new EngineerForm(mainFrame, (Engineer) el);
        form.setVisible(true);
    }
}