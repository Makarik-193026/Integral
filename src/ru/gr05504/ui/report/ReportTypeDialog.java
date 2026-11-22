package ru.gr05504.ui.report;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;

public class ReportTypeDialog extends JDialog {

    private static final String SHORT_REPORT = "Краткий отчёт";
    private static final String DETAILED_REPORT = "Подробный отчёт";

    private String selectedReport;

    private final JLabel imageLabel = new JLabel();

    public ReportTypeDialog(Window owner) {
        super(owner, "Выбор типа отчёта", Dialog.ModalityType.APPLICATION_MODAL);

        JComboBox<String> reportComboBox = new JComboBox<>(
                new String[]{SHORT_REPORT, DETAILED_REPORT}
        );

        reportComboBox.setSelectedItem(SHORT_REPORT);
        updateImage(SHORT_REPORT);

        reportComboBox.addActionListener(e ->
                updateImage((String) reportComboBox.getSelectedItem())
        );

        JButton confirmButton = new JButton("Выбрать");
        JButton cancelButton = new JButton("Отмена");

        confirmButton.addActionListener(e -> {
            selectedReport = (String) reportComboBox.getSelectedItem();
            dispose();
        });

        cancelButton.addActionListener(e -> {
            selectedReport = null;
            dispose();
        });

        JLabel titleLabel = new JLabel("Какой отчёт сформировать?");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(350, 280));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(cancelButton);
        buttonPanel.add(confirmButton);

        JPanel contentPanel = new JPanel(new BorderLayout(10, 15));
        contentPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        contentPanel.add(titleLabel, BorderLayout.NORTH);
        contentPanel.add(imageLabel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.add(reportComboBox, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(contentPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(450, 430);
        setLocationRelativeTo(owner);
    }

    private void updateImage(String reportType) {
        String imagePath;

        if (SHORT_REPORT.equals(reportType)) {
            imagePath = "resources/short-report.jpg";
        } else {
            imagePath = "resources/detailed-report.jpg";
        }

        File imageFile = new File(imagePath);

        if (!imageFile.exists()) {
            imageLabel.setIcon(null);
            imageLabel.setText("Картинка не найдена: " + imagePath);
            return;
        }

        ImageIcon originalIcon = new ImageIcon(imageFile.getAbsolutePath());

        Image scaledImage = originalIcon.getImage().getScaledInstance(
                350,
                280,
                Image.SCALE_SMOOTH
        );

        imageLabel.setText("");
        imageLabel.setIcon(new ImageIcon(scaledImage));
    }

    public String showDialog() {
        setVisible(true);
        return selectedReport;
    }
}
