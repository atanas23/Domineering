//
//public class CommandLineDomineering {
//
//	private static class CommandLineD implements MoveChannel<DomineeringMove> {
//		public DomineeringMove getMove() {
//				return DomineeringMove.valueOf(System.console().readLine("Enter your move: "));
//			}
//
//		@Override
//		public void giveMove(DomineeringMove move) {
//			System.out.println("I play " + move);
//		}
//
//		@Override
//		public void end(int value) {
//			System.out.println("Game over. The result is " + value);
//		}
//
//		@Override
//		public void comment(String msg) {
//			System.out.println(msg);
//		}
//	}
//
//	public static void main(String[] args) {
//		DomineeringBoard board = new DomineeringBoard();
////		board.tree().firstPlayer(new CommandLineD());
//		board.tree().secondPlayer(new CommandLineD());
//	}
//
//}
