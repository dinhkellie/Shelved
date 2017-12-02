package edu.swarthmore.cs.cs71.shelved.spark;


import static spark.Spark.*;

import edu.swarthmore.cs.cs71.shelved.model.server.*;
import edu.swarthmore.cs.cs71.shelved.network.serialization.GsonUtils;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;

public class ServerExample {

    public static void main(String[] argv) {


        SessionFactory sf = new Configuration().configure().buildSessionFactory();

        staticFiles.location("/public");


        initializeDatabase(sf);
        post("/signup", new ServerRouteSignup(sf), GsonUtils.makeMessageGson()::toJson);
        get("/list", new DisplayTestRoute(sf));
        post("/login", new ServerRouteLogin(sf), GsonUtils.makeMessageGson()::toJson);
    }


    private static void initializeDatabase(SessionFactory sf) {
        EntityManager session = sf.createEntityManager();
        try {
            HibBook book = new HibBook();
            book.setAuthor("Haruki Murakami");
            book.setGenre("Fiction");
            book.setTitle("Norweigian Wood");
            book.setPages(296);
            book.setPublisher("Vintage International");
            HibShelvedBook shelvedBook = new HibShelvedBook();
            shelvedBook.setBook(book);
            shelvedBook.setBookMark(59);
            shelvedBook.setForLend(true);
            HibUser user1  = new HibUser();
            user1.setEmail("lan@swat.edu");
            user1.setName("Lan");
            user1.setSalt();
            user1.setPassword("aaaa123");
            HibBook book2 = new HibBook();
            book2.setAuthor("JK Rowling");
            book2.setGenre("Fiction");
            book2.setTitle("Harry Potter");
            book2.setPages(300);
            book2.setPublisher("Bloomsbury");

//            HibBookShelf bookShelf = new HibBookShelf();
//            bookShelf.configureBookShelf(5);
//            bookShelf.getRowShelf(1).addBook(shelvedBook, 1);

            session.getTransaction().begin();
            session.persist(book);
            session.persist(book2);
            //session.persist(shelvedBook);
            session.persist(user1);
            //session.persist(bookShelf);

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }


}