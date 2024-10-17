package view;

import javax.swing.*;

import controller.ReportController;
import model.Order;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ManagerReportView extends JPanel {
    private static final long serialVersionUID = -8774575343983437915L;
	private ReportController reportController;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextArea reportArea;

    public ManagerReportView(ReportController reportController) {
        this.reportController = reportController;
        setLayout(new BorderLayout());

        // Top panel for date selection
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Start Date (YYYY-MM-DD):"));
        startDateField = new JTextField(10);
        topPanel.add(startDateField);

        topPanel.add(new JLabel("End Date (YYYY-MM-DD):"));
        endDateField = new JTextField(10);
        topPanel.add(endDateField);

        JButton fetchReportButton = new JButton("Fetch Report");
        topPanel.add(fetchReportButton);
        add(topPanel, BorderLayout.NORTH);

        // Text area to display the report
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        add(new JScrollPane(reportArea), BorderLayout.CENTER);

        // Button Action to fetch and display reports
        fetchReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchAndDisplayReports();
            }
        });
    }

    // Fetch and display orders between two dates
    private void fetchAndDisplayReports() {
        String startDate = startDateField.getText();
        String endDate = endDateField.getText();
        try {
            List<Order> orders = reportController.getOrdersBetweenDates(startDate, endDate);
            StringBuilder reportText = new StringBuilder();
            for (Order order : orders) {
                reportText.append("Order ID: ").append(order.getOrderID())
                    .append(", Guest Name: ").append(order.getGuestName())
                    .append(", Status: ").append(order.getOrderStatus())
                    .append(", Date: ").append(order.getOrderDate())
                    .append("\n");
            }
            reportArea.setText(reportText.toString());
        } catch (Exception e) {
            reportArea.setText("Error fetching report: " + e.getMessage());
        }
    }
}
