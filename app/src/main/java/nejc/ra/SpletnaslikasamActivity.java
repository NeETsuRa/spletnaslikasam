package nejc.ra;

import java.io.*;
import java.net.*;



import android.app.Activity;
import android.graphics.*;
import android.os.*;
import android.widget.*;

public class SpletnaslikasamActivity extends Activity {
	ImageView slika; //povem da bom prikazoval sliko (objekt slika)
	
	//metoda za download slike
	private Bitmap downloadImage(String url) { 
		Bitmap b = null; //slika
		InputStream in = null; //povezava
		try {
			in = openHttpConnection(url); //odpre url
			b=BitmapFactory.decodeStream(in); //shrani sliko
			in.close(); //zapre povezavo
		} catch (IOException e) {
			Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		return b;
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //naložimo in prikažemo sliko
        Bitmap b = downloadImage("http://www.aspieweb.net/wp-content/uploads/2010/08/aspergers-love.jpg");
        slika = (ImageView)findViewById(R.id.sl); //lokacija slike ki je na main.xml
        slika.setImageBitmap(b); // doloèitev slike
    }
    
    
    //branje povezave funkcijo klièemo zgoraj pri nalaganju slike
    //potrebujemo za vzpostavitev povezave
    private InputStream openHttpConnection(String url) throws IOException {
    	InputStream in=null; //definiramo povezavo
    	int odg = -1;
    	
    	URL u = new URL(url); //vpišemo naslov
    	URLConnection conn = u.openConnection(); //vzpostavimo povezavo
    	
    	if (!(conn instanceof HttpURLConnection))
    		throw new IOException("Ni HTTP povezava."); //napaka ob nepravilni povezavi
    	
    	try {
    		HttpURLConnection httpConn = (HttpURLConnection)conn;//povezava
    		httpConn.setAllowUserInteraction(false);//prepoved vmešanja uporabnika
    		httpConn.setInstanceFollowRedirects(true);
    		httpConn.setRequestMethod("GET");//nastavitev metode
    		httpConn.connect();//poveže
    		odg = httpConn.getResponseCode();//vpiše številko odgovora
    		if (odg == HttpURLConnection.HTTP_OK)
    			in = httpConn.getInputStream();//èe je odgovor pritrdiln si zapišemo povezavo
    	} catch (Exception ex) {
    		throw new IOException("Napaka pri povezavi.");
    	}//v primeru drugaènega odgovora napaka pri povezavi
    	
    	return in; //vrnemo povezavo
    }
}