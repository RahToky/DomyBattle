package mg.mahatoky.domy.model;

import java.util.HashSet;
import java.util.Set;

/**
 * @author mahatoky
 */
public class Dominoes extends HashSet<Domino> {


    public Dominoes() {
    }

    public Dominoes(int initialSize) {
        super(initialSize);
    }


    @Override
    public boolean contains(Object o) {
        if (!(o instanceof Domino)) {
            return false;
        }
        for (Domino domino : this) {
            if (domino.equals(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return true if one face match with one or many dominoes in the collection
     *
     * @param domino
     * @return is match
     */
    public boolean match(Domino domino) {
        if (domino == null)
            return false;
        for (Domino d : this) {
            if (d.match(domino)) {
                return true;
            }
        }
        return false;
    }

    public static Dominoes from(Set<Domino> dominoes) {
        if (dominoes == null)
            return null;
        Dominoes result = new Dominoes(dominoes.size());
        result.addAll(dominoes);
        return result;
    }

    /**
     * How many dominoes have eye
     *
     * @param eye
     * @return
     */
    public int howManyHaveEye(int eye) {
        int res = 0;
        for (Domino d : this) {
            if (d.have(eye))
                res++;
        }
        return res;
    }

    public int countEyes() {
        int res = 0;
        for (Domino d : this) {
            res += d.countEyes();
        }
        return res;
    }

    /**
     * Return one domino with eye
     *
     * @param eye
     * @return
     */
    public Domino getAnyWith(int eye) {
        for (Domino d : this) {
            if (d.have(eye)) {
                return d;
            }
        }
        return null;
    }

    /**
     * Return all dominoes who have eye
     *
     * @param eye
     * @return
     */
    public Dominoes getAllWith(int eye) {
        if (howManyHaveEye(eye) == 0)
            return null;
        Dominoes dominoes = new Dominoes();
        for (Domino d : this) {
            if (d.have(eye)) {
                dominoes.add(d);
            }
        }
        return dominoes;
    }

    /**
     * Get one, order is not sure
     *
     * @return
     */
    public Domino get() {
        for (Domino d : this) {
            return d;
        }
        return null;
    }

    /**
     * Return the domino who have eye and contains the higher eyes
     *
     * @param eye
     * @return
     */
    public Domino getHigherWith(int eye) {
        Domino biggerDomino = null;
        for (Domino d : this) {
            if (d.have(eye) && (biggerDomino == null || biggerDomino.countEyes() < d.countEyes())) {
                biggerDomino = d;
            }
        }
        return biggerDomino;
    }

    /**
     * Return the domino who have eye and contains the lower eyes
     *
     * @param eye
     * @return
     */
    public Domino getLowerWith(int eye) {
        Domino biggerDomino = null;
        for (Domino d : this) {
            if (d.have(eye) && (biggerDomino == null || biggerDomino.countEyes() > d.countEyes())) {
                biggerDomino = d;
            }
        }
        return biggerDomino;
    }

    /**
     * Get the domino with countEyes is the higher
     *
     * @return
     */
    public Domino getHigher() {
        Domino biggerDomino = null;
        for (Domino d : this) {
            if (biggerDomino == null) {
                biggerDomino = d;
            } else if (biggerDomino.countEyes() < d.countEyes()) {
                biggerDomino = d;
            }
        }
        return biggerDomino;
    }

    /**
     * Get the domino with countEyes is the lower
     *
     * @return
     */
    public Domino getLower() {
        Domino lowerDomino = null;
        for (Domino d : this) {
            if (lowerDomino == null) {
                lowerDomino = d;
            } else if (lowerDomino.countEyes() > d.countEyes()) {
                lowerDomino = d;
            }
        }
        return lowerDomino;
    }
}
