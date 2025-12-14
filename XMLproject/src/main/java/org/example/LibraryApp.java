package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class Book {
    String id;
    String title;
    String author;
    String year;
    double price;
    String category;
    int totalCount;
    int availableCount;

    public Book(String id, String title, String author, String year, double price, String category, int total, int avail) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.price = price;
        this.category = category;
        this.totalCount = total;
        this.availableCount = avail;
    }
}


public class LibraryApp extends JFrame {
    private static final String XML_FILE = "library.xml";
    private static final String XSD_FILE = "library.xsd";

    private List<Book> books = new ArrayList<>();
    private JTable table;
    private DefaultTableModel tableModel;
    private Document doc;

    public LibraryApp() {
        super("Библиотечная система");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        initUI();
        loadAndValidateXML();
        refreshTable(books);
    }

    private void initUI() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton btnAdd = new JButton("Добавить книгу");
        JButton btnSearch = new JButton("Поиск");
        JButton btnReset = new JButton("Сброс поиска");
        JButton btnUpdatePrice = new JButton("Переоценка");
        JButton btnCheckout = new JButton("Выдать книгу");

        topPanel.add(btnAdd);
        topPanel.add(btnSearch);
        topPanel.add(btnReset);
        topPanel.add(btnUpdatePrice);
        topPanel.add(btnCheckout);

        add(topPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Название", "Автор", "Год", "Цена", "Категория", "Всего", "В наличии"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowSorter(new TableRowSorter<>(tableModel));
        add(new JScrollPane(table), BorderLayout.CENTER);


        btnAdd.addActionListener(e -> showAddBookDialog());
        btnSearch.addActionListener(e -> showSearchDialog());
        btnReset.addActionListener(e -> refreshTable(books));
        btnUpdatePrice.addActionListener(e -> updatePrice());
        btnCheckout.addActionListener(e -> checkoutBook());
    }



    private void loadAndValidateXML() {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(XSD_FILE));

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setSchema(schema);
            dbFactory.setNamespaceAware(true);

            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            dBuilder.setErrorHandler(new org.xml.sax.helpers.DefaultHandler() {
                @Override
                public void error(org.xml.sax.SAXParseException e) throws SAXException {
                    throw e;
                }
            });

            doc = dBuilder.parse(new File(XML_FILE));
            doc.getDocumentElement().normalize();

            books.clear();
            NodeList nList = doc.getElementsByTagName("book");

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String id = element.getAttribute("id");
                    int total = Integer.parseInt(element.getAttribute("totalCount"));
                    int avail = Integer.parseInt(element.getAttribute("availableCount"));

                    String title = getTagValue("title", element);
                    String author = getTagValue("author", element);
                    String year = getTagValue("year", element);
                    double price = Double.parseDouble(getTagValue("price", element));
                    String category = getTagValue("category", element);

                    books.add(new Book(id, title, author, year, price, category, total, avail));
                }
            }
            JOptionPane.showMessageDialog(this, "XML успешно загружен и проверен по схеме!", "Успех", JOptionPane.INFORMATION_MESSAGE);

        } catch (SAXException e) {
            JOptionPane.showMessageDialog(this, "Ошибка валидации XML: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ошибка загрузки файла: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveXML() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(XML_FILE));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ошибка сохранения XML.", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void refreshTable(List<Book> bookList) {
        tableModel.setRowCount(0);
        for (Book b : bookList) {
            tableModel.addRow(new Object[]{
                    b.id, b.title, b.author, b.year, b.price, b.category, b.totalCount, b.availableCount
            });
        }
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

    private void showAddBookDialog() {
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField yearField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField catField = new JTextField();
        JTextField totalField = new JTextField();

        Object[] message = {
                "Название:", titleField,
                "Автор:", authorField,
                "Год:", yearField,
                "Цена:", priceField,
                "Категория:", catField,
                "Всего экземпляров:", totalField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Добавить книгу", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int maxId = books.stream().mapToInt(b -> Integer.parseInt(b.id)).max().orElse(0);
                String newId = String.valueOf(maxId + 1);

                Element newBook = doc.createElement("book");
                newBook.setAttribute("id", newId);
                newBook.setAttribute("totalCount", totalField.getText());
                newBook.setAttribute("availableCount", totalField.getText()); // Изначально все доступны

                appendChild(newBook, "title", titleField.getText());
                appendChild(newBook, "author", authorField.getText());
                appendChild(newBook, "year", yearField.getText());
                appendChild(newBook, "price", priceField.getText());
                appendChild(newBook, "category", catField.getText());

                doc.getDocumentElement().appendChild(newBook);
                saveXML();

                loadAndValidateXML();
                refreshTable(books);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ошибка ввода данных.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void appendChild(Element parent, String tagName, String text) {
        Element el = doc.createElement(tagName);
        el.appendChild(doc.createTextNode(text));
        parent.appendChild(el);
    }

    private void showSearchDialog() {
        String[] criteria = {"Автор", "Год", "Категория"};
        JComboBox<String> cbCriteria = new JComboBox<>(criteria);
        JTextField txtSearch = new JTextField();

        Object[] msg = {
                "Искать по:", cbCriteria,
                "Значение:", txtSearch
        };

        int opt = JOptionPane.showConfirmDialog(this, msg, "Поиск", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            String val = txtSearch.getText().toLowerCase();
            List<Book> filtered = new ArrayList<>();
            int type = cbCriteria.getSelectedIndex();

            for (Book b : books) {
                boolean match = false;
                if (type == 0 && b.author.toLowerCase().contains(val)) match = true;
                if (type == 1 && b.year.equals(val)) match = true;
                if (type == 2 && b.category.toLowerCase().contains(val)) match = true;

                if (match) filtered.add(b);
            }
            refreshTable(filtered);
        }
    }

    private void updatePrice() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите книгу для переоценки.");
            return;
        }

        String id = (String) table.getValueAt(selectedRow, 0);
        String currentPrice = table.getValueAt(selectedRow, 4).toString();

        String newPriceStr = JOptionPane.showInputDialog(this, "Текущая цена: " + currentPrice + "\nВведите новую цену:");
        if (newPriceStr != null) {
            try {
                double newPrice = Double.parseDouble(newPriceStr);

                NodeList nList = doc.getElementsByTagName("book");
                for (int i = 0; i < nList.getLength(); i++) {
                    Element el = (Element) nList.item(i);
                    if (el.getAttribute("id").equals(id)) {
                        el.getElementsByTagName("price").item(0).setTextContent(String.valueOf(newPrice));
                        break;
                    }
                }
                saveXML();
                loadAndValidateXML();
                refreshTable(books);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Некорректная цена.");
            }
        }
    }

    private void checkoutBook() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите книгу для выдачи.");
            return;
        }

        String id = (String) table.getValueAt(selectedRow, 0);

        NodeList nList = doc.getElementsByTagName("book");
        for (int i = 0; i < nList.getLength(); i++) {
            Element el = (Element) nList.item(i);
            if (el.getAttribute("id").equals(id)) {
                int avail = Integer.parseInt(el.getAttribute("availableCount"));
                if (avail > 0) {
                    el.setAttribute("availableCount", String.valueOf(avail - 1));
                    saveXML();
                    loadAndValidateXML();
                    refreshTable(books);
                    JOptionPane.showMessageDialog(this, "Книга выдана успешно.");
                } else {
                    JOptionPane.showMessageDialog(this, "Нет доступных экземпляров!", "Ошибка", JOptionPane.WARNING_MESSAGE);
                }
                return;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryApp().setVisible(true));
    }
}
