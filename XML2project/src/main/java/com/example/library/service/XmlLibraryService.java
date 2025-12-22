package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.User;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class XmlLibraryService {

    private final String LIBRARY_PATH = "library_data.xml";
    private final String USERS_PATH = "users_data.xml";

    public XmlLibraryService() {
        initFile(LIBRARY_PATH, "library.xml");
        initFile(USERS_PATH, "users.xml");
    }

    private void initFile(String destPath, String resourceName) {
        File f = new File(destPath);
        if (!f.exists()) {
            try (InputStream is = new ClassPathResource(resourceName).getInputStream()) {
                Files.copy(is, f.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Document loadDoc(String path) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new File(path));
    }

    private void saveDoc(Document doc, String path) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(path));
        transformer.transform(source, result);
    }



    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try {
            Document doc = loadDoc(LIBRARY_PATH);
            NodeList nodeList = doc.getElementsByTagName("book");
            for (int i = 0; i < nodeList.getLength(); i++) {
                books.add(nodeToBook((Element) nodeList.item(i)));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return books;
    }

    public void addBook(Book book) {
        try {
            Document doc = loadDoc(LIBRARY_PATH);
            Element root = doc.getDocumentElement();

            Element newBook = doc.createElement("book");
            newBook.setAttribute("id", UUID.randomUUID().toString());

            createChild(doc, newBook, "title", book.getTitle());
            createChild(doc, newBook, "author", book.getAuthor());
            createChild(doc, newBook, "year", book.getYear());
            createChild(doc, newBook, "category", book.getCategory());
            createChild(doc, newBook, "price", String.valueOf(book.getPrice()));
            createChild(doc, newBook, "count", String.valueOf(book.getCount()));

            root.appendChild(newBook);
            saveDoc(doc, LIBRARY_PATH);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void updatePrice(String id, Double newPrice) {
        try {
            Document doc = loadDoc(LIBRARY_PATH);
            XPath xpath = XPathFactory.newInstance().newXPath();
            Node priceNode = (Node) xpath.evaluate("/library/book[@id='" + id + "']/price", doc, XPathConstants.NODE);
            if (priceNode != null) {
                priceNode.setTextContent(String.valueOf(newPrice));
                saveDoc(doc, LIBRARY_PATH);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<Book> searchBooks(String criteria, String value) {
        List<Book> books = new ArrayList<>();
        try {
            Document doc = loadDoc(LIBRARY_PATH);
            XPath xpath = XPathFactory.newInstance().newXPath();
            String expression = String.format("/library/book[contains(translate(%s, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]",
                    criteria, value.toLowerCase());

            NodeList nodes = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                books.add(nodeToBook((Element) nodes.item(i)));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return books;
    }

    public Book getBookById(String id) {
        try {
            Document doc = loadDoc(LIBRARY_PATH);
            XPath xpath = XPathFactory.newInstance().newXPath();
            Node node = (Node) xpath.evaluate("/library/book[@id='" + id + "']", doc, XPathConstants.NODE);
            if(node != null) return nodeToBook((Element) node);
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }


    public User getUserByUsername(String username) {
        try {
            Document doc = loadDoc(USERS_PATH);
            XPath xpath = XPathFactory.newInstance().newXPath();
            Node userNode = (Node) xpath.evaluate("/users/user[@username='" + username + "']", doc, XPathConstants.NODE);

            if (userNode != null) {
                Element el = (Element) userNode;
                User user = new User();
                user.setUsername(username);
                user.setPassword(el.getElementsByTagName("password").item(0).getTextContent());
                user.setRole(el.getElementsByTagName("role").item(0).getTextContent());

                NodeList books = el.getElementsByTagName("bookId");
                for(int i=0; i<books.getLength(); i++) {
                    user.getBorrowedBookIds().add(books.item(i).getTextContent());
                }
                return user;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Document doc = loadDoc(USERS_PATH);
            NodeList nodes = doc.getElementsByTagName("user");
            for(int i=0; i<nodes.getLength(); i++) {
                Element el = (Element) nodes.item(i);
                users.add(getUserByUsername(el.getAttribute("username")));
            }
        } catch (Exception e) {e.printStackTrace();}
        return users;
    }

    public synchronized void issueBook(String bookId, String username) {
        try {
            Document libDoc = loadDoc(LIBRARY_PATH);
            Document userDoc = loadDoc(USERS_PATH);
            XPath xpath = XPathFactory.newInstance().newXPath();

            Node countNode = (Node) xpath.evaluate("/library/book[@id='" + bookId + "']/count", libDoc, XPathConstants.NODE);
            int count = Integer.parseInt(countNode.getTextContent());
            if (count > 0) {
                countNode.setTextContent(String.valueOf(count - 1));
                saveDoc(libDoc, LIBRARY_PATH);

                Node userNode = (Node) xpath.evaluate("/users/user[@username='" + username + "']", userDoc, XPathConstants.NODE);
                if (userNode != null) {
                    Element userEl = (Element) userNode;
                    Node booksNode = userEl.getElementsByTagName("books").item(0);
                    if(booksNode == null) {
                        booksNode = userDoc.createElement("books");
                        userEl.appendChild(booksNode);
                    }

                    Element bookIdEl = userDoc.createElement("bookId");
                    bookIdEl.setTextContent(bookId);
                    booksNode.appendChild(bookIdEl);

                    saveDoc(userDoc, USERS_PATH);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private Book nodeToBook(Element el) {
        String id = el.getAttribute("id");
        String title = el.getElementsByTagName("title").item(0).getTextContent();
        String author = el.getElementsByTagName("author").item(0).getTextContent();
        String year = el.getElementsByTagName("year").item(0).getTextContent();
        String category = el.getElementsByTagName("category").item(0).getTextContent();
        Double price = Double.parseDouble(el.getElementsByTagName("price").item(0).getTextContent());
        Integer count = Integer.parseInt(el.getElementsByTagName("count").item(0).getTextContent());
        return new Book(id, title, author, year, category, price, count);
    }

    private void createChild(Document doc, Element parent, String tagName, String text) {
        Element el = doc.createElement(tagName);
        el.setTextContent(text);
        parent.appendChild(el);
    }
}