package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class ChessName {

	public final char name;

	private static int actionListnerSize = 0;

	public final int index;

	public final static int 将_index = 1;

	public final static int 士_index = 2;

	public final static int 相_index = 3;

	public final static int 马_index = 4;

	public final static int 车_index = 5;

	public final static int 炮_index = 6;

	public final static int 兵_index = 7;

	public final static ChessName 将 = new ChessName('将', 将_index);

	public final static ChessName 士 = new ChessName('士', 士_index);

	public final static ChessName 相 = new ChessName('相', 相_index);

	public final static ChessName 马 = new ChessName('马', 马_index);

	public final static ChessName 车 = new ChessName('车', 车_index);

	public final static ChessName 炮 = new ChessName('炮', 炮_index);

	public final static ChessName 兵 = new ChessName('兵', 兵_index);

	ArrayList<AcListener> acListeners = new ArrayList<AcListener>();

	private static Collection<ChessName> names = new ArrayList<ChessName>();

	static {
		names.add(将);
		names.add(士);
		names.add(相);
		names.add(马);
		names.add(车);
		names.add(炮);
		names.add(兵);
	}

	static int possibleStepIndex;
	static {

		loadPossibleStepAction();
	}

	protected ChessName(char n, int uNum) {
		name = n;

		this.index = uNum;

	}

	public String toString() {
		return "" + name;
	}

	public void registerNewActionListener(AcListener listener) {

		acListeners.add(listener);

	}

	public Object fire(Object object, int n) {

		return acListeners.get(n).fire(object);
	}

	public AcListener getActionListener(int n) {
		return acListeners.get(n);
	}

	public static int confirmAllRegisterNAL() {
		boolean confirm = true;
		for (ChessName name : names) {
			if (name.acListeners.size() != actionListnerSize + 1) {
				confirm = false;
				break;
			}
		}
		if (!confirm) {

			for (ChessName name : names) {
				for (int i = actionListnerSize; i < name.acListeners.size(); i++) {
					name.acListeners.remove(i);
				}
			}

			return -1;

		} else {
			actionListnerSize++;

			return actionListnerSize - 1;
		}
	}

	public Collection<ChessStep> getPossibleSteps(ChessState state, ChessLocation location) {

		return (Collection<ChessStep>) fire(new StateAandLocation(state, location), possibleStepIndex);

	}

	class StateAandLocation {
		ChessState state;
		ChessLocation location;

		StateAandLocation(ChessState state, ChessLocation location) {
			this.state = state;
			this.location = location;
		}
	}

	private static void loadPossibleStepAction() {

		class StepOf兵将 implements AcListener {

			public Object fire(Object O) {

				StateAandLocation sl = (StateAandLocation) O;
				ChessLocation location = sl.location;
				ChessState state = sl.state;

				Collection<ChessStep> steps = new LinkedList<ChessStep>();

				for (ChessDirection dir : ChessDirection.LRUDDirection) {
					steps.add(new ChessStep(state, location, location.nextLocation(dir)));
				}

				return steps;
			}

		}

		StepOf兵将 stepOf兵将 = new StepOf兵将();

		ChessName.将.registerNewActionListener(stepOf兵将);
		ChessName.兵.registerNewActionListener(stepOf兵将);
		ChessName.士.registerNewActionListener(new AcListener() {

			public Object fire(Object O) {
				StateAandLocation sl = (StateAandLocation) O;
				ChessLocation location = sl.location;
				ChessState state = sl.state;

				Collection<ChessStep> steps = new LinkedList<ChessStep>();

				for (ChessDirection dir : ChessDirection.XieDirection)
					steps.add(new ChessStep(state, location, location.nextLocation(dir)));

				return steps;
			}
		});

		ChessName.相.registerNewActionListener(new AcListener() {

			public Object fire(Object O) {
				StateAandLocation sl = (StateAandLocation) O;
				ChessLocation location = sl.location;
				ChessState state = sl.state;
				Collection<ChessStep> steps = new LinkedList<ChessStep>();

				steps.add(new ChessStep(state, location, ChessLocation.newInstance(location.x + 2, location.y + 2)));
				steps.add(new ChessStep(state, location, ChessLocation.newInstance(location.x + 2, location.y - 2)));

				steps.add(new ChessStep(state, location, ChessLocation.newInstance(location.x - 2, location.y + 2)));

				steps.add(new ChessStep(state, location, ChessLocation.newInstance(location.x - 2, location.y - 2)));

				return steps;
			}
		});

		ChessName.马.registerNewActionListener(new AcListener() {

			public Object fire(Object O) {
				StateAandLocation sl = (StateAandLocation) O;
				ChessLocation location = sl.location;
				ChessState state = sl.state;
				Collection<ChessStep> steps = new LinkedList<ChessStep>();

				steps.add(new ChessStep(state, location, location.newAbsoluteLocation(2, 1)));
				steps.add(new ChessStep(state, location, location.newAbsoluteLocation(2, -1)));
				steps.add(new ChessStep(state, location, location.newAbsoluteLocation(-2, 1)));
				steps.add(new ChessStep(state, location, location.newAbsoluteLocation(-2, -1)));
				steps.add(new ChessStep(state, location, location.newAbsoluteLocation(1, 2)));
				steps.add(new ChessStep(state, location, location.newAbsoluteLocation(1, -2)));
				steps.add(new ChessStep(state, location, location.newAbsoluteLocation(-1, 2)));
				steps.add(new ChessStep(state, location, location.newAbsoluteLocation(-1, -2)));

				return steps;
			}
		});

		ChessName.车.registerNewActionListener(new AcListener() {

			public Object fire(Object O) {
				StateAandLocation sl = (StateAandLocation) O;
				ChessLocation location = sl.location;
				ChessState state = sl.state;
				Collection<ChessStep> steps = new LinkedList<ChessStep>();

				for (ChessDirection dir : ChessDirection.LRUDDirection) {
					ChessLocation nextLocation = location.nextLocation(dir);
					boolean finding = true;
					while (!nextLocation.outofBound() && finding) {
						ChessStep step = ChessStep.getChessStepNotAnalyse(state, location, nextLocation);

						steps.add(step);
						if (state.getPiece(nextLocation) != null)
							finding = false;
						nextLocation = nextLocation.nextLocation(dir);
					}
				}

				return steps;
			}
		});

		ChessName.炮.registerNewActionListener(new AcListener() {

			public Object fire(Object O) {
				StateAandLocation sl = (StateAandLocation) O;
				ChessLocation location = sl.location;
				ChessState state = sl.state;
				Collection<ChessStep> steps = new LinkedList<ChessStep>();

				for (ChessDirection dir : ChessDirection.LRUDDirection) {
					ChessLocation nextLocation = location.nextLocation(dir);

					int count = 0;
					while (!nextLocation.outofBound() && count < 2) {
						ChessStep step = ChessStep.getChessStepNotAnalyse(state, location, nextLocation);

						if (state.getPiece(nextLocation) != null)
							count++;

						if (count != 1)
							steps.add(step);

						nextLocation = nextLocation.nextLocation(dir);
					}
				}

				return steps;
			}
		});

		possibleStepIndex = ChessName.confirmAllRegisterNAL();

		if (possibleStepIndex == -1) {
			System.out.println("Error loadPossibleStepAction");
			return;
		}

	}
}
