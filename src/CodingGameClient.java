import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.lang.String.format;
import static okhttp3.RequestBody.create;

class CodingGameClient {

    /** url of the coding game server */
//    private static final String API_URL = "https://coding-game.swat-sii.fr/api";
    private static final String API_URL = "http://localhost/api/";
//    private static final String API_URL = "http://192.168.0.2/api/";

    /** change this boolean to show json response in console */
    private static final boolean ENABLE_JSON_LOG = false;

    private static final String CREATE_GAME_URL = API_URL + "/fights";
    private static final String JOIN_GET_GAME_URL = API_URL + "/fights/%s/players/%s";
    private static final String PLAY_GAME_URL = API_URL + "/fights/%s/players/%s/actions/%s";

    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new GsonBuilder().create();
    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    static Game createGame(String gameName, boolean speedy, boolean versus) throws IOException, JSONException {

        JSONObject jsonRequest = new JSONObject().put("name",gameName).put("speedy",speedy).put("versus",versus);
        RequestBody body = create(JSON_MEDIA_TYPE, jsonRequest.toString());
        Request request = new Request.Builder().url(CREATE_GAME_URL).post(body).build();

        return callAndLogAndConvert(request, "CREATE");
    }

    static Game joinGame(String gameToken, String playerKey, String character, String name) throws JSONException, IOException {

        JSONObject jsonRequest = new JSONObject().put("character", character).put("name", name);
        RequestBody body = create(JSON_MEDIA_TYPE, jsonRequest.toString());
        Request request = new Request.Builder()
                .url(format(JOIN_GET_GAME_URL, gameToken, playerKey))
                .post(body).build();

        return callAndLogAndConvert(request, "JOIN");
    }

    static Game getGame(String gameToken, String playerKey) throws IOException, JSONException {

        Request request = new Request.Builder()
                .url(format(JOIN_GET_GAME_URL, gameToken, playerKey))
                .build();

        return callAndLogAndConvert(request, "GET");
    }

    static Game play(String gameToken, String playerKey, String actionName) throws IOException, JSONException {

        System.out.println(actionName + " !");
        Request request = new Request.Builder()
                .url(format(PLAY_GAME_URL, gameToken, playerKey, actionName))
                .post(create(JSON_MEDIA_TYPE, ""))
                .build();

        return callAndLogAndConvert(request, "PLAY");
    }

    static Game playAndWaitCoolDown(String gameToken, String playerKey, String actionName) throws Exception {

        Game game = play(gameToken, playerKey, actionName);
        waitCoolDown(game, actionName);
        return game;
    }

    private static Game callAndLogAndConvert(Request request, String requestType) throws IOException, JSONException {
        Response response = client.newCall(request).execute();
        String outputbody = response.body().string();
        if (ENABLE_JSON_LOG) {
            System.out.println("Response from " + requestType + " game request:");
            System.out.println(new JSONObject(outputbody).toString(2));
        }
        return gson.fromJson(outputbody, Game.class);
    }

    private static void waitCoolDown(Game game, String actionName) {
        if (game.getMe() != null) {
            game.getMe().getCharacter().getActions().stream()
                    .filter(a -> a.getName().equals(actionName))
                    .findFirst()
                    .ifPresent(a -> {
                        try {
                            if (a.coolDown > 0) {
                                long l = (long) Math.ceil(a.coolDown * game.getSpeed());
                                System.out.println("Waiting cool down during " + l + "ms...");
                                Thread.sleep(l);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

}
