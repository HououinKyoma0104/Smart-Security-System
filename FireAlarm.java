import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import upm_grove.GroveTemp;

public class FireAlarm {

	private static AlarmLcd lcdScreen;
	private static AlarmBuzzer buzzer;
	private static GroveTemp temperatureSensor;
	private static int lastTemperature = -1;
	private static  int temperatureThreshold;
	private static TimerTask alarmTask;
	private static Timer FireTimer;
	private static boolean alarmTick = true;
	private static boolean isAlertOn = false;
	private static Properties config = new Properties();

	public static void main(String[] args) {
		loadConfigurationFile();
		initiateSensors();
		listenToTemperatureChanges();
	}

	private static void loadConfigurationFile() {

		try {
			config.load(FireAlarm.class.getClassLoader().getResourceAsStream("config.properties"));
			temperatureThreshold =Integer.parseInt(config.getProperty("TEMPERATURE_THRESHOLD"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void handleFireAlertTick() {
		if(alarmTick){
			lcdScreen.setLcdColor("white");
			buzzer.stopBuzzing();
		}
		else{
			lcdScreen.setLcdColor("red");
			buzzer.buzz();
		}
		alarmTick = !alarmTick;
	}

	private static void initiateSensors() {

		lcdScreen = new AlarmLcd();
		buzzer = new AlarmBuzzer(5);
		temperatureSensor = new GroveTemp(0);
	}

	private static void listenToTemperatureChanges() {
		Timer temperatureCheckTimer = new Timer();
		temperatureCheckTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				int currentTemperature = temperatureSensor.value();
				lcdScreen.displayMessageOnLcd("temperature: " + currentTemperature, 0);
				System.out.println("value: " +  currentTemperature);
				if(lastTemperature < temperatureThreshold && currentTemperature >= temperatureThreshold && !isAlertOn){
					alertFire();
				}
				if(lastTemperature >= temperatureThreshold && currentTemperature < temperatureThreshold && isAlertOn){
					stopAlertingFire();
				}
				lastTemperature = currentTemperature;
			}


		},0,2000);
	}

	private static void alertFire() {

		Utils.sendMessageWithTwilio("fire alarm", config);
		Utils.notifyAzure((new Date().toString()), config);

		System.out.println("fire!");
		isAlertOn = true;
		lcdScreen.displayMessageOnLcd("fire detected", 1);
		lcdScreen.setLcdColor("red");
		buzzer.buzz();
		alarmTask = new TimerTask() {

			@Override
			public void run() {
				handleFireAlertTick();
			}
		};
		FireTimer = new Timer();
		FireTimer.schedule(alarmTask, 0,250);
	}

	private static void stopAlertingFire() {
		isAlertOn = false;
		System.out.println("fire stopped!");
		lcdScreen.displayMessageOnLcd("fire stopped", 1);
		lcdScreen.setLcdColor("white");
		buzzer.stopBuzzing();
		FireTimer.cancel();
	}

}