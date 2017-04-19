package resources.charactersResources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import resources.actionResources.Action;
import resources.charactersResources.Character;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierre on 15/04/17.
 */
public class Character {

    private Integer id;

    private Integer armor;

    private String name;

    private List<Action> actions;

    public Character (int id, int armor, String name, List<Action> actions) {
        this.id = id;
        this.armor = armor;
        this.name = name;
        this.actions = actions;
    }

    public Character (JSONObject characterJSON) throws JSONException {
        this.armor = characterJSON.getInt("armor");
        this.name = characterJSON.getString("name");
        this.actions = createActions(characterJSON.getJSONArray("actions"));

    }

    private List<Action> createActions(JSONArray actionsJSON) throws JSONException {
        List<Action> actions = new ArrayList<>();

        for(int i = 0; i < actionsJSON.length(); i++) {
            actions.add(new Action((actionsJSON.getJSONObject(i))));
        }
        return actions;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArmor() {
        return armor;
    }

    public void setArmor(Integer armor) {
        this.armor = armor;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
