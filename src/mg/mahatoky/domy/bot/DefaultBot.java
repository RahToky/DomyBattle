package mg.mahatoky.domy.bot;

import mg.mahatoky.domy.model.Dominoes;
import mg.mahatoky.domy.model.PlacedDominoes;
import mg.mahatoky.domy.model.Player;
import mg.mahatoky.domy.model.PlayerResponse;

/**
 * @author mahatoky
 */
public class DefaultBot extends Player {

    public DefaultBot(String name) {
        super(name);
    }

    @Override
    public PlayerResponse play(Dominoes playerDominoes, Dominoes playableDominoes, PlacedDominoes placedDominoes) {
        PlayerResponse playerResponse = new PlayerResponse();
        if (playableDominoes.size() == 1) {
            playerResponse.setPlace(placedDominoes.matchHead(playableDominoes.get()) ? PlayerResponse.PLACE.HEAD : PlayerResponse.PLACE.TAIL);
            playerResponse.setDomino(playableDominoes.get());
        }else if(placedDominoes.isEmpty()){
            playerResponse.setPlace(PlayerResponse.PLACE.HEAD);
            playerResponse.setDomino(playableDominoes.getHigher());
        }else{
            playerResponse.setPlace(placedDominoes.matchHead(playableDominoes.get()) ? PlayerResponse.PLACE.TAIL : PlayerResponse.PLACE.HEAD);
            playerResponse.setDomino(playableDominoes.get());
        }
        return playerResponse;
    }



}
