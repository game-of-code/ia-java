package resources.actionResources;

import org.json.JSONObject;
import resources.effectResources.Effect;

import java.util.List;

/**
 * Created by pierre on 15/04/17.
 */
public class Action {

    private Integer id;

    private String name;

    private String localName;

    private List<Effect> effects;

    private Boolean dropShield;

    private Integer coolDown;

    public Action(Integer id, String name, String localName, List<Effect> effects, Boolean dropShield, Integer coolDown) {
        this.id = id;
        this.name = name;
        this.localName = localName;
        this.effects = effects;
        this.dropShield = dropShield;
        this.coolDown = coolDown;
    }

    public Action(JSONObject actionJSON) {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public void setEffects(List<Effect> effects) {
        this.effects = effects;
    }

    public Boolean getDropShield() {
        return dropShield;
    }

    public void setDropShield(Boolean dropShield) {
        this.dropShield = dropShield;
    }

    public Integer getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(Integer coolDown) {
        this.coolDown = coolDown;
    }
}
