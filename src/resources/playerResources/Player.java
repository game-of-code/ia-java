package resources.playerResources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import resources.actionResources.Action;
import resources.charactersResources.Character;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by pierre on 18/04/17.
 */
public class Player {

    Integer healthPoints;
    Integer armor;
    Boolean isBehindShield;
    ArrayList<Action> history;
    Character character;


    public Player(Integer healthPoints, Integer armor, Boolean isBehindShield, ArrayList<Action> history, Character character) {
        this.healthPoints = healthPoints;
        this.armor = armor;
        this.isBehindShield = isBehindShield;
        this.history = history;
        this.character = character;
    }

    // TODO générer l'historique une fois le type connu
    public Player(JSONObject json) throws JSONException {

        healthPoints = json.getInt("healthPoints");
        armor = json.getInt("armor");
        isBehindShield = null;
        if(json.has("isBehindShield")) {
            isBehindShield = json.getBoolean("isBehindShield");
        }
        history = new ArrayList<>();
        character = new Character(json.getJSONObject("character"));
    }

    public void updateFromJSON(JSONObject json) throws JSONException {
        if(json.has("healthPoints")){this.healthPoints = json.getInt("healthPoints");}
        if(json.has("armor")){this.armor = json.getInt("armor");}
        if(json.has("isBehindShield")){this.isBehindShield = json.getBoolean("isBehindShield");}
        if(json.has("history")){updateHistory(json.getJSONArray("history"));}
        // TODO update history
    }

    private void updateHistory(JSONArray json) throws JSONException {
        for(int i = history.size(); i < json.length(); i++){
            history.add(new Action(json.getJSONObject(i)));
        }
    }
    public Integer getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(Integer healthPoints) {
        this.healthPoints = healthPoints;
    }

    public Integer getArmor() {
        return armor;
    }

    public void setArmor(Integer armor) {
        this.armor = armor;
    }

    public Boolean getBehindShield() {
        return isBehindShield;
    }

    public void setBehindShield(Boolean behindShield) {
        isBehindShield = behindShield;
    }

    public ArrayList<Action> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<Action> history) {
        this.history = history;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

}
