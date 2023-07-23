package model;

public class ChessStep implements Sidable {

	ChessState state;

	ChessPiece fromPiece;

	ChessPiece toPiece;

	public ChessLocation fromLocation;

	public ChessLocation toLocation;

	protected boolean notMeetRule = false;

	protected boolean eatSelf = false;

	protected boolean attack = false;

	private static int checkSpecialIndex;

	private static int check将对面Index;

	private static int rightDistancIndex;

	private static int affectIndex;

	static {
		loadSpecialCheckAction();
		load将对面CheckAction();
		loadRightDistanceActionListner();
		loadAffectActionListner();
	}

	public ChessStep() {

	};

	public static ChessStep getChessStepNotAnalyse(ChessState state, ChessLocation fromLocation,
			ChessLocation toLocation) {

		ChessStep step = new ChessStep();
		step.fromLocation = fromLocation;
		step.toLocation = toLocation;
		step.state = state;

		if (step.fromLocation.outofBound() || step.toLocation.outofBound()) {
			step.notMeetRule = true;
			return step;
		}

		step.fromPiece = state.getPiece(fromLocation);
		step.toPiece = state.getPiece(toLocation);

		if (fromLocation.equal(toLocation)) {
			step.notMeetRule = true;
			return step;
		}

		if (step.fromPiece == null) {
			step.notMeetRule = true;
			return step;
		}

		if (!step.isRightDistance()) {
			step.notMeetRule = true;
			return step;
		}

		if (step.toPiece != null) {
			step.attack = true;
			if (step.toPiece.whichSide() == step.fromPiece.whichSide()) {
				step.eatSelf = true;
			}
		}

		step.notMeetRule = step.check将对面();

		return step;
	}

	public ChessStep(ChessState state, ChessLocation from, ChessLocation to) {

		this.state = state;
		this.fromLocation = from;
		this.toLocation = to;

		if (fromLocation.outofBound() || toLocation.outofBound()) {
			notMeetRule = true;
			return;
		}

		fromPiece = state.getPiece(fromLocation);
		toPiece = state.getPiece(toLocation);

		if (fromLocation.equal(toLocation)) {
			notMeetRule = true;
			return;
		}

		if (fromPiece == null) {
			notMeetRule = true;
			return;
		}

		if (!isRightDistance()) {
			notMeetRule = true;
			return;
		}

		if (toPiece != null) {
			attack = true;
			if (toPiece.whichSide() == fromPiece.whichSide()) {
				eatSelf = true;
			}
		}

		if (!specialCheckOK()) {
			notMeetRule = true;
			return;
		}

		if (!eatSelf) {
			notMeetRule = check将对面();
		}
	}

	public String toString() {

		return this.fromPiece + " " + this.fromLocation + " " + this.toLocation;

	}

	public boolean notMeetRule() {
		return notMeetRule;
	}

	public boolean isMove() {

		return !notMeetRule && !attack;

	}

	public ChessDirection dirction() {
		return ChessDirection.getRelativelyDir(fromLocation, toLocation);
	}

	public boolean isEat() {

		return !notMeetRule && attack && !eatSelf;

	}

	public boolean isProtect() {

		return !notMeetRule && attack && eatSelf;

	}

	public boolean isWrong() {

		return notMeetRule || eatSelf;
	}

	public boolean isAttack() {
		return attack;
	}

	public boolean isEatSelf() {

		return eatSelf;
	}

	private static void loadSpecialCheckAction() {
		AcListener l1 = new AcListener() {

			public Object fire(Object O) {
				ChessStep step = (ChessStep) O;

				return step.toLocation.squareDistance(step.whichSide().getCoreLocation()) <= 2;

			}

		};

		ChessName.将.registerNewActionListener(l1);
		ChessName.士.registerNewActionListener(l1);
		ChessName.相.registerNewActionListener(new AcListener() {

			public Object fire(Object O) {
				ChessStep step = (ChessStep) O;
				if (step.toLocation.whichSide() != step.whichSide()) {

					return false;
				}

				return (step.state.getPiece(ChessLocation.newInstance((step.fromLocation.x + step.toLocation.x) / 2,
						(step.fromLocation.y + step.toLocation.y) / 2)) == null);

			}
		});

		ChessName.马.registerNewActionListener(new AcListener() {

			ChessLocation checkLocation;

			public Object fire(Object O) {
				ChessStep step = (ChessStep) O;
				int i = step.toLocation.y - step.fromLocation.y;

				if (i == 1 || i == -1) {

					checkLocation = ChessLocation.newInstance((step.fromLocation.x + step.toLocation.x) / 2,
							step.fromLocation.y);
				} else {

					checkLocation = ChessLocation.newInstance(step.fromLocation.x,
							(step.fromLocation.y + step.toLocation.y) / 2);

				}

				return (step.state.getPiece(checkLocation) == null);

			}
		});

		ChessName.车.registerNewActionListener(new AcListener() {

			public Object fire(Object O) {
				ChessStep step = (ChessStep) O;

				ChessDirection dir = ChessDirection.getRelativelyDir(step.fromLocation, step.toLocation);

				if (dir == null)
					return false;

				ChessLocation nextLocation = step.fromLocation.nextLocation(dir);

				while (!nextLocation.equal(step.toLocation)) {
					if (step.state.getPiece(nextLocation) != null)
						return false;
					nextLocation = nextLocation.nextLocation(dir);
				}

				return true;

			}

		});

		ChessName.炮.registerNewActionListener(new AcListener() {

			public Object fire(Object O) {
				ChessStep step = (ChessStep) O;

				ChessDirection dir = ChessDirection.getRelativelyDir(step.fromLocation, step.toLocation);

				if (dir == null)
					return false;

				int j = 0;

				ChessLocation nextLocation = step.fromLocation.nextLocation(dir);

				while (!nextLocation.equal(step.toLocation)) {
					if (step.state.getPiece(nextLocation) != null)
						j++;
					nextLocation = nextLocation.nextLocation(dir);
				}

				if (j == 0) {
					return step.state.getPiece(step.toLocation) == null;

				} else if (j == 1) {
					return step.state.getPiece(step.toLocation) != null;

				} else {
					return false;
				}

			}
		});

		ChessName.兵.registerNewActionListener(new AcListener() {

			public Object fire(Object O) {
				ChessStep step = (ChessStep) O;

				return (step.fromLocation.whichSide().UpDir() == step.dirction()) == (step
						.whichSide() == step.fromLocation.whichSide());

			}
		});

		checkSpecialIndex = ChessName.confirmAllRegisterNAL();

	}

	public boolean specialCheckOK() {

		return (Boolean) (fromPiece.name().fire(this, checkSpecialIndex));

	}

	private static void loadRightDistanceActionListner() {

		class rightDistanc车炮 implements AcListener {

			public Object fire(Object O) {

				ChessStep step = (ChessStep) O;

				return ((step.fromLocation.x == step.toLocation.x) || (step.fromLocation.y == step.toLocation.y));

			}

		}

		class rightDistanc其他 implements AcListener {
			int squareDistance;

			rightDistanc其他(int n) {
				squareDistance = n;
			}

			public Object fire(Object O) {
				ChessStep step = (ChessStep) O;
				return step.squareDistance() == squareDistance;
			}

		}

		ChessName.车.registerNewActionListener(new rightDistanc车炮());
		ChessName.炮.registerNewActionListener(new rightDistanc车炮());
		ChessName.将.registerNewActionListener(new rightDistanc其他(1));
		ChessName.士.registerNewActionListener(new rightDistanc其他(2));
		ChessName.相.registerNewActionListener(new rightDistanc其他(8));
		ChessName.马.registerNewActionListener(new rightDistanc其他(5));
		ChessName.兵.registerNewActionListener(new rightDistanc其他(1));
		rightDistancIndex = ChessName.confirmAllRegisterNAL();
		if (rightDistancIndex == -1) {
			System.out.println("Error loadRightDistancActionListener");
			return;
		}

		// affect register

	}

	private static void loadAffectActionListner() {
		AcListener al = new AcListener() {

			public Object fire(Object O) {

				ChessStep step = (ChessStep) O;

				return !step.notMeetRule;
			}

		};

		ChessName.兵.registerNewActionListener(al);
		ChessName.士.registerNewActionListener(al);
		ChessName.将.registerNewActionListener(al);
		ChessName.车.registerNewActionListener(al);
		ChessName.炮.registerNewActionListener(al);
		ChessName.相.registerNewActionListener(new AcListener() {

			public Object fire(Object O) {

				ChessStep step = (ChessStep) O;

				return step.squareDistance() == 2 || !step.notMeetRule;

			}
		});

		ChessName.马.registerNewActionListener(new AcListener() {

			public Object fire(Object O) {
				ChessStep step = (ChessStep) O;

				return step.squareDistance() == 1 || !step.notMeetRule;
			}
		});

		affectIndex = ChessName.confirmAllRegisterNAL();

		if (affectIndex == -1) {
			System.out.println("Error loadAffectActionListner");
			return;
		}

	}

	public boolean isAffected() {
		return (Boolean) fromPiece.name().fire(this, affectIndex);
	}

	private static void load将对面CheckAction() {

		ChessName.将.registerNewActionListener(new AcListener() {

			public Object fire(Object O) {
				ChessStep step = (ChessStep) O;
				ChessLocation fromLocation = step.fromLocation;
				ChessLocation toLocation = step.toLocation;

				if (fromLocation.x == toLocation.x) {
					if (!step.attack)

						return false;

					if (toLocation.getRelativeY() < fromLocation.getRelativeY())
						return false;

				}

				if (step.state.getBlack将Locaiton().getAbsoluteX() != step.state.getRed将Location().getAbsoluteX())
					return false;

				Side side = step.fromPiece.whichSide();

				ChessDirection dir = side.UpDir();

				ChessLocation nextLocation = toLocation.nextLocation(dir);

				ChessState state = step.state;

				while (!nextLocation.outofBound()) {
					if (state.getPiece(nextLocation) != null)

						return state.getPiece(nextLocation).name() == ChessName.将;

					nextLocation = nextLocation.nextLocation(dir);
				}

				return false;

			}
		});

		AcListener al = new AcListener() {

			public Object fire(Object O) {
				ChessStep step = (ChessStep) O;
				ChessLocation fromLocation = step.fromLocation;
				ChessLocation toLocation = step.toLocation;

				if (step.state.getBlack将Locaiton().getAbsoluteX() != step.state.getRed将Location().getAbsoluteX())
					return false;

				if (step.state.getBlack将Locaiton().getAbsoluteX() != fromLocation.getAbsoluteX())
					return false;

				if (fromLocation.getAbsoluteX() != toLocation.getAbsoluteX()) {

					ChessLocation nextLocation = fromLocation.nextLocation(ChessDirection.REDUp);

					boolean find = false;

					while (!nextLocation.outofBound()) {
						ChessPiece piece = step.state.getPiece(nextLocation);
						if (piece != null) {
							if (piece.name() == ChessName.将) {
								find = true;
							}

							break;
						}

						nextLocation = nextLocation.nextLocation(ChessDirection.REDUp);
					}

					if (!find)
						return false;

					find = false;
					nextLocation = fromLocation.nextLocation(ChessDirection.REDDown);

					while (!nextLocation.outofBound()) {
						ChessPiece piece = step.state.getPiece(nextLocation);
						if (piece != null) {
							if (piece.name() == ChessName.将) {
								find = true;
							}

							break;
						}

						nextLocation = nextLocation.nextLocation(ChessDirection.REDDown);
					}

					return find;

				} else {

					ChessDirection dir = ChessDirection.getRelativelyDir(fromLocation, toLocation);

					if (dir == null)
						return false;

					ChessLocation nextLocation = fromLocation.nextLocation(dir);

					boolean find = false;
					while (!nextLocation.outofBound() && !nextLocation.equal(toLocation)) {
						ChessPiece piece = step.state.getPiece(nextLocation);
						if (piece != null) {
							if (piece.name() == ChessName.将) {
								find = true;
							}

							break;
						}

						nextLocation = nextLocation.nextLocation(dir);
					}

					if (!find)
						return false;

					dir = dir.opposite();

					find = false;

					nextLocation = fromLocation.nextLocation(dir);

					while (!nextLocation.outofBound()) {
						ChessPiece piece = step.state.getPiece(nextLocation);
						if (piece != null) {
							if (piece.name() == ChessName.将) {
								find = true;
							}

							break;
						}

						nextLocation = nextLocation.nextLocation(dir);
					}

					return find;

				}

			}

		};

		ChessName.士.registerNewActionListener(al);
		ChessName.相.registerNewActionListener(al);
		ChessName.马.registerNewActionListener(al);
		ChessName.车.registerNewActionListener(al);
		ChessName.炮.registerNewActionListener(al);
		ChessName.兵.registerNewActionListener(al);

		check将对面Index = ChessName.confirmAllRegisterNAL();

		if (check将对面Index == -1) {
			System.out.println("Error load将对面CheckAction");
			return;
		}
	}

	public boolean check将对面() {

		return (Boolean) fromPiece.name().fire(this, check将对面Index);

	}

	public Side whichSide() {
		if (!notMeetRule())
			return fromPiece.whichSide();
		else
			return null;
	}

	public boolean isRightDistance() {

		return (Boolean) fromPiece.name().fire(this, rightDistancIndex);

	}

	public int squareDistance() {

		return fromLocation.squareDistance(toLocation);
	}

}
