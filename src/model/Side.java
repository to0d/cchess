package model;

public abstract class Side {

	String s;
	public final static Side ºì = new Side() {

		{

			s = "ºì";

		}

		@Override
		public ChessLocation getCoreLocation() {
			// TODO Auto-generated method stub
			return ChessLocation.newInstance(ChessState.X_MIDDLE, ChessState.Y_RED_CORE);
		}

		@Override
		public ChessDirection DownDir() {

			return ChessDirection.REDDown;
		}

		@Override
		public ChessDirection LeftDir() {
			// TODO Auto-generated method stub
			return ChessDirection.REDLeft;
		}

		@Override
		public ChessDirection LeftDownDir() {
			// TODO Auto-generated method stub
			return ChessDirection.REDLeftDown;
		}

		@Override
		public ChessDirection LeftUpDir() {
			// TODO Auto-generated method stub
			return ChessDirection.REDLeftUp;
		}

		@Override
		public ChessDirection RightDir() {
			// TODO Auto-generated method stub
			return ChessDirection.REDRight;
		}

		@Override
		public ChessDirection RightDownDir() {
			// TODO Auto-generated method stub
			return ChessDirection.REDRightDown;
		}

		@Override
		public ChessDirection RightUpDir() {
			// TODO Auto-generated method stub
			return ChessDirection.REDRightUp;
		}

		@Override
		public ChessDirection UpDir() {
			// TODO Auto-generated method stub
			return ChessDirection.REDUp;
		}

	};
	public final static Side ºÚ = new Side() {

		{
			s = "ºÚ";

		}

		@Override
		public ChessLocation getCoreLocation() {
			// TODO Auto-generated method stub
			return ChessLocation.newInstance(ChessState.X_MIDDLE, ChessState.Y_BLACK_CORE);
		}

		@Override
		public ChessDirection DownDir() {
			// TODO Auto-generated method stub
			return ChessDirection.BLACKDown;
		}

		@Override
		public ChessDirection LeftDir() {
			// TODO Auto-generated method stub
			return ChessDirection.BLACKLeft;
		}

		@Override
		public ChessDirection LeftDownDir() {
			// TODO Auto-generated method stub
			return ChessDirection.BLACKLeftDown;
		}

		@Override
		public ChessDirection LeftUpDir() {
			// TODO Auto-generated method stub
			return ChessDirection.BLACKLeftUp;
		}

		@Override
		public ChessDirection RightDir() {
			// TODO Auto-generated method stub
			return ChessDirection.BLACKRight;
		}

		@Override
		public ChessDirection RightDownDir() {
			// TODO Auto-generated method stub
			return ChessDirection.BLACKRightDown;
		}

		@Override
		public ChessDirection RightUpDir() {
			// TODO Auto-generated method stub
			return ChessDirection.BLACKUp;
		}

		@Override
		public ChessDirection UpDir() {
			// TODO Auto-generated method stub
			return ChessDirection.BLACKUp;
		}
	};

	public String toString() {
		return s;
	}

	public abstract ChessDirection LeftDir();

	public abstract ChessDirection RightDir();

	public abstract ChessDirection DownDir();

	public abstract ChessDirection UpDir();

	public abstract ChessDirection LeftUpDir();

	public abstract ChessDirection LeftDownDir();

	public abstract ChessDirection RightUpDir();

	public abstract ChessDirection RightDownDir();

	public abstract ChessLocation getCoreLocation();
}
