package mg.mahatoky.domy.model;

/**
 * @author mtk_ext
 */
public class Domino implements Comparable<Domino>{

    private int face1;
    private int face2;

    public Domino(int face1, int face2) {
        this.face1 = face1;
        this.face2 = face2;
    }

    public int getFace1() {
        return face1;
    }

    public void setFace1(int face1) {
        this.face1 = face1;
    }

    public int getFace2() {
        return face2;
    }

    public void setFace2(int face2) {
        this.face2 = face2;
    }

    public boolean isDouble(){
        return face1 == face2;
    }

    @Override
    public String toString() {
        return "[" + face1 + ":" + face2 + "]";
    }

    public int countEyes(){
        return face1 + face2;
    }

    public boolean have(int eye){
        return face1 == eye || face2 == eye;
    }

    public boolean match(Domino domino){
        if(domino == null)
            return false;
        return have(domino.face1) || have(domino.face2);
    }

    public void switchFaces(){
        int temp = face1;
        this.face1 = face2;
        this.face2 = temp;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Domino)){
            return false;
        }
        return have(((Domino) obj).face1) && have(((Domino) obj).face2);
    }

    @Override
    public int compareTo(Domino o) {
        return Integer.compare(countEyes(), o.countEyes());
    }
}
