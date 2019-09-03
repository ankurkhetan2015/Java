package game;

import pieces.IPiece;

public interface IPromotionMove extends IMove {
    IPiece getPromotion();
}
