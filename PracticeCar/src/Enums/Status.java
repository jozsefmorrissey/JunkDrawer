package Enums;

public enum Status {
	FUNCTIONAL, KNOCKING_SOUND, WHINING_SOUND, BROKEN, CLOGGED, LEAKING;

	@Override
	public String toString(){
		switch(this){
		case FUNCTIONAL: return "functional";
		case KNOCKING_SOUND: return "knocking sound";
		case WHINING_SOUND: return "whining_sound";
		case BROKEN: return "broken";
		case CLOGGED: return "clogged"; 
		case LEAKING: return "leaking";
		default: return "not defined";
		}
	}
}
