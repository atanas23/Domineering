import java.io.FileFilter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class BlackBoxDomineering2 implements MoveChannel<DomineeringMove> {

	private static Scanner scan = new Scanner(System.in);
	private static Player opponent;

	public BlackBoxDomineering2() {
	}

	@Override
	public DomineeringMove getMove() {

		int x, y;
		String move;
		move = scan.nextLine();
		x = Integer.valueOf(move.substring(0, 1));
		y = Integer.valueOf(move.substring(2, 3));

		if (opponent == Player.MAXIMIZER) {
			return new DomineeringMove(x, y, x + 1, y);
		} else
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
		if (Integer.valueOf(args[2]) + Integer.valueOf(args[3]) < 10) {
			if (args[0].equals("first")) {
				opponent = Player.MINIMIZER;
				DomineeringBoard2 board = new DomineeringBoard2(Integer.valueOf(args[2]), Integer.valueOf(args[3]));
				board.tree().firstPlayer(new BlackBoxDomineering2());
			} else if (args[0].equals("second")) {
				opponent = Player.MAXIMIZER;
				DomineeringBoard2 board = new DomineeringBoard2(Integer.valueOf(args[2]), Integer.valueOf(args[3]));
				board.tree().secondPlayer(new BlackBoxDomineering2()); 
			}
		}else {
			if (args[0].equals("first")) {
				opponent = Player.MINIMIZER;
				DomineeringBoard2 board = new DomineeringBoard2(Integer.valueOf(args[2]), Integer.valueOf(args[3]));
				board.heuristicTree(2).firstPlayer(new BlackBoxDomineering2());
			} else if (args[0].equals("second")) {
				opponent = Player.MAXIMIZER;
				DomineeringBoard2 board = new DomineeringBoard2(Integer.valueOf(args[2]), Integer.valueOf(args[3]));
				board.heuristicTree(2).secondPlayer(new BlackBoxDomineering2());
			}
		}
	}
}
