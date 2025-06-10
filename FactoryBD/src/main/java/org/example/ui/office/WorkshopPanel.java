package org.example.ui.office;

import org.example.dao.BaseDao;
import org.example.model.BaseModel;
import org.example.model.Workshop;
import org.example.ui.BasePanel;

import javax.swing.*;

public class WorkshopPanel extends BasePanel {


    public WorkshopPanel(BaseDao<Workshop> dao, JFrame mainFrame) {

        super(dao, mainFrame, new WorkshopTable());

    }
    @Override
    protected void showForm(BaseModel el) {
        WorkshopForm form = new WorkshopForm(mainFrame, (Workshop) el);
        form.setVisible(true);
    }
}