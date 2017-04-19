package main;

import org.json.JSONException;
import org.json.JSONObject;
import resources.RestResources.RestRequests;
import resources.enums.Actions;
import resources.enums.Characters;
import resources.gameResources.Game;

import java.io.IOException;

/**
 * Created by pierre on 03/04/17.
 */
public class main {
    public static void main(String[] args){
        RestRequests requests = RestRequests.getInstance();
        // envoie la requête POST pour créer une nouvelle partie
        try {
            // create session on server en store the resulting Game
            Game game = Game.createGame("game", false, false);
            System.out.println("TOKEN : "+game.getToken());
            // TODO est-ce que "me" est créé/stocké?
            // join the game
            System.out.println("CREATION DE LA PARTIE. Name : Pierrot ; Classe : "+Characters.PALADIN.toString());
            System.out.println("Waiting for game to start...");
            game.joinGameWithCountdown(game.getToken(), game.getPlayerKey(), "pierrot", Characters.PALADIN.toString());
            System.out.println("Game started !");
            // make an action
            Thread.sleep(game.getCountDown());
            while(true){
                game.makeAction(Actions.HIT);
                for(int i = 0; i < 10; i++ ){
                    game.getGame();
                    System.out.println("PDV me : "+game.getMe().getHealthPoints());
                    Thread.sleep(100);
                }
            }



            /**
            JSONObject gameJSON = requests.createGame("myGame1", false,false);
            System.out.println("------Create game answer-----");
            String gameToken = gameJSON.getString("token");
            Game game = new Game(gameJSON);

            JSONObject joinAnswer = requests.joinGame(gameToken, "playerKey", "warrior", "name");
            System.out.println("---------Join answer---------");
            System.out.println(joinAnswer);

            Thread.sleep(10000);
            JSONObject getJSON = requests.getGame(game.getToken(), "playerKey");
            System.out.println("---------get action---------");
            System.out.println(getJSON);

            JSONObject playAnswer = requests.play(game.getToken(), "playerKey", "HIT");
            System.out.println("---------play action---------");
            System.out.println(playAnswer);
            */
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } /**catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

}
