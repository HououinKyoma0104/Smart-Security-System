
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import com.google.gson.JsonObject;

public class Utils {

	public static void notifyAzure(String value, Properties config) {
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					String url = config.getProperty("SERVER");
					HttpURLConnection httpConnection = (HttpURLConnection) (new URL(url)).openConnection();
					httpConnection.setRequestMethod("PUT");
					httpConnection.setDoOutput(true);
					httpConnection.setDoInput(true);
					httpConnection.setRequestProperty("Content-Type", "application/json");
					httpConnection.setRequestProperty("Accept", "application/json");
					httpConnection.setRequestProperty("X-Auth-Token", config.getProperty("AUTH_TOKEN"));
					JsonObject jsonObject = new JsonObject();
					jsonObject.addProperty("value", value);
					OutputStreamWriter wr = new OutputStreamWriter(httpConnection.getOutputStream());
					wr.write(jsonObject.toString());
					wr.flush();
					int HttpResult = httpConnection.getResponseCode(); 
					if(HttpResult != HttpURLConnection.HTTP_OK){
						StringBuilder sb = new StringBuilder();  
						BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(),"utf-8"));  
						String line = null;  
						while ((line = br.readLine()) != null) {  
							sb.append(line + "\n");  
						}  
						br.close();  
						System.out.println(""+sb.toString());  
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("problem posting data to azure");
				
			}
		}});
		thread.run();
		
	}

	public static void sendMessageWithTwilio(String body, Properties config){
		final String ACCOUNT_SID = config.getProperty("TWILIO_ACCT_SID");
		final String AUTH_TOKEN = config.getProperty("TWILIO_AUTH_TOKEN");
		final String TWILIO_OUTGOING_NUMBER = config.getProperty("TWILIO_OUTGOING_NUMBER");
		final String NUMBER_TO_SEND_TO = config.getProperty("NUMBER_TO_SEND_TO");
