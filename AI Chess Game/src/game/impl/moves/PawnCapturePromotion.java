package game.impl.moves;

import game.ILocation;
import game.IPromotionMove;
import pieces.IPiece;

public class PawnCapturePromotion extends CaptureMove implements IPromotionMove {
    private IPiece promotion;

    public PawnCapturePromotion(IPiece piece, ILocation from, ILocation to, IPiece promotion) {
        super(piece, from, to);
        this.promotion = promotion;
    }

    @Override
    public IPiece getPromotion() {
        return promotion;
    }
}
