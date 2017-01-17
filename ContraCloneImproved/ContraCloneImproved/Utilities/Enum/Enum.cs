using System;

namespace ContraCloneImproved
{
	public enum animStateEnum
	{
		// Modulus is used to determine orientation for a given state.
		IDLE_RIGHT = 0, WALK_RIGHT = 2, JUMP_RIGHT = 4, CROUCH_RIGHT = 6, DIE_RIGHT = 8, FALL_RIGHT = 10, LAND_RIGHT = 12, TUCK_RIGHT = 14,

		IDLE_LEFT = 1, WALK_LEFT = 3, JUMP_LEFT = 5, CROUCH_LEFT = 7, DIE_LEFT = 9, FALL_LEFT = 11, LAND_LEFT = 13, TUCK_LEFT = 15,

		//FACING_RIGHT = 0, FACING_LEFT = 1,
	}

	public enum animImageEnum
	{
		IDLE_IMAGES = 0, WALK_IMAGES = 1, JUMP_IMAGES = 2, CROUCH_IMAGES = 3, DIE_IMAGES = 4, TUCK_IMAGES = 5, LAND_IMAGES = 6,
	}

	public enum attackEnum
	{
		PACE_ATTACK = 5000,
		BURST_ATTACK = 50,
	}

	public enum charEnum
	{
		GROUNDED, MOVING, DEAD
	}

	public enum keyEnum
	{
		RIGHT = 0,
		LEFT = 1,
		UP = 2,
		DOWN = 3,
		SPACE = 4,
		ENTER = 5,
		A = 6,
		D = 7,
		P = 8,
		C = 9,
		W = 10,
		G = 11,
		B = 12,
		L = 13,
		K = 14,
		X = 15,
		S = 16,
		Q = 17,
		U = 18,
		E = 19,
	}

	public enum gameKeyEnum
	{
		RIGHT = 0,
		LEFT = 1,
		UP = 2,
		DOWN = 3,
		SPACE = 4,
		ENTER = 5,
		L = 6,
		B = 7,
		V = 8,
		S = 9,
		F = 10,
		D = 11,
	}

	public enum timeEnum
	{
		CONTROL_REFRESH = 20,
		BARRIOR_BUILD_REFRESH = 500,
		DRAW_REFRESH = 20,
		HERO_IMAGE_REFRESH = 100,

		BULLET_LIFE = 2000,

		INPUT_TEST_TIMER = 5000,
	}

	public enum viewEnum
	{
		UP = 0,
		DOWN = 1,
		RIGHT = 2,
		LEFT = 3,
		LAST_RIGHT_LEFT = 4,
	}

	public enum teamEnum
	{
		FRIENDLY = 0,
		ENEMY = 1,
		NEUTRAL = 2,
	}

	public enum OriginalBackground
	{
		WIDTH = 902,
		HEIGHT = 13930,
	}
}

