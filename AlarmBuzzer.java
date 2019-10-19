import upm_buzzer.Buzzer;

public class AlarmBuzzer {
	private Buzzer buzzer;

	public AlarmBuzzer(int slot) {
		buzzer = new Buzzer(slot);
		stopBuzzing();
	}
	
	public void buzz() {
		buzzer.setVolume(0.5f);
		buzzer.playSound(2600, 0);
	}

	public void stopBuzzing() {
		buzzer.stopSound();
		buzzer.stopSound(); 
	}
	}