package org.example.ui.office;

import org.example.dao.BaseDao;
import org.example.model.BaseModel;
import org.example.model.TestLab;
import org.example.ui.BasePanel;

import javax.swing.*;

public class TestLabPanel extends BasePanel {


    public TestLabPanel(BaseDao<TestLab> dao, JFrame mainFrame) {

        super(dao, mainFrame, new TestLabTable());

    }
    @Override
    protected void showForm(BaseModel el) {
        TestLabForm form = new TestLabForm(mainFrame, (TestLab) el);
        form.setVisible(true);
    }
}