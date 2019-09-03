package game.impl;

public class CastlingInfo {
    boolean whiteKingMoved = false;
    boolean whiteRookAMoved = false;
    boolean whiteRookHMoved = false;

    boolean blackKingMoved = false;
    boolean blackRookAMoved = false;
    boolean blackRookHMoved = false;

    public CastlingInfo() {}

    public CastlingInfo (CastlingInfo castlingInfo) {
        whiteKingMoved = castlingInfo.whiteKingMoved;
        whiteRookAMoved = castlingInfo.whiteRookAMoved;
        whiteRookHMoved = castlingInfo.whiteRookHMoved;
        blackKingMoved = castlingInfo.blackKingMoved;
        blackRookAMoved = castlingInfo.blackRookAMoved;
        blackRookHMoved = castlingInfo.blackRookHMoved;
    }

    public boolean isWhiteShortAllowed() {
        return !whiteKingMoved && !whiteRookHMoved;
    }

    public boolean isWhiteLongAllowed() {
        return !whiteKingMoved && !whiteRookAMoved;
    }

    public boolean isBlackShortAllowed() {
        return !blackKingMoved && !blackRookHMoved;
    }

    public boolean isBlackLongAllowed() {
        return !blackKingMoved && !blackRookAMoved;
    }
}