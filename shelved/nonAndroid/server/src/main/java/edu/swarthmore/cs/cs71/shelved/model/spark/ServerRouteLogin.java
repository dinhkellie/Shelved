package edu.swarthmore.cs.cs71.shelved.model.spark;

import edu.swarthmore.cs.cs71.shelved.model.server.HibUserService;
import edu.swarthmore.cs.cs71.shelved.network.InvalidLoginUserResponse;
import edu.swarthmore.cs.cs71.shelved.network.ValidLoginUserResponse;
import edu.swarthmore.cs.cs71.shelved.network.ResponseMessage;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;

public class ServerRouteLogin extends ServerRoute{

    public ServerRouteLogin(SessionFactory sf) {
        super(sf);
    }

    @Override
    protected ResponseMessage execute(Request request, Response response) {

        HibUserService service = new HibUserService();

        int result = service.checkUserValid(getSf(),
                request.queryParams("email"),
                request.queryParams("password")

        );
        if (result < 0){
            System.out.println("-1 if no username found. -2 if incorrect password. -3 if ArrayStoreException. We returned: "+String.valueOf(result));
            return new InvalidLoginUserResponse("Invalid user login");
        }
        return new ValidLoginUserResponse(result);
    }
}
