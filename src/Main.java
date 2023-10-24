import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Product {
    private String name;
    private double unitPrice;

    public Product(String name, double unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public String getName() {
        return this.name;
    }

    public double getUnitPrice() {
        return this.unitPrice;
    }
}

class LineItem {
    private Product product;
    private int quantity;

    public LineItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return this.product;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public double getTotal() {
        return this.product.getUnitPrice() * this.quantity;
    }
}

class Invoice {
    private String title;
    private ArrayList<LineItem> lineItems;
    private double totalAmount;

    public Invoice(String title) {
        this.title = title;
        this.lineItems = new ArrayList<>();
        this.totalAmount = 0.0;
    }

    public void addLineItem(LineItem item) {
        this.lineItems.add(item);
        this.totalAmount += item.getTotal();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(title).append("\n");
        for (LineItem item : lineItems) {
            Product product = item.getProduct();
            builder.append(product.getName()).append(", Quantity: ").append(item.getQuantity()).append(", Total: ").append(item.getTotal()).append("\n");
        }
        builder.append("Total Amount: ").append(totalAmount);
        return builder.toString();
    }
}

class InvoiceFrame extends JFrame {
    private JTextField productNameField, unitPriceField, quantityField;
    private JTextArea invoiceDisplayArea;
    private Invoice invoice;

    public InvoiceFrame() {
        invoice = new Invoice("Sample Invoice");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        productNameField = new JTextField(10);
        unitPriceField = new JTextField(10);
        quantityField = new JTextField(10);
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productName = productNameField.getText();
                double unitPrice = Double.parseDouble(unitPriceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                Product product = new Product(productName, unitPrice);
                LineItem lineItem = new LineItem(product, quantity);
                invoice.addLineItem(lineItem);
                invoiceDisplayArea.setText(invoice.toString());
            }
        });

        inputPanel.add(new JLabel("Product:"));
        inputPanel.add(productNameField);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(unitPriceField);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);
        inputPanel.add(addButton);

        invoiceDisplayArea = new JTextArea(10, 40);
        JScrollPane scrollPane = new JScrollPane(invoiceDisplayArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        pack();
    }
}

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InvoiceFrame frame = new InvoiceFrame();
            frame.setVisible(true);
        });
    }
}
