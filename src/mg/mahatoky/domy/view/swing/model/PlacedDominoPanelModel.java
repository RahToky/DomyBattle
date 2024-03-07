package mg.mahatoky.domy.view.swing.model;

import mg.mahatoky.domy.model.Domino;
import mg.mahatoky.domy.model.PlayerResponse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mtk_ext
 */
public class PlacedDominoPanelModel {
    private Integer tail;
    private Integer head;
    private Domino tailDomino;
    private Domino headDomino;

    private Point[] headCornersPoints;
    private Point[] tailCornersPoints;

    private int dominoShortSide;
    private int dominoLongSide;
    private Direction headDirection = Direction.LEFT;
    private Direction tailDirection = Direction.RIGHT;

    private java.util.List<Domino> dominoList = new ArrayList<>();

    private Direction lastHeadLefRightDirection = Direction.LEFT;
    private Direction lastTailLefRightDirection = Direction.RIGHT;
    private Direction lastHeadDirection = Direction.LEFT;
    private Direction lastTailDirection = Direction.RIGHT;
    private int headStepCount = 3;
    private int tailStepCount = 3;

    private int panelWidth;
    private int panelHeight;

    /**
     * @param width  parent panel width
     * @param height parent panel height
     */
    public PlacedDominoPanelModel(int width, int height) {
        this.panelWidth = width;
        this.panelHeight = height;
    }

    public Integer getTail() {
        return tail;
    }

    public Integer getHead() {
        return head;
    }

    public Domino getTailDomino() {
        return tailDomino;
    }

    public Domino getHeadDomino() {
        return headDomino;
    }

    public Point[] getHeadCornersPoints() {
        return headCornersPoints;
    }

    public void setHeadCornersPoints(Point[] headCornersPoints) {
        this.headCornersPoints = headCornersPoints;
    }

    public Point[] getTailCornersPoints() {
        return tailCornersPoints;
    }

    public void setTailCornersPoints(Point[] tailCornersPoints) {
        this.tailCornersPoints = tailCornersPoints;
    }

    public List<Domino> getDominoList() {
        return dominoList;
    }

    public void setDominoList(List<Domino> dominoList) {
        this.dominoList = dominoList;
    }

    public int getDominoShortSide() {
        return dominoShortSide;
    }

    public void setDominoShortSide(int dominoShortSide) {
        this.dominoShortSide = dominoShortSide;
    }

    public int getDominoLongSide() {
        return dominoLongSide;
    }

    public void setDominoLongSide(int dominoLongSide) {
        this.dominoLongSide = dominoLongSide;
    }

    public Direction getHeadDirection() {
        return headDirection;
    }

    public void setHeadDirection(Direction headDirection) {
        this.headDirection = headDirection;
    }

    public Direction getTailDirection() {
        return tailDirection;
    }

    public void setTailDirection(Direction tailDirection) {
        this.tailDirection = tailDirection;
    }

    public DominoPosition addDomino(Domino domino, PlayerResponse.PLACE place) {
        if (domino == null)
            return null;
        DominoPosition dominoPosition = new DominoPosition();
        dominoList.add(domino);
        if (dominoList.size() == 1) {
            dominoPosition.setVertical(domino.isDouble());
            head = domino.getFace1();
            tail = domino.getFace2();
            tailDomino = domino;
            headDomino = domino;
            if (domino.isDouble()) {
                headCornersPoints = new Point[]{
                        new Point((panelWidth - getDominoShortSide()) / 2, panelHeight / 2 - getDominoShortSide()),
                        new Point((panelWidth - getDominoShortSide()) / 2, panelHeight / 2 + getDominoShortSide())
                };
                tailCornersPoints = new Point[]{
                        new Point(headCornersPoints[0].x + getDominoShortSide(), headCornersPoints[0].y),
                        new Point(headCornersPoints[1].x + getDominoShortSide(), headCornersPoints[1].y)
                };
            } else {
                headCornersPoints = new Point[]{
                        new Point(panelWidth / 2 - getDominoShortSide(), (panelHeight - getDominoShortSide()) / 2),
                        new Point(panelWidth / 2 - getDominoShortSide(), (panelHeight + getDominoShortSide()) / 2)
                };
                tailCornersPoints = new Point[]{
                        new Point(headCornersPoints[0].x + getDominoLongSide(), headCornersPoints[0].y),
                        new Point(headCornersPoints[1].x + getDominoLongSide(), headCornersPoints[1].y)
                };
            }
            dominoPosition.setPoint(headCornersPoints[0]);
            return dominoPosition;
        }

        Point point = new Point();
        if (PlayerResponse.PLACE.HEAD.equals(place)) {
            if (headStepCount >= headDirection.maxStep && !headDomino.isDouble() && !domino.isDouble()) {
                switch (headDirection) {
                    case LEFT:
                        headDirection = Direction.TOP;
                        lastHeadLefRightDirection = Direction.LEFT;
                        break;
                    case RIGHT:
                        headDirection = Direction.TOP;
                        lastHeadLefRightDirection = Direction.RIGHT;
                        break;
                    case TOP:
                        headDirection = lastHeadLefRightDirection.equals(Direction.LEFT) ? Direction.RIGHT : Direction.LEFT;
                        break;
                }
                headStepCount = 0;
            }
            setHead(domino);
            if ((headDirection.equals(Direction.TOP) && !domino.isDouble()) ||
                    !headDirection.equals(Direction.TOP) && domino.isDouble()
            ) {
                dominoPosition.setVertical(true);
            }
            point = getNextDominoStartPoint(headDirection, true, domino.isDouble());
            updateHeadCorners(point, dominoPosition.isVertical);
            headDomino = domino;
            lastHeadDirection = headDirection;
            headStepCount++;
            if (domino.isDouble() && !headDirection.equals(Direction.TOP)) {
                headStepCount++;
            }
        } else {
            if (tailStepCount >= tailDirection.maxStep && !tailDomino.isDouble() && !domino.isDouble()) {
                switch (tailDirection) {
                    case LEFT:
                        tailDirection = Direction.BOT;
                        lastTailLefRightDirection = Direction.LEFT;
                        break;
                    case RIGHT:
                        tailDirection = Direction.BOT;
                        lastTailLefRightDirection = Direction.RIGHT;
                        break;
                    case BOT:
                        tailDirection = lastTailLefRightDirection.equals(Direction.LEFT) ? Direction.RIGHT : Direction.LEFT;
                        break;
                }
                tailStepCount = 0;
            }
            setTail(domino);
            if ((tailDirection.equals(Direction.BOT) && !domino.isDouble()) ||
                    !tailDirection.equals(Direction.BOT) && domino.isDouble()
            ) {
                dominoPosition.setVertical(true);
            }
            point = getNextDominoStartPoint(tailDirection, false, domino.isDouble());
            updateTailCorners(point, dominoPosition.isVertical);
            lastTailDirection = tailDirection;
            tailDomino = domino;
            tailStepCount++;
            if (domino.isDouble() && !tailDirection.equals(Direction.TOP)) {
                tailStepCount++;
            }
        }
        dominoPosition.setPoint(point);
        return dominoPosition;
    }

    private void setHead(Domino domino) {
        if (Direction.LEFT.equals(headDirection) || Direction.TOP.equals(headDirection)) {
            if (domino.getFace2() == head) {
                head = domino.getFace1();
            } else {
                head = domino.getFace2();
                domino.switchFaces();
            }
        } else if (Direction.RIGHT.equals(headDirection)) {
            if (domino.getFace1() == head) {
                head = domino.getFace2();
            } else {
                head = domino.getFace1();
                domino.switchFaces();
            }
        }
    }

    private void setTail(Domino domino) {
        if (Direction.RIGHT.equals(tailDirection) || Direction.BOT.equals(tailDirection)) {
            if (domino.getFace2() == tail) {
                tail = domino.getFace1();
                domino.switchFaces();
            } else {
                tail = domino.getFace2();
            }
        } else if (Direction.LEFT.equals(tailDirection)) {
            if (domino.getFace1() == tail) {
                tail = domino.getFace2();
                domino.switchFaces();
            } else {
                tail = domino.getFace1();
            }
        }
    }

    private void updateHeadCorners(Point dominoCurrentPosition, boolean currentIsVertical) {
        if (Direction.TOP.equals(headDirection)) {
            if (currentIsVertical) {
                headCornersPoints[0] = dominoCurrentPosition;
                headCornersPoints[1] = new Point(headCornersPoints[0].x + dominoLongSide, headCornersPoints[0].y);
            } else {
                headCornersPoints[0] = dominoCurrentPosition;
                headCornersPoints[1] = new Point(headCornersPoints[0].x + dominoShortSide, headCornersPoints[0].y);
            }
        } else if (Direction.RIGHT.equals(headDirection)) {
            if (currentIsVertical) {
                headCornersPoints[0] = new Point(dominoCurrentPosition.x + dominoShortSide, dominoCurrentPosition.y);
                headCornersPoints[1] = new Point(headCornersPoints[0].x, headCornersPoints[0].y + dominoLongSide);
            } else {
                headCornersPoints[0] = new Point(dominoCurrentPosition.x + dominoLongSide, dominoCurrentPosition.y);
                headCornersPoints[1] = new Point(headCornersPoints[0].x, headCornersPoints[0].y + dominoShortSide);
            }
        } else if (Direction.LEFT.equals(headDirection)) {
            headCornersPoints[0] = dominoCurrentPosition;
            if (currentIsVertical) {
                headCornersPoints[1] = new Point(headCornersPoints[0].x, headCornersPoints[0].y + dominoLongSide);
            } else {
                headCornersPoints[1] = new Point(headCornersPoints[0].x, headCornersPoints[0].y + dominoShortSide);
            }
        }
    }

    private void updateTailCorners(Point dominoCurrentPosition, boolean currentIsVertical) {
        if (Direction.BOT.equals(tailDirection)) {
            if (currentIsVertical) {
                tailCornersPoints[0] = new Point(dominoCurrentPosition.x, dominoCurrentPosition.y + dominoLongSide);
                tailCornersPoints[1] = new Point(tailCornersPoints[0].x + dominoShortSide, tailCornersPoints[0].y);
            } else {
                tailCornersPoints[0] = new Point(dominoCurrentPosition.x, dominoCurrentPosition.y + dominoShortSide);
                tailCornersPoints[1] = new Point(tailCornersPoints[0].x + dominoLongSide, tailCornersPoints[0].y);
            }
        } else if (Direction.RIGHT.equals(tailDirection)) {
            if (currentIsVertical) {
                tailCornersPoints[0] = new Point(dominoCurrentPosition.x + dominoShortSide, dominoCurrentPosition.y);
                tailCornersPoints[1] = new Point(tailCornersPoints[0].x, tailCornersPoints[0].y + dominoLongSide);
            } else {
                tailCornersPoints[0] = new Point(dominoCurrentPosition.x + dominoLongSide, dominoCurrentPosition.y);
                tailCornersPoints[1] = new Point(tailCornersPoints[0].x, tailCornersPoints[0].y + dominoShortSide);
            }
        } else if (Direction.LEFT.equals(tailDirection)) {
            tailCornersPoints[0] = dominoCurrentPosition;
            if (currentIsVertical) {
                tailCornersPoints[1] = new Point(tailCornersPoints[0].x, tailCornersPoints[0].y + dominoLongSide);
            } else {
                tailCornersPoints[1] = new Point(tailCornersPoints[0].x, tailCornersPoints[0].y + dominoShortSide);
            }
        }
    }

    private Point getNextDominoStartPoint(Direction direction, boolean isHead, boolean isDouble) {
        Point point = new Point();
        switch (direction) {
            case TOP://HEAD
                if (Direction.LEFT.equals(lastHeadDirection)) {
                    if (headDomino.isDouble()) {
                        point.x = headCornersPoints[0].x + getDominoShortSide() / 2;
                    } else {
                        point.x = headCornersPoints[0].x - (isDouble ? getDominoShortSide() / 2 : 0);
                    }
                    point.y = headCornersPoints[0].y - (isDouble ? getDominoShortSide() : getDominoLongSide());
                } else if (Direction.RIGHT.equals(lastHeadDirection)) {
                    point.x = headCornersPoints[0].x - getDominoShortSide();
                    point.y = headCornersPoints[0].y - getDominoLongSide();
                } else {
                    if (headDomino.isDouble()) {
                        point.x = headCornersPoints[0].x + getDominoShortSide() / 2;
                        point.y = headCornersPoints[0].y - getDominoLongSide();
                    } else {
                        if (isDouble) {
                            point.x = headCornersPoints[0].x - getDominoShortSide() / 2;
                            point.y = headCornersPoints[0].y - getDominoShortSide();
                        } else {
                            point.x = headCornersPoints[0].x;
                            point.y = headCornersPoints[0].y - getDominoLongSide();
                        }
                    }
                }
                break;
            case BOT: // TAIL
                if (Direction.LEFT.equals(lastTailDirection)) {
                    point.x = tailCornersPoints[0].x - getDominoShortSide();
                    point.y = tailCornersPoints[0].y;
                } else if (Direction.RIGHT.equals(lastTailDirection)) {
                    point = tailCornersPoints[0];
                } else {
                    point.y = tailCornersPoints[0].y;
                    if (tailDomino.isDouble()) {
                        point.x = tailCornersPoints[0].x + getDominoShortSide() / 2;
                    } else {
                        if (isDouble) {
                            point.x = tailCornersPoints[0].x - getDominoShortSide() / 2;
                        } else {
                            point.x = tailCornersPoints[0].x;
                        }
                    }
                }
                break;
            case LEFT:
                if (isHead) {
                    if (Direction.LEFT.equals(headDirection)) {
                        if (isDouble) {
                            point.x = headCornersPoints[0].x - getDominoShortSide();
                            point.y = headCornersPoints[0].y - getDominoShortSide() / 2;
                        } else {
                            if (headDomino.isDouble()) {
                                point.x = headCornersPoints[0].x - getDominoLongSide();
                                point.y = headCornersPoints[0].y + getDominoShortSide() / 2;
                            } else {
                                if (isDouble) {
                                    point.x = headCornersPoints[0].x - getDominoShortSide();
                                    point.y = headCornersPoints[0].y - getDominoLongSide() / 2;
                                } else {
                                    point.x = headCornersPoints[0].x - getDominoLongSide();
                                    point.y = headCornersPoints[0].y;
                                }
                            }
                        }
                    } else {//TOP
                        point.x = headCornersPoints[0].x - getDominoShortSide();
                        point.y = headCornersPoints[0].y + getDominoShortSide();
                    }
                } else {
                    if (Direction.LEFT.equals(lastTailDirection)) {// continue to left
                        if (isDouble) {
                            point.x = tailCornersPoints[0].x - getDominoShortSide();
                            point.y = tailCornersPoints[0].y - getDominoShortSide() / 2;
                        } else {
                            if (tailDomino.isDouble()) {
                                point.x = tailCornersPoints[0].x - getDominoLongSide();
                                point.y = tailCornersPoints[0].y + getDominoShortSide() / 2;
                            } else {
                                if (isDouble) {
                                    point.x = tailCornersPoints[0].x - getDominoShortSide();
                                    point.y = tailCornersPoints[0].y - getDominoLongSide() / 2;
                                } else {
                                    point.x = tailCornersPoints[0].x - getDominoLongSide();
                                    point.y = tailCornersPoints[0].y;
                                }
                            }
                        }
                    } else {//BOT
                        point.x = tailCornersPoints[0].x - getDominoShortSide();
                        point.y = tailCornersPoints[0].y;
                    }
                }
                break;
            default: // RIGHT
                if (isHead) {
                    if (Direction.RIGHT.equals(lastHeadDirection)) { // we continue to right
                        point.x = headCornersPoints[0].x;
                        if (headDomino.isDouble()) {
                            point.y = headCornersPoints[0].y + getDominoShortSide() / 2;
                        } else {
                            if (isDouble) {
                                point.y = headCornersPoints[0].y - getDominoShortSide() / 2;
                            } else {
                                point.y = headCornersPoints[0].y;
                            }
                        }
                    } else {// from top to right, so last is not double, current is not double too
                        point.x = headCornersPoints[0].x;
                        point.y = headCornersPoints[0].y - dominoShortSide;
                    }
                } else {
                    point.x = tailCornersPoints[0].x;
                    if (Direction.RIGHT.equals(tailDirection)) { // we continue to right
                        if (tailDomino.isDouble()) {
                            point.y = tailCornersPoints[0].y + getDominoShortSide() / 2;
                        } else {
                            if (isDouble) {
                                point.y = tailCornersPoints[0].y - getDominoShortSide() / 2;
                            } else {
                                point.y = tailCornersPoints[0].y;
                            }
                        }
                    } else { // from bot to right, so last is not double, current is not double too
                        point.y = tailCornersPoints[0].y;
                    }
                }
                break;
        }
        return point;
    }

    public void clear() {
        this.head = null;
        this.tail = null;
        this.headDomino = null;
        this.tailDomino = null;
        this.headStepCount = 3;
        this.tailStepCount = 3;
        this.headDirection = Direction.LEFT;
        this.tailDirection = Direction.RIGHT;
        lastHeadLefRightDirection = Direction.LEFT;
        lastTailLefRightDirection = Direction.RIGHT;
        lastHeadDirection = Direction.LEFT;
        lastTailDirection = Direction.RIGHT;
        this.dominoList.clear();
    }

    public enum Direction {
        TOP(2), BOT(2), LEFT(7), RIGHT(7);

        int maxStep;

        Direction(int maxStep) {
            this.maxStep = maxStep;
        }
    }

    public static class DominoPosition {
        private Point point;
        private boolean isVertical = false;

        public Point getPoint() {
            return point;
        }

        public void setPoint(Point point) {
            this.point = point;
        }

        public boolean isVertical() {
            return isVertical;
        }

        public void setVertical(boolean vertical) {
            isVertical = vertical;
        }
    }
}
