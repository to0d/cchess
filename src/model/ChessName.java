package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class ChessName {

	public final char name;

	private static int actionListnerSize = 0;

	public final int index;

	public final static int ��_index = 1;

	public final static int ʿ_index = 2;

	public final static int ��_index = 3;

	public final static int ��_index = 4;

	public final static int ��_index = 5;

	public final static int ��_index = 6;

	public final static int ��_index = 7;

	public final static ChessName �� = new ChessName('��', ��_index);

	public final static ChessName ʿ = new ChessName('ʿ', ʿ_index);

	public final static ChessName �� = new ChessName('��', ��_index);

	public final static ChessName �� = new ChessName('��', ��_index);

	public final static ChessName �� = new ChessName('��', ��_index);

	public final static ChessName �� = new ChessName('��', ��_index);

	public final static ChessName �� = new ChessName('��', ��_index);

	ArrayList<AcListener> acListeners = new ArrayList<AcListener>();

	private static Collection<ChessName> names = new ArrayList<ChessName>();

	static {
		names.add(��);
		names.add(ʿ);
		names.add(��);
		names.add(��);
		names.add(��);
		names.add(��);
		names.add(��);
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

		class StepOf���� implements AcListener {

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

		StepOf���� stepOf���� = new StepOf����();

		ChessName.��.registerNewActionListener(stepOf����);
		ChessName.��.registerNewActionListener(stepOf����);
		ChessName.ʿ.registerNewActionListener(new AcListener() {

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

		ChessName.��.registerNewActionListener(new AcListener() {

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

		ChessName.��.registerNewActionListener(new AcListener() {

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

		ChessName.��.registerNewActionListener(new AcListener() {

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

		ChessName.��.registerNewActionListener(new AcListener() {

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
