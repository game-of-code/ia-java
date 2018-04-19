import java.util.List;

class Game {

    /** Token of the game */
    private String token;

    /** Is the game started? */
    private GameStatus status;

    /** Speed of the game (number of milliseconds in a time unit) */
    private Integer speed;

    /** If status is {@link GameStatus#PLAYING}, indicates the time (in ms) until the game starts */
    private Long countDown;

    /** Data of the current player */
    private Player me;

    /** Data of the foe of the current player */
    private Player foe;

    enum GameStatus {WAITING,PLAYING,FINISHED}

    class Player {

        /** Heal points remaining */
        Long healthPoints;

        /** Armor remaining */
        Long armor;

        /** Character chosen by the player */
        CharacterCharacteristic character;

        public Long getHealthPoints() {
            return healthPoints;
        }

        public Long getArmor() {
            return armor;
        }

        public CharacterCharacteristic getCharacter() {
            return character;
        }
    }

    class CharacterCharacteristic {

        /** Armor of the character. All characters start with the same amount of heal points, but they have different armors. */
        Long armor;

        /** Name of the character */
        String name;

        /** List of the available actions for this character */
        List<Action> actions;

        public Long getArmor() {
            return armor;
        }

        public String getName() {
            return name;
        }

        public List<Action> getActions() {
            return actions;
        }
    }

    class Action {

        /** Name of the action (to be used by the players to play) */
        String name;

        /** Description of the action (to be read by players to understand the action: players can't see real effects) */
        String description;

        /** Time before the player can't play again after this action (in time units, can't be null) */
        Double coolDown;

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public Double getCoolDown() {
            return coolDown;
        }
    }


    public String getToken() {
        return token;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Integer getSpeed() {
        return speed;
    }

    public Long getCountDown() {
        return countDown;
    }

    public Player getMe() {
        return me;
    }

    public Player getFoe() {
        return foe;
    }

}
