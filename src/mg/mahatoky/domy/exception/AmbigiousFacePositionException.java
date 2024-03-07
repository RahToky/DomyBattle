package mg.mahatoky.domy.exception;

/**
 * @author mtk_ext
 */
public class AmbigiousFacePositionException extends Exception{

    public AmbigiousFacePositionException(){
        super("Can't place domino because it can be placed on head or tail.");
    }
}
