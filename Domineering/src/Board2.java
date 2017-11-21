
// An abstract two-player game with outcomes in the integers.
// We define a particular game by implementing the abstract methods.
//
// Our approach requires immutable implementations of Board.  We
// require that the only public constructor builds the initial board.
// Other constructors may be used for private purposes.

import java.io.*;
import java.util.*;
import java.lang.*;

public abstract class Board2<Move> {
	abstract Player nextPlayer();

	abstract Set<Move> availableMoves();

	abstract int value();

	abstract int hValue();

	abstract Board2<Move> play(Move move);
	
	private int lvl;

	//--------------------------------------------------+++++-------------------------------------------------------------------------------
	// Constructs the game tree of the board using the minimax algorithm
	// (without alpha-beta pruning):
	public GameTree2<Move> tree() {
		if (availableMoves().isEmpty())
			return new GameTree2<Move>(this, new Nil<Entry<Move, GameTree2<Move>>>(), value());
		else
			return (nextPlayer() == Player.MAXIMIZER ? maxTree2() : minTree2());
	}
	
	//--------------------------------------------------+++++-------------------------------------------------------------------------------
	// Two helper methods for that, which call the above method tree:
	public GameTree2<Move> maxTree() {
		int optimalOutcome = Integer.MIN_VALUE;
		LList<Entry<Move, GameTree2<Move>>> children = new Nil<Entry<Move, GameTree2<Move>>>();
		for (Move m : availableMoves()) {
			GameTree2<Move> subtree = play(m).heuristicTree(lvl - 1);
			children = children.append(new Cons<Entry<Move, GameTree2<Move>>>(
					new Entry<Move, GameTree2<Move>>(m, subtree), new Nil<Entry<Move, GameTree2<Move>>>()));
			optimalOutcome = Math.max(optimalOutcome, subtree.optimalOutcome());
		}

		return new GameTree2<Move>(this, children, optimalOutcome);
	}
	
	//--------------------------------------------------+++++-------------------------------------------------------------------------------
	public GameTree2<Move> maxTree2() {
		int optimalOutcome = Integer.MIN_VALUE;
		LList<Entry<Move, GameTree2<Move>>> children = new Nil<Entry<Move, GameTree2<Move>>>();
		
		for (Move m : availableMoves()) {
			GameTree2<Move> subtree = play(m).tree();
			children = children.append(new Cons<Entry<Move, GameTree2<Move>>>(
					new Entry<Move, GameTree2<Move>>(m, subtree), new Nil<Entry<Move, GameTree2<Move>>>()));
			optimalOutcome = Math.max(optimalOutcome, subtree.optimalOutcome());
			if (optimalOutcome == 1) {
				break;
			}
		}
		return new GameTree2<Move>(this, children, optimalOutcome);
	}
	
	//--------------------------------------------------+++++-------------------------------------------------------------------------------
	public GameTree2<Move> minTree() {
		int optimalOutcome = Integer.MAX_VALUE;
		LList<Entry<Move, GameTree2<Move>>> children = new Nil<Entry<Move, GameTree2<Move>>>();

		for (Move m : availableMoves()) {
			GameTree2<Move> subtree = play(m).heuristicTree(lvl - 1);
			children = children.append(new Cons<Entry<Move, GameTree2<Move>>>(
					new Entry<Move, GameTree2<Move>>(m, subtree), new Nil<Entry<Move, GameTree2<Move>>>()));
			optimalOutcome = Math.min(optimalOutcome, subtree.optimalOutcome());
		}
		return new GameTree2<Move>(this, children, optimalOutcome);
	}
	
	//--------------------------------------------------+++++-------------------------------------------------------------------------------
	public GameTree2<Move> minTree2() {
		int optimalOutcome = Integer.MAX_VALUE;
		LList<Entry<Move, GameTree2<Move>>> children = new Nil<Entry<Move, GameTree2<Move>>>();

		for (Move m : availableMoves()) {
			GameTree2<Move> subtree = play(m).tree();
			children = children.append(new Cons<Entry<Move, GameTree2<Move>>>(
					new Entry<Move, GameTree2<Move>>(m, subtree), new Nil<Entry<Move, GameTree2<Move>>>()));
			optimalOutcome = Math.min(optimalOutcome, subtree.optimalOutcome());
			if (optimalOutcome == -1) {
				break;
			}
		}
		return new GameTree2<Move>(this, children, optimalOutcome);
	}
	
	//--------------------------------------------------+++++-------------------------------------------------------------------------------
	public GameTree2<Move> heuristicTree(int level) {
		this.lvl = level;
		
		if (level == 0) {
			if (availableMoves().isEmpty())
				return new GameTree2<Move>(this, new Nil<Entry<Move, GameTree2<Move>>>(), hValue());
			else
				return new GameTree2<Move>(this, new Nil<Entry<Move, GameTree2<Move>>>(), hValue());
		} else {
			return (nextPlayer() == Player.MAXIMIZER ? maxTree() : minTree());
		}
	}
	
//	public GameTree2<Move> heuristicTree(int level) {
//		if (availableMoves().isEmpty())
//			return new GameTree2<Move>(this, new Nil<Entry<Move, GameTree2<Move>>>(), value());
//		else if (level == 0) 
//			return new GameTree2<Move>(this, new Nil<Entry<Move, GameTree2<Move>>>(), hValue());
//		else
//			return (nextPlayer() == Player.MAXIMIZER ? maxTree() : minTree());
//	}
}
