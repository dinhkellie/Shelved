package edu.swarthmore.cs.cs71.shelved.spark;

import edu.swarthmore.cs.cs71.shelved.network.FailureResponse;
import edu.swarthmore.cs.cs71.shelved.network.ResponseMessage;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.persistence.EntityManager;


public abstract class ServerRoute implements Route{
    private final SessionFactory sf;

    public ServerRoute(SessionFactory sf) {
        this.sf = sf;
    }
    protected abstract ResponseMessage execute(Request request, Response response);
    @Override
    public ResponseMessage handle(Request request, Response response) throws Exception {
        EntityManager session = sf.createEntityManager();
        PersistenceUtils.ENTITY_MANAGER.set(session);
        session.getTransaction().begin();
        try {
            // Do stuffs
            System.out.println("Start execution");
            ResponseMessage o = execute(request, response);
            session.getTransaction().commit();
            System.out.println("Finish execution");
            return o;
        } catch (Exception e) {
            session.getTransaction().rollback();
            // new object to represent failure messages
            return new FailureResponse();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    public SessionFactory getSf() {
        return sf;
    }
}




