package edu.swarthmore.cs.cs71.shelved;


import static spark.Spark.*;

import edu.swarthmore.cs.cs71.shelved.model.HibBook;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;

public class ServerExample {

    public static void main(String[] argv) {



        SessionFactory sf = new Configuration().configure().buildSessionFactory();

        staticFiles.location("/public");


        initializeDatabase(sf);


        get("/list", new DisplayTestRoute(sf));
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

            HibBook book2 = new HibBook();
            book2.setAuthor("JK Rowling");
            book2.setGenre("Fiction");
            book2.setTitle("Harry Potter");
            book2.setPages(300);
            book2.setPublisher("Bloomsbury");

            session.getTransaction().begin();
            session.persist(book);
            session.persist(book2);

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

