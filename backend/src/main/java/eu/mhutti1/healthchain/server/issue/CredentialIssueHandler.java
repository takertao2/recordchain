package eu.mhutti1.healthchain.server.issue;

import com.sun.net.httpserver.HttpExchange;
import eu.mhutti1.healthchain.server.RequestUtils;
import eu.mhutti1.healthchain.server.events.EventConsumer;
import eu.mhutti1.healthchain.server.session.SessionInvalidException;
import eu.mhutti1.healthchain.server.session.SessionManager;
import eu.mhutti1.healthchain.storage.EventNode;
import eu.mhutti1.healthchain.storage.EventStorage;
import eu.mhutti1.healthchain.utils.Crypto;
import org.hyperledger.indy.sdk.IndyException;
import org.hyperledger.indy.sdk.anoncreds.Anoncreds;
import org.hyperledger.indy.sdk.anoncreds.AnoncredsResults;
import org.hyperledger.indy.sdk.wallet.Wallet;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by jedraz on 30/10/2018.
 */
public class CredentialIssueHandler extends EventConsumer {

  @Override
  public boolean handleEventAction(HttpExchange httpExchange) throws IOException {

    httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

    String query = httpExchange.getRequestURI().getQuery();
    Map<String, String> params = RequestUtils.queryToMap(query);

    String token = params.get("token");
    String eventId = params.get("event_id");

    Wallet issuerWallet = null;
    String issuerDid = null;

    String response = RequestUtils.messageOK();
    int responseCode = RequestUtils.statusOK();

    try {
      issuerWallet = SessionManager.getSessionCredentials(token).getWallet();
      issuerDid = SessionManager.getSessionCredentials(token).getDid();
    } catch (SessionInvalidException e) {
      response = "Invalid session token";
      responseCode = RequestUtils.statusSessionExpired();
    } catch (IndyException e) {
      e.printStackTrace();
    }

    if (issuerWallet == null) {
      httpExchange.sendResponseHeaders(responseCode, response.length());
      OutputStream os = httpExchange.getResponseBody();
      os.write(response.getBytes());
      os.close();
      return false;
    }

    JSONObject payload = EventStorage.getEvent(issuerDid, eventId).getPayload();
    String credentialRequestJSON = payload.getString("credentialRequestJSON");
    String credDefJSON = payload.getString("credDefJSON");
    String credOfferJSON = payload.getString("credOfferJSON");
    String credentialRequestMetadataJSON = payload.getString("credentialRequestMetadataJSON");
    String credValuesJSON = payload.getString("credValuesJSON");
    String proverDid = EventStorage.getEvent(issuerDid, eventId).getFromDid();

    String credValues = Crypto.encodeCredValues(credValuesJSON);

    // Issuer create Credential
    AnoncredsResults.IssuerCreateCredentialResult createCredentialResult = null;
    try {
      createCredentialResult = Anoncreds.issuerCreateCredential(issuerWallet, credOfferJSON, credentialRequestJSON,
              credValues, null, - 1).get();
    } catch (InterruptedException e) {
      e.printStackTrace();
      response = RequestUtils.messageInternalServerError();
      responseCode = RequestUtils.statuSInternalServerError();
    } catch (ExecutionException e) {
      e.printStackTrace();
      response = RequestUtils.messageInternalServerError();
      responseCode = RequestUtils.statuSInternalServerError();
    } catch (IndyException e) {
      e.printStackTrace();
      response = RequestUtils.messageUnauthorized();
      responseCode = RequestUtils.statusUnauthorized();
    }

    String credential = createCredentialResult.getCredentialJson();

    JSONObject newPayload = new JSONObject()
            .put("credential", credential)
            .put("credDefJSON", credDefJSON)
            .put("credentialRequestMetadataJSON", credentialRequestMetadataJSON);

    EventStorage.store(proverDid, new EventNode("Your health record is ready to be stored in your wallet", issuerDid, newPayload, "credential_store", null));

    httpExchange.sendResponseHeaders(responseCode, response.length());
    OutputStream os = httpExchange.getResponseBody();
    os.write(response.getBytes());
    os.close();
    return true;
  }
}
