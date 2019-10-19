import upm_i2clcd.Jhd1313m1;


public class AlarmLcd {
	public static Jhd1313m1 lcdScreen;
	private static final short[] redColor = {255, 0, 0}; 
	private static final short[] whiteColor = {255, 255, 255}; 
	
	public AlarmLcd() {
		lcdScreen = new Jhd1313m1(1, 0x3E, 0x62);
		displayMessageOnLcd("",1);
	}
	
	public void setLcdColor(String colorString) {
		switch (colorString){
		case "red": 	lcdScreen.setColor(redColor[0], redColor[1], redColor[2]); break;
		case "white": 	lcdScreen.setColor(whiteColor[0], whiteColor[1], whiteColor[2]); break;
		}
	}	
	
    
    public void displayMessageOnLcd(String string, int line) {
		while (string.length() < 16) { string += " "; }
		lcdScreen.setCursor(line, 0);
		lcdScreen.write(string);
	}
	
}