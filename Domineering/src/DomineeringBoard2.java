import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DomineeringBoard2 extends Board2<DomineeringMove> {

	private static final Player horiz = Player.MAXIMIZER; // player who puts
															// vertical lines
	private static final Player vert = Player.MINIMIZER; // player who puts
															// horizontal lines
	private int height = 4;
	private int width = 4;
	// private Player currentP = horiz; // horizontal player is always first

	private final boolean[][] board;

	private final HashSet<DomineeringMove> vertM; // moves of vertical player
	private final HashSet<DomineeringMove> horizM; // moves of horizontal player

	/**
	 * Constructor for h x w board
	 */
	public DomineeringBoard2(int w, int h) {
		height = h;
		width = w;
		boolean[][] board = new boolean[w][h];
		fill(board);
		this.board = board;
		vertM = DomineeringMove.noMoves();
		horizM = DomineeringMove.noMoves();
	}

	/**
	 * 
	 * @param h
	 *            - height of board
	 * @param w
	 *            - width of board
	 * @param vM
	 *            - moves of vertical player
	 * @param hM
	 *            - moves of horizontal player
	 */
	private DomineeringBoard2(int h, int w, HashSet<DomineeringMove> vM, HashSet<DomineeringMove> hM) {

		height = h;
		width = w;

		boolean[][] board = new boolean[w][h];
		fill(board);

		for (DomineeringMove m : vM) {
			board[m.getX()][m.getY()] = true;
			board[m.getX1()][m.getY1()] = true;
		}

		for (DomineeringMove m : hM) {
			board[m.getX()][m.getY()] = true;
			board[m.getX1()][m.getY1()] = true;
		}

		this.board = board;

		assert (disjoint(vM, hM));
		vertM = vM;
		horizM = hM;
	}

	/**
	 * Helper method
	 * 
	 * @param b
	 *            - 2D array of type boolean
	 * @return array, which on every position has "false"
	 */
	public boolean[][] fill(boolean[][] b) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				b[j][i] = false;
			}
		}
		return b;
	}

	/**
	 * switches between players
	 */
	@Override
	public Player nextPlayer() {
		return (vertM.size() + horizM.size()) % 2 == 0 ? horiz : vert;
	}

	/**
	 * @return all moves possible on the board
	 */
	@Override
	public Set<DomineeringMove> availableMoves() {
		Set<DomineeringMove> aMoves = new HashSet<DomineeringMove>();

		if (nextPlayer() == vert) {
			for (int i = 0; i < height - 1; i++) {
				for (int j = 0; j < width; j++) {
					if (!board[j][i] && !board[j][i + 1]) {
						aMoves.add(new DomineeringMove(j, i, j, i + 1));
					}
				}
			}
		} else {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width - 1; j++) {
					if (!board[j][i] && !board[j + 1][i]) {
						aMoves.add(new DomineeringMove(j, i, j + 1, i));
					}
				}
			}
		}
		return aMoves;
	}

	public boolean winsH(boolean[][] board) {
		for (int i = 0; i < height - 1; i++) {
			for (int j = 0; j < width; j++) {
				if (!board[j][i] && !board[j][i + 1]) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean winsV(boolean[][] board) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width - 1; j++) {
				if (!board[j][i] && !board[j + 1][i]) {
					return false;
				}
			}
		}
		return true;
	}


//	@Override
//	public int value() {
//		if (winsH(board)) {
//			return 1;
//		} else if (winsV(board)) {
//			return -1;
//		} else
//			return 0;
//	}
	@Override
	public int value() {
		if (nextPlayer() == horiz) 
			return (availableMoves().isEmpty() ? - 1 : 0);
		else
			return (availableMoves().isEmpty() ?  1 : 0);
	}
	
	public int hValue() {
		int hM = 0;
		int vM = 0;
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (i < width -1 && !board[i][j] && !board[i + 1][j]) {
					hM++;
				}
				if ( j < height - 1 && !board[i][j] && !board[i][j + 1]) {
					vM++;
				}
			}
		}
		return hM - vM;
	}

	@Override
	public Board2<DomineeringMove> play(DomineeringMove move) {
		if (nextPlayer() == horiz)
			return new DomineeringBoard2(height, width, add(vertM, move), horizM);
		else
			return new DomineeringBoard2(height, width, vertM, add(horizM, move));
	}

	public String toString() {
		String field = "";
		if (board.length > 0) {
			for (int i = 0; i < board.length; i++) {
				field += ("+---");
			}
			field += "+\n";
			for (int i = 0; i < board[0].length; i++) {
				for (int j = 0; j < board.length; j++) {
					field += "|";
					if (board[j][i] == true) {
						field += " X ";
					} else {
						field += "   ";
					}
				}
				field += "|";
				field += ("\n");

				for (int j = 0; j < board.length; j++) {
					field += ("+---");
				}
				field += "+\n";
			}
		}
		return field;
	}

	// these methods ensure immutability
	private static HashSet<DomineeringMove> add(HashSet<DomineeringMove> a, DomineeringMove b) {
		@SuppressWarnings("unchecked")
		HashSet<DomineeringMove> c = (HashSet<DomineeringMove>) a.clone();
		c.add(b);
		return c;
	}

	private static HashSet<DomineeringMove> intersection(HashSet<DomineeringMove> a, HashSet<DomineeringMove> b) {
		@SuppressWarnings("unchecked")
		HashSet<DomineeringMove> c = ((HashSet<DomineeringMove>) a.clone());
		c.retainAll(b);
		return c;
	}

	private static boolean disjoint(HashSet<DomineeringMove> a, HashSet<DomineeringMove> b) {
		return intersection(a, b).isEmpty();
	}

}
