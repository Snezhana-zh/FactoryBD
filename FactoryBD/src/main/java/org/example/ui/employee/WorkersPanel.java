package org.example.ui.employee;

import org.example.dao.BaseDao;
import org.example.model.BaseModel;
import org.example.model.Worker;
import org.example.ui.BasePanel;

import javax.swing.*;

public class WorkersPanel extends BasePanel {


    public WorkersPanel(BaseDao<Worker> dao, JFrame mainFrame) {

        super(dao, mainFrame, new WorkersTable());

    }
    @Override
    protected void showForm(BaseModel el) {
        WorkersForm form = new WorkersForm(mainFrame, (Worker) el);
        form.setVisible(true);
    }
}