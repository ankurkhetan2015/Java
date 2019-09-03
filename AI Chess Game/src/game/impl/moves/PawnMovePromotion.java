package game.impl.moves;

import game.ILocation;
import game.IPromotionMove;
import pieces.IPiece;

public class PawnMovePromotion extends Move implements IPromotionMove {
    private IPiece promotion;

    public PawnMovePromotion(IPiece piece, ILocation from, ILocation to, IPiece promotion) {
        super(piece, from, to);
        this.promotion = promotion;
    }

    @Override
    public IPiece getPromotion() {
        return promotion;
    }

    @Override
    public String toString() {
        String result = getPiece().getNotationView() + " " + getFrom().toString() + " - " + getTo().toString();
        if (getPromotion() != null) {
            result += " " + getPromotion().getTextView();
        }
        return result;
    }

}
