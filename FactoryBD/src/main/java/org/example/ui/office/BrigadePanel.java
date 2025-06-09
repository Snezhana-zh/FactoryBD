package org.example.ui.office;

import org.example.dao.BaseDao;
import org.example.model.BaseModel;
import org.example.model.Brigade;
import org.example.ui.BasePanel;

import javax.swing.*;

public class BrigadePanel extends BasePanel {


    public BrigadePanel(BaseDao<Brigade> dao, JFrame mainFrame) {

        super(dao, mainFrame, new BrigadeTable());

    }
    @Override
    protected void showForm(BaseModel el) {
        BrigadeForm form = new BrigadeForm(mainFrame, (Brigade) el);
        form.setVisible(true);
    }
}