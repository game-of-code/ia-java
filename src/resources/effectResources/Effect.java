package resources.effectResources;

/**
 * Created by pierre on 15/04/17.
 */
public class Effect {

    private Integer id;

    private String effect;

    private Integer intensity;

    public Effect(Integer id, String effect, Integer intensity) {
        this.id = id;
        this.effect = effect;
        this.intensity = intensity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public Integer getIntensity() {
        return intensity;
    }

    public void setIntensity(Integer intensity) {
        this.intensity = intensity;
    }
}
