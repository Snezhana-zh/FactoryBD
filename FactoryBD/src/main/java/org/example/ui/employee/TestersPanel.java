package org.example.ui.employee;

import org.example.dao.BaseDao;
import org.example.model.BaseModel;
import org.example.model.Tester;
import org.example.ui.BasePanel;

import javax.swing.*;

public class TestersPanel extends BasePanel {


    public TestersPanel(BaseDao<Tester> dao, JFrame mainFrame) {

        super(dao, mainFrame, new TestersTable());

    }
    @Override
    protected void showForm(BaseModel el) {
        TestersForm form = new TestersForm(mainFrame, (Tester) el);
        form.setVisible(true);
    }
}