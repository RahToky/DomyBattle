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
     * 
     * @param playerDominoes it is your dominoes
     * @param playableDominoes it is part of your dominoes that you can choose
     * @param placedDominoes all dominoes already played in table
     * @return null if no suitable domino or choose domino with position
     */
    public abstract PlayerResponse play(final Dominoes playerDominoes, final Dominoes playableDominoes, final PlacedDominoes placedDominoes);


    public boolean matchHead(PlacedDominoes placedDominoes, Domino domino){
        return placedDominoes.isEmpty() || placedDominoes.getHead() == domino.getFace1() || placedDominoes.getHead() == domino.getFace2();
    }
    public boolean matchTail(PlacedDominoes placedDominoes, Domino domino){
        return placedDominoes.getDominoes().isEmpty() || placedDominoes.getTail() == domino.getFace1() || placedDominoes.getTail() == domino.getFace2();
    }

}
