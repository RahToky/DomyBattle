package mg.mahatoky.domy.model;

import java.util.List;
import java.util.Set;

/**
 * @author mtk_ext
 */
public abstract class Player {

    protected String name;

    public Player(String name){
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Contains the player's tactics
     * @param playerDominoes it is your dominoes
     * @param playableDominoes it is part of your dominoes that you can choose
     * @param placedDominoes all dominoes already played in table
     * @return null if no suitable domino or choose domino with position
     */
    public abstract PlayerResponse play(final Dominoes playerDominoes, final Dominoes playableDominoes, final PlacedDominoes placedDominoes);

}
