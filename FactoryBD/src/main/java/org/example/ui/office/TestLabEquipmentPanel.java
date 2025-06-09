package org.example.ui.office;

import org.example.dao.BaseDao;
import org.example.model.BaseModel;
import org.example.model.TestLabEquipment;
import org.example.ui.BasePanel;

import javax.swing.*;

public class TestLabEquipmentPanel extends BasePanel {


    public TestLabEquipmentPanel(BaseDao<TestLabEquipment> dao, JFrame mainFrame) {

        super(dao, mainFrame, new TestLabEquipmentTable());

    }
    @Override
    protected void showForm(BaseModel el) {
        TestLabEquipmentForm form = new TestLabEquipmentForm(mainFrame, (TestLabEquipment) el);
        form.setVisible(true);
    }
}