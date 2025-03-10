import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Font;

public class InvoiceFrame extends JFrame {
    private Invoice invoice = new Invoice();
    private JTextArea addressTextArea;

    public InvoiceFrame() {
        setTitle("Invoice");
        setLayout(new BorderLayout());
        setSize(800, 600);

        // Title Panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Invoice");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel refreshJPanel = new JPanel();
        JButton refreshButton = new JButton("Refresh");
        refreshJPanel.add(refreshButton);
        add(refreshJPanel, BorderLayout.EAST);


        // Address Panel
        JPanel addressPanel = new JPanel();
        addressPanel.setLayout(new BorderLayout());
        JLabel addressLabel = new JLabel("Enter Address:");
        addressTextArea = new JTextArea(2, 10);
        addressPanel.add(addressLabel, BorderLayout.NORTH);
        addressPanel.add(new JScrollPane(addressTextArea), BorderLayout.CENTER);
        add(addressPanel, BorderLayout.WEST);

        // Invoice Panel
        JPanel invoicePanel = new JPanel();
        invoicePanel.setLayout(new BorderLayout());
        JTextArea invoiceTextArea = new JTextArea(10, 30);
        invoiceTextArea.setEditable(false);
        invoicePanel.add(new JScrollPane(invoiceTextArea), BorderLayout.CENTER);
        add(invoicePanel, BorderLayout.CENTER);

        // Add Item Panel
        JPanel addItemPanel = new JPanel();
        JButton addItemButton = new JButton("Add Item");
        addItemPanel.add(addItemButton);

        // Delete Item Panel
        JPanel deleteItemPanel = new JPanel();
        JButton deleteItemButton = new JButton("Delete Item");
        deleteItemPanel.add(deleteItemButton);

        //South Panel
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        southPanel.add(addItemPanel, BorderLayout.NORTH);
        southPanel.add(deleteItemPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> updateInvoiceTextArea(invoiceTextArea));

        // Add action listener to the add item button
        addItemButton.addActionListener(e -> {
            // Prompt user for product name
            String productName = JOptionPane.showInputDialog(this, "Enter product name:");
            if (productName == null || productName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Product name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            // Prompt user for product price
            String productPriceStr = JOptionPane.showInputDialog(this, "Enter product price:");
            double productPrice;
            try {
                productPrice = Double.parseDouble(productPriceStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid price. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            // Prompt user for quantity
            String quantityStr = JOptionPane.showInputDialog(this, "Enter quantity:");
            int quantity;
            try {
                quantity = Integer.parseInt(quantityStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            // Create and add the new line item
            Product product = new Product(productPrice, productName);
            LineItem item = new LineItem(product, quantity);
            invoice.addLineItem(item);
            updateInvoiceTextArea(invoiceTextArea);
        });

        // Add action listener to the delete item button
        deleteItemButton.addActionListener(e -> {
            String selectedText = invoiceTextArea.getSelectedText();
            if (selectedText != null) {
                String[] parts = selectedText.split("\t");
                if (parts.length >= 4) {
                    String productName = parts[0];
                    invoice.removeLineItem(productName);
                    updateInvoiceTextArea(invoiceTextArea);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid selection. Please select a full line item.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No item selected. Please select a line item to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void updateInvoiceTextArea(JTextArea invoiceTextArea) {
        StringBuilder invoiceText = new StringBuilder();

        invoiceText.append("INVOICE\n\n");

        invoiceText.append(addressTextArea.getText() + "\n\n");
        invoiceText.append("-----------------------------------\n");

        // Add headers
        invoiceText.append("Item\tQty\tPrice\tTotal\n\n");
        
        ArrayList<LineItem> lineItems = invoice.getLineItems();
        for (LineItem item : lineItems) {
            invoiceText.append(item.getProductName())
                    .append("\t")
                    .append("$" + item.getAmount())
                    .append("\t")
                    .append("$" + item.getProductCost())
                    .append("\t")
                    .append("$" + item.computeCost())
                    .append("\n");
        }
        invoiceText.append("-----------------------------------\n");
        invoiceText.append("\nAmount Due: ").append("$" + invoice.computeTotal());
        invoiceTextArea.setText(invoiceText.toString());
    }
}