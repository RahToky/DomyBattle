package mg.mahatoky.domy.exception;

/**
 * @author mtk_ext
 */
public class AmbiguousFacePositionException extends Exception{

    public AmbiguousFacePositionException(){
        super("Can't place domino because it can be placed on head or tail.");
    }
}
