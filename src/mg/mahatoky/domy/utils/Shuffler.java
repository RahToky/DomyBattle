package mg.mahatoky.domy.utils;

import mg.mahatoky.domy.model.Domino;
import mg.mahatoky.domy.model.Dominoes;

import java.util.*;

/**
 * @author mtk_ext
 */
public abstract class Shuffler {

    /**
     * Generate 28 dominoes in order from 0:0 0:1 ... to 5:5 5:6 6:6
     *
     * @return ordered dominoes
     */
    public static Dominoes generateDominoes() {
        Dominoes dominoes = new Dominoes(28);
        for (int face1 = 0; face1 < 7; face1++) {
            for (int face2 = face1; face2 < 7; face2++) {
                dominoes.add(new Domino(face1, face2));
            }
        }
        return dominoes;
    }

    /**
     * @param dominoes set of 28 dominoes
     * @return dominoes split in Map<Integer,Set<Domino>>. key are 1,2,3,4
     * @throws Exception if dominoes length!=28
     */
    public static Map<Integer, Dominoes> mixIn4Parts(Dominoes dominoes) throws Exception {
        if (dominoes.size() != 28) {
            throw new Exception("Dominoes size must be 28");
        }
        List<Domino> remainingDominoes = new ArrayList<>(dominoes);
        Map<Integer, Dominoes> sharedDominoes = new HashMap<>(4);
        sharedDominoes.put(1, new Dominoes());
        sharedDominoes.put(2, new Dominoes());
        sharedDominoes.put(3, new Dominoes());
        sharedDominoes.put(4, new Dominoes());

        Random random = new Random();
        while (!remainingDominoes.isEmpty()) {
            for (int i = 1; i < 5; i++) {
                int randomId = remainingDominoes.size() > 1 ? random.nextInt(remainingDominoes.size() - 1) : 0;
                sharedDominoes.get(i).add(remainingDominoes.get(randomId));
                remainingDominoes.remove(randomId);
            }
        }
        return sharedDominoes;
    }

}
