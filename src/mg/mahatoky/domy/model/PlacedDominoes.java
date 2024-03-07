package mg.mahatoky.domy.model;

import mg.mahatoky.domy.exception.AmbigiousFacePositionException;
import mg.mahatoky.domy.exception.NotValidDomino;

import java.util.LinkedList;

/**
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

    public void add(Domino domino) throws NotValidDomino, AmbigiousFacePositionException {
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
                throw new AmbigiousFacePositionException();
            }
        }
    }

    public void addToHead(Domino domino) throws NotValidDomino, AmbigiousFacePositionException {
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

    public void addToTail(Domino domino) throws NotValidDomino, AmbigiousFacePositionException {
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

    public boolean matchHead(Domino domino){
        if(dominoes.isEmpty()){
            return true;
        }
        return domino !=null && domino.have(head);
    }
    public boolean matchTail(Domino domino){
        if(dominoes.isEmpty()){
            return true;
        }
        return domino !=null && domino.have(tail);
    }

    public boolean contains(Domino domino){
        for(Domino d: dominoes){
            if(d.equals(domino)){
                return true;
            }
        }
        return false;
    }
}
