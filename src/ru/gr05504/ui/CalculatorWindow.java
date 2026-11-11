// ui/CalculatorWindow.java
package ru.gr05504.ui;

import ru.gr05504.domain.Calculation;
import ru.gr05504.domain.metods.ManyMethods;
import ru.gr05504.domain.metods.MethodRealised;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CalculatorWindow extends JFrame
        implements CalculationFormView, CalculationListView {

    private final JTextField formulaField = new JTextField(20);
    private final JTextField lowerField = new JTextField(10);
    private final JTextField upperField = new JTextField(10);
    private final JTextField intervalsField = new JTextField("100", 10);
    private final JComboBox<MethodRealised> methodBox = new JComboBox<>(ManyMethods.standard().toArray(new MethodRealised[0]));
    private final JButton calculateButton = new JButton("Вычислить");
    private final JButton deleteButton = new JButton("Удалить");
    private final DefaultListModel<Calculation> listModel = new DefaultListModel<>();
    private final JList<Calculation> calculationList = new JList<>(listModel);

    private CalculationFormPresenter formPresenter;
    private CalculationListPresenter listPresenter;

    private final CalculationDescriptionFormatter formatter =
            new CalculationDescriptionFormatter();

    public CalculatorWindow() {
        setTitle("Определённый интеграл");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buildLayout();
        setSize(760, 480);
        setLocationRelativeTo(null);
    }

    public void setPresenters(
            CalculationFormPresenter formPresenter,
            CalculationListPresenter listPresenter
    ) {
        this.formPresenter = formPresenter;
        this.listPresenter = listPresenter;

        this.listPresenter.start();
    }

    private void buildLayout() {
        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));
        form.add(new JLabel("Формула f(x):"));
        form.add(formulaField);
        form.add(new JLabel("Нижний предел a:"));
        form.add(lowerField);
        form.add(new JLabel("Верхний предел b:"));
        form.add(upperField);
        form.add(new JLabel("Число отрезков n:"));
        form.add(intervalsField);
        form.add(new JLabel("Метод:"));
        form.add(methodBox);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(calculateButton);
        buttons.add(deleteButton);

        JPanel top = new JPanel(new BorderLayout(5, 5));
        top.add(form, BorderLayout.CENTER);
        top.add(buttons, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(calculationList), BorderLayout.CENTER);

        calculationList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean selected,
                                                          boolean hasFocus) {
                super.getListCellRendererComponent(list, value, index, selected, hasFocus);
                setText(formatter.format((Calculation) value));
                return this;
            }
        });

        calculateButton.addActionListener(e -> {
            if (formPresenter.calculateClicked()) listPresenter.refresh();
        });
        deleteButton.addActionListener(e -> listPresenter.deleteSelected());
    }



    @Override public String getFormula()            { return formulaField.getText(); }
    @Override public String getLowerText()          { return lowerField.getText(); }
    @Override public String getUpperText()          { return upperField.getText(); }
    @Override public String getIntervalsText()      { return intervalsField.getText(); }
    @Override public MethodRealised getMethod()  { return (MethodRealised) methodBox.getSelectedItem(); }
    @Override public Calculation getSelected()      { return calculationList.getSelectedValue(); }

    @Override
    public void clearForm() {
        formulaField.setText("");
        lowerField.setText("");
        upperField.setText("");
    }

    @Override
    public void showCalculations(List<Calculation> calculations) {
        listModel.clear();
        for (Calculation c : calculations) listModel.addElement(c);
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}