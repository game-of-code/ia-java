import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class IA {

    // data from main arguments
    private static Mode mode;
    private static String gameName;
    private static String gameToken;
    private static boolean versusPlayer = false;
    private static Character character;
    private static String playerName;

    /** change this boolean to accelerate the game */
    private static final boolean speedy = false;

    enum Character {WARRIOR,PALADIN,DRUID,SORCERER,TROLL,ELF}

    private static final String playerKey = UUID.randomUUID().toString();
    private enum Mode {CREATE,JOIN}

    public static void main(String[] args) {

        // check mode argument
        if (args.length == 0 || (!Mode.CREATE.name().equals(args[0]) && !Mode.JOIN.name().equals(args[0])) ) {
            System.out.println("1st argument is required, and must be " + Mode.CREATE + " or " + Mode.JOIN);
            System.exit(0);
        }
        mode = Mode.valueOf(args[0]);

        // check game name/token argument, and versus argument in case of creation mode
        switch (mode) {
            case CREATE:
                if (args.length < 2) {
                    System.out.println("2nd argument is required, and must be the game name");
                    System.exit(0);
                }
                gameName = args[1];

                if (args.length > 4 && args[4].toLowerCase().equals("true")) {
                    versusPlayer = true;
                }
                break;
            case JOIN:
                if (args.length < 2) {
                    System.out.println("2nd argument is required, and must be the game token");
                    System.exit(0);
                }
                gameToken = args[1];
                break;
        }

        // check character argument
        if (args.length < 3) {
            System.out.println("3rd argument is required, and must be your character type");
            System.exit(0);
        }
        if (Stream.of(Character.values()).map(Character::name).noneMatch(c -> c.equals(args[2]))) {
            System.out.println("3rd argument must be " +
                    Stream.of(Character.values()).map(Character::name).collect(Collectors.joining(" or ")));
            System.exit(0);
        }
        character = Character.valueOf(args[2]);

        // check player name argument
        if (args.length < 4) {
            System.out.println("4th argument is required and must be your player name");
            System.exit(0);
        }
        playerName = args[3];

        System.out.println("All yours arguments are OK.");
        System.out.println();

        try {

            Game game = null;
            switch (mode) {
                case CREATE:
                    System.out.println("Creating the game...");
                    game = CodingGameClient.createGame(gameName, speedy, versusPlayer);
                    gameToken = game.getToken();
                case JOIN:
                    System.out.println("Joining the game...");
                    game = CodingGameClient.joinGame(gameToken, playerKey, character.name(), playerName);
                    break;
            }

            if (game.getStatus() == Game.GameStatus.WAITING) {
                System.out.println("Waiting the foe to join the game...");
                while (game.getStatus() == Game.GameStatus.WAITING) {
                    Thread.sleep(500);
                    game = CodingGameClient.getGame(gameToken, playerKey);
                }
            }

            game = CodingGameClient.getGame(game.getToken(), playerKey);
            System.out.println("Waiting count down during " + game.getCountDown() + "ms...");
            Thread.sleep(game.getCountDown());

            
            Random random = new Random();
            
            while(game.getStatus() != Game.GameStatus.FINISHED) {
                // ******************************************
                // IMPLEMENT YOUR AI HERE
                // ******************************************
    
                // The following AI just randomly alternate with the 4 possible actions
                switch (random.nextInt(4)) {
                    case 0:
                        CodingGameClient.playAndWaitCoolDown(game.getToken(), playerKey, "HIT");
                        break;
                    case 1:
                        CodingGameClient.playAndWaitCoolDown(game.getToken(), playerKey, "THRUST");
                        break;
                    case 2:
                        CodingGameClient.playAndWaitCoolDown(game.getToken(), playerKey, "HEAL");
                        break;
                    case 3:
                        CodingGameClient.playAndWaitCoolDown(game.getToken(), playerKey, "SHIELD");
                        System.out.println("Waiting for shield duration... (" + (long) game.getSpeed()/2 + "ms)");
                        Thread.sleep((long) game.getSpeed()/2);
                        break;
                }

                game = CodingGameClient.getGame(game.getToken(), playerKey);
                System.out.println("Me: " + game.getMe().getHealthPoints() + "pv, foe: " + game.getFoe().getHealthPoints() + "pv.");
            }

            if (game.getMe().getHealthPoints() > 0) {
                System.out.println("You WIN !");
            } else {
                System.out.println("You lose.");
            }
        } catch (Exception e) {
            System.out.println("Something goes wrong...");
            e.printStackTrace();
            System.exit(0);
        }
    }

}
