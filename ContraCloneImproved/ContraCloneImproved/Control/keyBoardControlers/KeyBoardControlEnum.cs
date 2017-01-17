using System;

namespace KeyBoardControlEnum
{
	// Comments above enum indicate corresponding key.
	public enum BuilderEnum
	{
		//                                     A	  		  S		 	   D			  F
		RIGHT = 0, LEFT = 1, UP = 2, DOWN = 3, FAST_DOWN = 4, FAST_UP = 5, FAST_LEFT = 6, FAST_RIGHT = 7, SPACE = 8,

		REFRESH_RATE = 20,
	}

	public enum BarrierEnum
	{
		// 		   Q		  C				W   	  E		   R		X		 	V		   U
		ENTER = 0, PRINT = 1, CAN_DROP = 2, DRAW = 3, INC = 4, DEC = 5, GROUND = 6, WATER = 7, UNDO = 8,

		REFRESH_RATE = 20,
	}

	public enum GameEnum
	{
		//									   S			  F			 D			
		RIGHT = 0, LEFT = 1, UP = 2, DOWN = 3, DROP_DOWN = 4, SHOOT = 5, JUMP = 6,

		REFRESH_RATE = 20,
	}

	public enum GameTestEnum
	{
		// 		   L
		ENTER = 0, LOAD = 1,

		REFRESH_RATE = 20,
	}

	public enum EnemyEnum
	{
		//ENTER    Q	  	 W
		ENTER = 0, DRAW = 1, PRINT = 2,
	

		REFRESH_RATE = 20,
	}
}

