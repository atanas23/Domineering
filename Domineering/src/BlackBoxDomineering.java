import java.io.FileFilter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class BlackBoxDomineering implements MoveChannel<DomineeringMove> {

	private static Scanner scan = new Scanner(System.in);
	private static Player opponent;

	public BlackBoxDomineering() {
	}

	@Override
	public DomineeringMove getMove() {
		
		int x,y;
		String move;
		move = scan.nextLine();
		x = Integer.valueOf(move.substring(0, 1));
		y = Integer.valueOf(move.substring(2, 3));
		
		if (opponent == Player.MAXIMIZER) {
			return new DomineeringMove(x, y, x + 1, y);
		}else
			return new DomineeringMove(x, y, x, y + 1);
	}

	@Override
	public void giveMove(DomineeringMove move) {
		System.out.println(move.toString());
		System.out.flush();
	}

	@Override
	public void end(int Value) {
		System.exit(0);
	}

	@Override
	public void comment(String msg) {
		System.err.println(msg);
	}

	public static void main(String[] args) {
		assert (args.length == 4);
		if (args[0].equals("first")) {
			opponent = Player.MINIMIZER;
			DomineeringBoard board = new DomineeringBoard(Integer.valueOf(args[2]), Integer.valueOf(args[3]));
			board.tree().firstPlayer(new BlackBoxDomineering());
		} else if (args[0].equals("second")) {
			opponent = Player.MAXIMIZER;
			DomineeringBoard board = new DomineeringBoard(Integer.valueOf(args[2]), Integer.valueOf(args[3]));
			board.tree().secondPlayer(new BlackBoxDomineering());
		}
	}
}
