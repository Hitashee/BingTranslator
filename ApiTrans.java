package Assignment;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.memetix.mst.language.Language;

public class ApiTrans{

	public static final String DatamarketAccessUri = "https://datamarket.accesscontrol.windows.net/v2/OAuth2-13";
	private String clientId;
	private String clientSecret;
	private String request;
	private AdminToken token;
	public String getRequest() {
	return request;
	}
public void setRequest(String request) {
		this.request = request;
}
	public AdminToken getToken() {
		return token;
	}
	public void setToken(AdminToken token) {
		this.token = token;
	}
	
	
	public ApiTrans(String clientId, String clientSecret) throws IOException
    {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	    this.clientId= URLEncoder.encode(this.clientId,"UTF-8");
	    this.clientSecret= URLEncoder.encode(this.clientSecret,"UTF-8");
		this.request= "grant_type=client_credentials&client_id="+ this.clientId +"&client_secret="+ this.clientSecret +"&scope=http://api.microsofttranslator.com";
		
    }
	
	public AdminToken getAccessTokenUsingPost(String DatamarketAccessUri,String request) throws IOException, ParseException{
		
		URL url= new URL(DatamarketAccessUri);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
	    conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	    DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(request);
		wr.flush();
		wr.close();

		int responseCode = conn.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + request);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		String res= response.toString();
		//print result
		System.out.println(response.toString());
		JSONParser parser = new JSONParser();
		JSONObject object= (JSONObject) parser.parse(res);
		AdminToken admToken= new AdminToken();
		String tokenType= (String) object.get("token_type");
		String accessToken=(String) object.get("access_token");
		String expiresIn=(String)object.get("expires_in");
		String scope= (String)object.get("scope");
		admToken.setAccessToken(accessToken);
		admToken.setTokenType(tokenType);
		admToken.setExpiresIn(expiresIn);
		admToken.setScope(scope);
		this.setToken(admToken);
		conn.disconnect();
		return this.getToken();
	}
	
	public String translateText(String from,String to,String text) throws IOException{
		String uri = "http://api.microsofttranslator.com/v2/Http.svc/Translate?";
		String request= "text="+URLEncoder.encode(text,"UTF-8")+ "&from=" + from + "&to=" + to;
		String authToken = "Bearer" + " " + this.getToken().getAccessToken();
		URL url= new URL(uri+request);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Authorization", authToken);
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		int responseCode = conn.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Post parameters : "+request);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println(response.toString());
		conn.disconnect();
		return response.toString();
	
	}
	static void translate() throws ParseException 
	{
		
		try {
			ApiTrans authToken= new ApiTrans(Data.clientId, Data.clientSecret);
			AdminToken token=authToken.getAccessTokenUsingPost(DatamarketAccessUri, authToken.getRequest());
			String line="";
			 File file = new File(Data.fileloc);
			 BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			  while( (line = br.readLine())!= null ){
			        String [] tokens = line.split(",");
			        String from = tokens[0];
			        String to = tokens[1];
			        String word = tokens[2];
			        Language in=Language.valueOf(from.toUpperCase());
			        Language op=Language.valueOf(to.toUpperCase());
			        String text=authToken.translateText(in.toString(),op.toString(), word);
			        text = text.substring(text.indexOf(">") + 1);
					text = text.substring(0, text.indexOf("<"));
					BingFile.fileWrite(text,Data.Mfile);
			        
			  }
			  br.close();
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	}
	
}