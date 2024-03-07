package mg.mahatoky.domy.view.console;

import mg.mahatoky.domy.model.Domino;
import mg.mahatoky.domy.model.PlacedDominoes;

/**
 * @author mahatoky
 */
public abstract class ConsolePrinter {

    public static void display(PlacedDominoes placedDominoes){
        if(placedDominoes.getDominoes().isEmpty()){
            return;
        }
        int head = placedDominoes.getHead();
        for(Domino domino: placedDominoes.getDominoes()){
            if(domino.getFace1() == head){
                head = domino.getFace2();
                System.out.print("["+domino.getFace1()+":"+domino.getFace2()+"]");
            }else{
                head = domino.getFace1();
                System.out.print("["+domino.getFace2()+":"+domino.getFace1()+"]");
            }
        }
        System.out.println("\n");
    }

}
