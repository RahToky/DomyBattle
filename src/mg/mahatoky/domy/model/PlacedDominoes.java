package mg.mahatoky.domy.model;

import mg.mahatoky.domy.exception.AmbiguousFacePositionException;
import mg.mahatoky.domy.exception.NotValidDomino;

import java.util.LinkedList;

/**
 * Represent all placed dominoes by {@link Player}
 * With some useful methods
 * @author mtk_ext
 */
public class PlacedDominoes {

    private final LinkedList<Domino> dominoes = new LinkedList<>();
    private Integer head;
    private Integer tail;

    public void clear(){
        dominoes.clear();
        head = null;
        tail = null;
    }

    /**
     * Add domino to the right place
     * @param domino
     * @throws NotValidDomino when domino don't match nor with head nor with tail
     * @throws AmbiguousFacePositionException when we can place both domino on head or tail
     */
    public void add(Domino domino) throws NotValidDomino, AmbiguousFacePositionException {
        if(dominoes.isEmpty()){
            head = domino.getFace1();
            tail = domino.getFace2();
            dominoes.add(domino);
        }else{
            if(isNotMatch(domino)){
                throw new NotValidDomino();
            }

            if(head.intValue() == tail.intValue()){
                dominoes.addLast(domino);
                tail = tail == domino.getFace1() ? domino.getFace2(): domino.getFace1();
            }else {
                throw new AmbiguousFacePositionException();
            }
        }
    }

    /**
     * Add domino on head
     * If it can't be placed on head, then try to place it in tail otherwise throw exception
     * @param domino
     * @throws NotValidDomino when domino don't match nor with head nor with tail
     * @throws AmbiguousFacePositionException when we can place both domino on head or tail
     */
    public void addToHead(Domino domino) throws NotValidDomino, AmbiguousFacePositionException {
        if(!match(domino)){
            throw new NotValidDomino();
        }
        if(dominoes.isEmpty()){
            this.add(domino);
            return;
        }
        if(head == domino.getFace1()){
            head = domino.getFace2();
        }else if(head == domino.getFace2()){
            head = domino.getFace1();
        }else{
            addToTail(domino);
            return;
        }
        dominoes.addFirst(domino);
    }

    /**
     * Add domino on tail
     * If it can't be placed on tail, then try to place it in head otherwise throw exception
     * @param domino
     * @throws NotValidDomino when domino don't match nor with head nor with tail
     * @throws AmbiguousFacePositionException when we can place both domino on head or tail
     */
    public void addToTail(Domino domino) throws NotValidDomino, AmbiguousFacePositionException {
        if(!match(domino)){
            throw new NotValidDomino();
        }
        if(dominoes.isEmpty()){
            this.add(domino);
            return;
        }
        if(tail == domino.getFace1()){
            tail = domino.getFace2();
        }else if(tail == domino.getFace2()){
            tail = domino.getFace1();
        }else{
            addToHead(domino);
            return;
        }
        dominoes.addLast(domino);
    }

    /**
     * Test if domino can be placed on head or tail
     * @param domino
     * @return true if matched otherwise false
     */
    public boolean match(Domino domino){
        return matchHead(domino) || matchTail(domino);
    }

    public boolean isNotMatch(Domino domino){
        return !match(domino);
    }

    public LinkedList<Domino> getDominoes() {
        return dominoes;
    }

    public Integer getHead() {
        return head;
    }

    public Integer getTail() {
        return tail;
    }

    public boolean isEmpty(){
        return dominoes.isEmpty();
    }

    /**
     * Test if domino can be placed on head
     * @param domino
     * @return
     */
    public boolean matchHead(Domino domino){
        if(dominoes.isEmpty()){
            return true;
        }
        return domino !=null && domino.have(head);
    }

    /**
     * Test if domino can be placed on tail
     * @param domino
     * @return
     */
    public boolean matchTail(Domino domino){
        if(dominoes.isEmpty()){
            return true;
        }
        return domino !=null && domino.have(tail);
    }

    /**
     * Test if {@link #dominoes} contains the given domino
     * @param domino
     * @return
     */
    public boolean contains(Domino domino){
        for(Domino d: dominoes){
            if(d.equals(domino)){
                return true;
            }
        }
        return false;
    }
}
