package mg.mahatoky.domy.model;

/**
 * @author mtk_ext
 */
public class PlayerResponse {

    private Domino domino;
    private PLACE place;

    public PlayerResponse(){

    }

    public PlayerResponse(Domino domino, PLACE place) {
        this.domino = domino;
        this.place = place;
    }

    public Domino getDomino() {
        return domino;
    }

    public void setDomino(Domino domino) {
        this.domino = domino;
    }

    public PLACE getPlace() {
        return place;
    }

    public void setPlace(PLACE place) {
        this.place = place;
    }

    public static enum PLACE {
        HEAD,TAIL;
    }

    public boolean isValid(){
        return domino != null && place != null;
    }

}
