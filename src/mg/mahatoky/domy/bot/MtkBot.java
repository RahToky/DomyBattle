package mg.mahatoky.domy.bot;

import mg.mahatoky.domy.model.*;

/**
 * @author mtk_ext
 */
public class MtkBot extends Player {

    public MtkBot(String name) {
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
            int countHead = playableDominoes.howManyHaveEye(placedDominoes.getHead());
            int countTail = playableDominoes.howManyHaveEye(placedDominoes.getTail());
            if(countHead < countTail){
                playerResponse.setPlace(PlayerResponse.PLACE.TAIL);
                playerResponse.setDomino(playableDominoes.getHigherWith(placedDominoes.getTail()));
            }else{
                playerResponse.setPlace(PlayerResponse.PLACE.HEAD);
                playerResponse.setDomino(playableDominoes.getHigherWith(placedDominoes.getHead()));
            }
        }
        return playerResponse;
    }



}
