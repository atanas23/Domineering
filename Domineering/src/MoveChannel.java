// This is so that we can let the computer play against the user
// without deciding whether we are going to work with a command-line
// based or GUI based approach.

interface MoveChannel<Move> {
  public Move getMove();
  public void giveMove(Move move);
  public void end(int Value);
  public void comment(String msg);
}

// Is this correct level of abstraction?
// (I haven't tested it with a GUI yet.)
