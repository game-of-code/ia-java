package resources.RestResources;

import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Singleton contenant toutes les requêtes REST nécessaires au jeu
 */
public class RestRequests {
    private static RestRequests instance = null;
    private OkHttpClient client = new OkHttpClient();
    private MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String url = "https://coding-game.swat-sii.fr/api/fights/";

    protected RestRequests() {
    }

    public static RestRequests getInstance() {
        if(instance == null){
            instance = new RestRequests();
        }
        return instance;
    }

    public JSONObject createGame(String gameName, boolean speedy, boolean versus) throws IOException, JSONException {
        // crée le body de la requete POST
        JSONObject values = new JSONObject();
        values.put("name",gameName);
        values.put("speedy",speedy);
        values.put("versus",versus);
        RequestBody body = RequestBody.create(JSON, values.toString());
        // envoie la requête
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        // traite la réponse
        Response response = client.newCall(request).execute();
        String responseStr = response.body().string();
        JSONObject res = new JSONObject(responseStr);
        System.out.println(res);
        return res;
    }

    public JSONObject joinGame(String gameToken, String playerKey, String character, String name) throws JSONException, IOException {
        // crée le body de la requêtes
        JSONObject values = new JSONObject();
        values.put("character", character);
        values.put("name", name);
        RequestBody body = RequestBody.create(JSON, values.toString());
        // envoie la requête
        Request request = new Request.Builder()
                .url(url+gameToken+"/players/"+playerKey)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        // traite la réponse
        String responseStr = response.body().string();
        System.out.println(responseStr);
        JSONObject res = new JSONObject(responseStr);
        return res;
    }

    public JSONObject getGame(String gameToken, String playerKey) throws IOException, JSONException {
        // envoie la requête
        Request request = new Request.Builder()
                .url(url+gameToken+"/players/"+playerKey)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        // traite la réponse
        String responseStr = response.body().string();
        System.out.println(responseStr);
        JSONObject res = new JSONObject(responseStr);
        return res;
    }

    public JSONObject play(String gameToken, String playerKey, String actionName) throws IOException, JSONException {
        //envoie la requête
        RequestBody body = RequestBody.create(JSON, "");
        Request request = new Request.Builder()
                .url(url+gameToken+"/players/"+playerKey+"/actions/"+actionName)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        // traite la réponse
        String responseStr = response.body().string();
        System.out.println(responseStr);
        return new JSONObject(responseStr);
    }
}
