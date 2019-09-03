package game.impl;

import game.*;
import game.impl.moves.CaptureMove;
import game.impl.moves.PawnStartJump;
import game.impl.moves.SimpleMove;
import pieces.*;
import pieces.impl.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Board implements IBoard {
    private static final char EMPTY = ' ';

    private IPiece[][] positions;
    private CastlingInfo castlingInfo;
    private Integer enPassant;

    public Board() {
        positions = new IPiece[IBoard.BOARD_HEIGHT][IBoard.BOARD_WIDTH];
    }

    @Override
    public void init() {
        positions[0][0] = new Rook(Color.WHITE);
        positions[0][1] = new Knight(Color.WHITE);
        positions[0][2] = new Bishop(Color.WHITE);
        positions[0][3] = new Queen(Color.WHITE);
        positions[0][4] = new King(Color.WHITE);
        positions[0][5] = new Bishop( Color.WHITE);
        positions[0][6] = new Knight(Color.WHITE);
        positions[0][7] = new Rook(Color.WHITE);

        positions[1][0] = new Pawn( Color.WHITE);
        positions[1][1] = new Pawn(Color.WHITE);
        positions[1][2] = new Pawn(Color.WHITE);
        positions[1][3] = new Pawn(Color.WHITE);
        positions[1][4] = new Pawn( Color.WHITE);
        positions[1][5] = new Pawn(Color.WHITE);
        positions[1][6] = new Pawn(Color.WHITE);
        positions[1][7] = new Pawn(Color.WHITE);


        positions[7][0] = new Rook(Color.BLACK);
        positions[7][1] = new Knight(Color.BLACK);
        positions[7][2] = new Bishop(Color.BLACK);
        positions[7][3] = new Queen(Color.BLACK);
        positions[7][4] = new King(Color.BLACK);
        positions[7][5] = new Bishop(Color.BLACK);
        positions[7][6] = new Knight(Color.BLACK);
        positions[7][7] = new Rook(Color.BLACK);

        positions[6][0] = new Pawn(Color.BLACK);
        positions[6][1] = new Pawn(Color.BLACK);
        positions[6][2] = new Pawn(Color.BLACK);
        positions[6][3] = new Pawn(Color.BLACK);
        positions[6][4] = new Pawn(Color.BLACK);
        positions[6][5] = new Pawn(Color.BLACK);
        positions[6][6] = new Pawn(Color.BLACK);
        positions[6][7] = new Pawn(Color.BLACK);

        castlingInfo = new CastlingInfo();
        enPassant = null;
    }

    @Override
    public IPiece getPiece(ILocation location) {
        return positions[location.getRow()][location.getCol()];
    }

    @Override
    public IBoard makeTurn(IMove move) {
        Board newBoard = new Board();
        CastlingInfo newCastlingInfo = new CastlingInfo(castlingInfo);

        int r = 0;
        int c = 0;
        for (IPiece piece : this) {
            newBoard.positions[r][c] = piece;
            if (c == IBoard.BOARD_WIDTH - 1) {
                r++;
                c = 0;
            }
            else {
                c++;
            }
        }

        for (ILocation[] positionChange : move.getPositionChanges()) {
            IPiece piece = newBoard.positions[positionChange[0].getRow()][positionChange[0].getCol()];
            newBoard.positions[positionChange[0].getRow()][positionChange[0].getCol()] = null;

            if (move instanceof SimpleMove) {
                if (move instanceof IPromotionMove) {
                    IPromotionMove promotionMove = (IPromotionMove) move;
                    if (promotionMove.getPromotion() != null) {
                        newBoard.positions[positionChange[1].getRow()][positionChange[1].getCol()] = promotionMove.getPromotion();
                        continue;
                    }
                }

                if (move instanceof PawnStartJump) {
                    newBoard.enPassant = ((PawnStartJump) move).getColumn();
                }

                if ((move instanceof CaptureMove) && (piece instanceof Pawn) && (newBoard.getPiece(positionChange[1]) == null)) {
                    if (piece.getColor() == Color.WHITE) {
                        newBoard.positions[positionChange[1].getRow()-1][positionChange[1].getCol()] = null;
                    }
                    else {
                        newBoard.positions[positionChange[1].getRow()+1][positionChange[1].getCol()] = null;
                    }
                    newBoard.enPassant = positionChange[0].getCol();
                }
            }
            newBoard.positions[positionChange[1].getRow()][positionChange[1].getCol()] = piece;

            updateCastlingInfo(newCastlingInfo, positionChange);
        }

        newBoard.castlingInfo = newCastlingInfo;
        return newBoard;
    }

    @Override
    public List<IMove> getPossibleMoves(GameStatus currentStatus) {
        List<IMove> results = new ArrayList<>();
        if (currentStatus.isOver()) {
            return results;
        }


        for (int r1 = 0; r1 < IBoard.BOARD_HEIGHT; r1++) {
            for (int c1 = 0; c1 < IBoard.BOARD_WIDTH; c1++) {
                IPiece piece = positions[r1][c1];
                if (piece == null) {
                    continue;
                }

                ILocation from = new Location(r1, c1);

                if ((piece.getColor() == Color.WHITE) == (currentStatus == GameStatus.WHITE_TURN)) {
                    results.addAll(piece.getPossibleMoves(this, from));
                }


                if ((piece.getColor() == Color.BLACK) == (currentStatus == GameStatus.BLACK_TURN)) {
                    results.addAll(piece.getPossibleMoves(this, from));
                }
            }
        }

        Collections.shuffle(results);

        return results;
    }

    @Override
    public GameStatus updateGameStatus(GameStatus gameStatus) {
        assert gameStatus == GameStatus.WHITE_TURN || gameStatus == GameStatus.BLACK_TURN;

        if (notEnoughPieces()) {
            return GameStatus.DRAW;
        }

        if (gameStatus == GameStatus.WHITE_TURN) {
            if (isCheckmate(Color.BLACK)) {
                return GameStatus.WHITE_WON;
            }
            else if (getPossibleMoves(GameStatus.BLACK_TURN).isEmpty()) {
                return GameStatus.DRAW;
            }
            return GameStatus.BLACK_TURN;
        }
        else {
            if (isCheckmate(Color.WHITE)) {
                return GameStatus.BLACK_WON;
            }
            else if (getPossibleMoves(GameStatus.WHITE_TURN).isEmpty()) {
                return GameStatus.DRAW;
            }
            return GameStatus.WHITE_TURN;
        }
    }

    @Override
    public boolean isCheck(Color color) {
        ILocation kingLocation = getKingLocation(color);
        IKing king = (IKing) getPiece(kingLocation);
        return king.isCheck(this, kingLocation);
    }

    @Override
    public boolean isCheckmate(Color color) {
        ILocation kingLocation = getKingLocation(color);
        IKing king = (IKing) getPiece(kingLocation);
        return king.isCheckMate(this, kingLocation);
    }

    @Override
    public Integer getEnPassant() {
        return enPassant;
    }

    @Override
    public CastlingInfo getCastlingInfo() {
        return castlingInfo;
    }

    private ILocation getKingLocation(Color color) {
        IKing king = null;
        ILocation kingLocation = null;
        for (int r = 0; r < IBoard.BOARD_HEIGHT; r++) {
            for (int c = 0; c < IBoard.BOARD_WIDTH; c++) {
                IPiece piece = positions[r][c];
                if (piece == null) {
                    continue;
                }
                if ((piece.getColor() == color) && (piece instanceof IKing)) {
                    king = (IKing) piece;
                    kingLocation = new Location(r, c);
                    break;
                }
            }
            if (king != null) {
                break;
            }
        }
        //System.out.println(kingLocation);
        return kingLocation;
    }

    private boolean notEnoughPieces() {
        List<IPiece> whitePieces = new ArrayList<>();
        List<IPiece> blackPieces = new ArrayList<>();
        boolean decidingPieces = false;
        for (IPiece piece : this) {
            if (piece == null) {
                continue;
            }

            if ((piece instanceof Pawn) || (piece instanceof  Rook) || (piece instanceof Queen)) {
                decidingPieces = true;
                break;
            }
            if (piece.getColor() == Color.WHITE) {
                whitePieces.add(piece);
            }
            else {
                blackPieces.add(piece);
            }
        }

        int whites = whitePieces.size();
        int blacks = blackPieces.size();
        if (decidingPieces || (whites > 3) || (blacks > 3) || (whites + blacks > 4)) {
            return false;
        }

        if (whites + blacks < 4) {
            return true;
        }

        //two knights against king
        if (((whites == 3) && (blacks == 1)) || ((whites == 1) && (blacks == 3))){
            boolean hasBishop = false;
            for (IPiece piece : (whites == 3) ? whitePieces : blackPieces) {
                if (piece instanceof Bishop) {
                    hasBishop = true;
                    break;
                }
            }
            return !hasBishop;
        }

        //a bishop aside
        ILocation whitePieceLocation = null;
        ILocation blackPieceLocation = null;
        for (int r = 0; r<IBoard.BOARD_HEIGHT; r++) {
            for (int c = 0; c<Board.BOARD_WIDTH; c++) {
                ILocation location = new Location(r,c);
                IPiece piece = getPiece(location);
                if (piece != null) {
                    if (piece instanceof Bishop) {
                        whitePieceLocation = location;
                    }
                    else {
                        blackPieceLocation = location;
                    }
                }
            }
        }
        if ((whitePieceLocation != null) && (blackPieceLocation != null)) {
            return (whitePieceLocation.getRow() + whitePieceLocation.getCol() + blackPieceLocation.getRow() + blackPieceLocation.getCol()) % 2 == 1;
        }

        return false;



    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Board:\n");
        for (int i = 0; i<BOARD_HEIGHT; i++) {
            for (int j = 0; j<BOARD_WIDTH; j++) {
                IPiece piece = positions[BOARD_HEIGHT - 1 - i][j];

                builder.append("[");
                if (piece == null) {
                    builder.append(EMPTY);
                }
                else {
                    builder.append(piece.getTextView());
                }
                builder.append("]");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public Iterator<IPiece> iterator() {
        return new Iterator<IPiece>() {
            int row = 0;
            int col = 0;

            @Override
            public boolean hasNext() {
                return (row < IBoard.BOARD_HEIGHT);
            }

            @Override
            public IPiece next() {
                IPiece piece = positions[row][col];
                if (col == IBoard.BOARD_HEIGHT - 1) {
                    row++;
                    col = 0;
                }
                else {
                    col++;
                }
                return piece;
            }
        };
    }

    private void updateCastlingInfo(CastlingInfo castlingInfo, ILocation[] positionChanges) {
        if (new Location(0,0).equals(positionChanges[0])) {
            castlingInfo.whiteRookAMoved = true;
        }
        else if (new Location(0,4).equals(positionChanges[0])) {
            castlingInfo.whiteKingMoved = true;
        }
        else if (new Location(0,7).equals(positionChanges[0])) {
            castlingInfo.whiteRookHMoved = true;
        }
        else if (new Location(7,0).equals(positionChanges[0])) {
            castlingInfo.blackRookAMoved = true;
        }
        else if (new Location(7,4).equals(positionChanges[0])) {
            castlingInfo.blackKingMoved = true;
        }
        else if (new Location(7,7).equals(positionChanges[0])) {
            castlingInfo.blackRookHMoved = true;
        }
    }

}
