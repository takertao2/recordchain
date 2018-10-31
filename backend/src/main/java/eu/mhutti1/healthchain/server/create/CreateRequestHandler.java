package eu.mhutti1.healthchain.server.create;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import eu.mhutti1.healthchain.server.RequestUtils;
import eu.mhutti1.healthchain.storage.EventNode;
import eu.mhutti1.healthchain.storage.LocalStorage;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

/**
 * Created by jedraz on 31/10/2018.
 */
public class CreateRequestHandler implements HttpHandler {

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {

    httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

    String query = httpExchange.getRequestURI().getQuery();
    Map<String, String> params = RequestUtils.queryToMap(query);

    String password = params.get("password");
    String username = params.get("username");
    String issuerDid = params.get("issuer_did");


    JSONObject payload = new JSONObject()
            .put("password", password)
            .put("username", username);

    // later on register for email notofication

    LocalStorage.store(issuerDid, new EventNode("", null, payload));
  }
}
