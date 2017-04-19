package resources.gameResources;

import org.json.JSONException;
import org.json.JSONObject;
import resources.RestResources.RestRequests;
import resources.enums.Actions;
import resources.enums.GameStatus;
import resources.playerResources.Player;

import java.io.IOException;

/**
 * Created by pierre on 13/04/17.
 */
public class Game {

    private String playerKey;

    private String token;

    private GameStatus status;

    private Integer speed;

    private Integer countDown;

    private Player me;

    private Player foe;

    public Game(){}

    public Game(String playerKey, String token, GameStatus status, Integer speed, Integer countDown, Player me, Player foe) {
        this.playerKey = playerKey;
        this.token = token;
        this.status = status;
        this.speed = speed;
        this.countDown = countDown;
        this.me = me;
        this.foe = foe;
    }

    public Game(JSONObject json) throws JSONException {
        token = json.getString("token");
        status = GameStatus.valueOf(json.getString("status"));
        speed = json.getInt("speed");
        countDown = null;
        me = null;
        foe = null;

        if(!json.get("countDown").equals(null)) {
            countDown = json.getInt("countDown");
        }
        if(!json.get("me").equals(null)) {
            me = new Player(json.getJSONObject("me"));
        }
        if(!json.get("foe").equals(null)) {
            foe = new Player(json.getJSONObject("foe"));
        }
    }

    /**
     * Send the request to create a new game
     * @param name name of Game
     * @param speedy Enable speedy mode
     * @param versus Set versus mode or IA mode
     * @return the game created
     */
    public static Game createGame(String name, boolean speedy, boolean versus) throws IOException, JSONException {
        JSONObject json = RestRequests.getInstance().createGame(name, speedy, versus);
        return new Game(json);
    }

    /**
     * Join an existing game and return data when the game is started
     * @param gameToken Token of the game
     * @param playerKey Private personal token
     * @param playerName Player display name
     * @param character Character type (WARRIOR, PALADIN, DRUID, SORCERER)
     * @return the Game object
     * @throws IOException
     * @throws JSONException
     */
    public Game joinGameWithCountdown(String gameToken, String playerKey, String playerName, String character) throws IOException, JSONException {
        // join game and update all values of the Game
        long itCheck = System.currentTimeMillis();
        this.joinGame(gameToken, playerKey, playerName, character);
        // wait for the game to start
        while(!status.equals(GameStatus.PLAYING)) {
            // update game status every speed*0.8
            if(System.currentTimeMillis()-itCheck >= (this.getSpeed()*0.8)){
                updateGameFromJSON(RestRequests.getInstance().getGame(this.getToken(), this.getPlayerKey()));
                itCheck = System.currentTimeMillis();
            }
        }
        return this;
    }

    public void joinGame(String gameToken, String playerKey, String playerName, String character) throws IOException, JSONException {
        this.playerKey = playerKey;
        updateGameFromJSON(RestRequests.getInstance().joinGame(gameToken, playerKey, character, playerName));
    }

    public void updateGameFromJSON(JSONObject json) throws JSONException {
        // pas nécessaire puisque ça devrait pas bouger
        if(json.has("token")){token = json.getString("token");}
        if(json.has("speed")){speed = json.getInt("speed");}
        if(json.has("status")){status = GameStatus.valueOf(json.getString("status"));}
        if(json.has("speed")){speed = json.getInt("speed");}
        countDown = null;
        me = null;
        foe = null;

        if(!json.get("countDown").equals(null)) {
            countDown = json.getInt("countDown");
        }
        if(!json.get("me").equals(null)) {
            me = new Player(json.getJSONObject("me"));
        }
        if(!json.get("foe").equals(null)) {
            foe = new Player(json.getJSONObject("foe"));
        }
    }

    public void makeAction(Actions hit) throws IOException, JSONException {
        JSONObject json = RestRequests.getInstance().play(this.getToken(),this.getPlayerKey(),hit.toString());
        this.updateAfterAction(json);
    }

    // TODO trouver comment récupérer l'action grâce à son nom
    public void makeActionWIthCoolDown(Actions hit) throws IOException, JSONException {
        JSONObject json = RestRequests.getInstance().play(this.getToken(),this.getPlayerKey(),hit.toString());
        this.updateAfterAction(json);
    }

    public void updateAfterAction(JSONObject json) throws JSONException {
        if(json.has("status")){this.status = GameStatus.valueOf(json.getString("status"));}
        if(json.has("me")){me.updateFromJSON(json.getJSONObject("me"));}
        if(json.has("foe")){me.updateFromJSON(json.getJSONObject("foe"));}
    }

    public void getGame() throws IOException, JSONException {
        updateAfterAction(RestRequests.getInstance().getGame(getToken(), getPlayerKey()));
    }

    public String getPlayerKey() {
        return playerKey;
    }

    public void setPlayerKey(String playerKey) {
        this.playerKey = playerKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getCountDown() {
        return countDown;
    }

    public void setCountDown(Integer countDown) {
        this.countDown = countDown;
    }

    public Player getMe() {
        return me;
    }

    public void setMe(Player me) {
        this.me = me;
    }

    public Player getFoe() {
        return foe;
    }

    public void setFoe(Player foe) {
        this.foe = foe;
    }

}
