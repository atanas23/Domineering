
// Game trees for abstract games two-person games with outcomes in the
// type of integers, parametrized by a type of moves.

import java.util.*;

public class GameTree2<Move> {
	private final Board2<Move> board;
	private final LList<Entry<Move, GameTree2<Move>>> children;
	private final int optimalOutcome;

	public GameTree2(Board2<Move> board, LList<Entry<Move, GameTree2<Move>>> children, int optimalOutcome) {

		assert (board != null && children != null);
		this.board = board;
		this.children = children;
		this.optimalOutcome = optimalOutcome;
	}

	public boolean isLeaf() {
		return (children.isEmpty());
	}

	// Getter methods:
	public Board2<Move> board() {
		return board;
	}

	public LList<Entry<Move, GameTree2<Move>>> children() {
		return children;
	}

	public int optimalOutcome() {
		return optimalOutcome;
	}

	// The following two methods are for game tree statistics only.
	// They are not used for playing.

	// Number of tree nodes:
	public int size() { // is this going to work?
		if (children.length() == 0) {
			return 1;
		} else
			return children.length();
	}

	// We take the height of a leaf to be zero (rather than -1):
	public int height() {
		int height = -1;
		return 1 + height;
	}

	//--------------------------------------------------+++++-------------------------------------------------------------------------------
	// Plays first using this tree:
	public void firstPlayer(MoveChannel<Move> c) {
		c.comment(board + "\nThe optimal outcome is " + optimalOutcome);
		LList<Entry<Move, GameTree2<Move>>> notFinal = children;

		if (isLeaf()) { 
			if (!board.availableMoves().isEmpty()) {
				board.heuristicTree(2).firstPlayer(c);
			} else {
				c.end(board.value());
			}
		} else {
			Entry<Move, GameTree2<Move>> optimalEntry = null;
			for (int i = 0; i < children.length(); i++) {
				if (optimalOutcome == notFinal.head().value().optimalOutcome()) {
					optimalEntry = notFinal.head();
					break;
				}
				notFinal = notFinal.tail();
			}
			assert (optimalEntry != null);
			c.giveMove(optimalEntry.key());
			optimalEntry.value().secondPlayer(c);
		}
	}

	//--------------------------------------------------+++++-------------------------------------------------------------------------------
	// Plays second using this tree:
	public void secondPlayer(MoveChannel<Move> c) {
		c.comment(board + "\nThe optimal outcome is " + optimalOutcome);
		if (isLeaf()) { 
			if (!board.availableMoves().isEmpty()) {
				board.heuristicTree(2).secondPlayer(c);
			}else {
				c.end(board.value());
			}
		} else {
			Move m = c.getMove();
			if (!board().availableMoves().contains(m))
				System.exit(1);
			if (LList.lookup(children, m) != null) {
				LList.lookup(children, m).firstPlayer(c);
			} else {
				GameTree2<Move> subtree = board.play(m).heuristicTree(2);
				subtree.firstPlayer(c);
			}
		}
	}
}
