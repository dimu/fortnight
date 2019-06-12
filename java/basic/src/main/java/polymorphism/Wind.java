package polymorphism;

public class Wind extends Instrument {
	public void play(Note n){
		System.out.println("wind.play() " + n);
	}
}
