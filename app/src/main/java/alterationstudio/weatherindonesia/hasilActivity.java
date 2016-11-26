package alterationstudio.weatherindonesia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class hasilActivity extends AppCompatActivity {

    private String TAG = hasilActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
    private List<String> tanggal;
    private List<String> kota1;
    private List<String> negara1;
    private List<String> sMain;
    private List<String> sTemp;
    private List<String> sPagi;
    private List<String> sSiang;
    private List<String> sMalam;
    private List<String> sSore;

    String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";

    public String readJSONFeed(String URL1){
        StringBuilder stringbuilder = new StringBuilder();
        String temp = URL1;
        URL openweather = null;

        try{
            openweather = new URL(temp);
            URLConnection oc = openweather.openConnection();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            oc.getInputStream()
                    )
            );

            String Input;

            while ((Input = br.readLine())!= null)
            {
                stringbuilder.append(Input);
            }
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return stringbuilder.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);

        lv = (ListView)findViewById(R.id.listCuaca) ;

        tanggal = new ArrayList<>();
        kota1 = new ArrayList<>();
        negara1 = new ArrayList<>();
        sMain = new ArrayList<>();
        sTemp = new ArrayList<>();
        sPagi = new ArrayList<>();
        sSiang = new ArrayList<>();
        sSore = new ArrayList<>();
        sMalam = new ArrayList<>();
        new getForecast().execute();

        Intent intent = getIntent();
        String city = intent.getStringExtra("Kota");
        String URL1 = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=389a47df45fd35c8a0c42eadb59f01f0";
        new hasilActivity.ReadPlacesFeedTask().execute(URL1);

        DateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy");
        String date = df.format(new Date());
        TextView tanggal = (TextView)findViewById(R.id.textView3);
        tanggal.setText(date);
    }

    public class ReadPlacesFeedTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {

            return readJSONFeed(urls[0]);
        }

        protected void onPostExecute(String result)
        {
            try {
                JSONObject jobj =new JSONObject(result);
                JSONArray arr2 = jobj.getJSONArray("weather");

                String temp=jobj.getJSONObject("main").getString("temp");

                float temp1=Float.valueOf(temp);
                float temp11= (float) (temp1-272.15);

                TextView derajad = (TextView)findViewById(R.id.derajad);
                String temperatur = String.valueOf(temp11);
                String DEGREE  = "\u00b0";
                Double temps = Double.parseDouble(temperatur);
                derajad.setText(String.format("%.1f", temps)+ DEGREE + "C");


                JSONArray arr = jobj.getJSONArray("weather");
                for (int i = 0; i < arr.length(); i++)
                {
                    String main = arr.getJSONObject(i).getString("main");
                    String s1=main;
//                    char[] chars = s1.toCharArray();
//                    chars[0] = Character.toUpperCase(chars[0]);
//                    s1= new String(chars);

                    TextView textView2=(TextView)findViewById(R.id.textView2);
                    ImageView imageView=(ImageView)findViewById(R.id.imageView);
                    if (s1.equals("Rain")){
                        textView2.setText("Hujan");
                        imageView.setImageResource(R.drawable.hujan);
                    }else if (s1.equals("Clear")){
                        textView2.setText("Cerah");
                        imageView.setImageResource(R.drawable.cerah);
                    }else if (s1.equals("Clouds")){
                        textView2.setText("Berawan");
                        imageView.setImageResource(R.drawable.berawan);
                    }else if (s1.equals("Thunderstorm")){
                        textView2.setText("Badai Petir");
                        imageView.setImageResource(R.drawable.petir);
                    }else if (s1.equals("Haze")){
                        textView2.setText("Berkabut");
                        imageView.setImageResource(R.drawable.kabut);
                    }else if (s1.equals("Mist")){
                        textView2.setText("Berembun");
                        imageView.setImageResource(R.drawable.embun);
                    }else{
                        textView2.setText(s1);
                        imageView.setImageResource(R.drawable.other);
                    }
                }

                String country = jobj.getJSONObject("sys").getString("country");
                String city = jobj.getString("name");

                TextView location = (TextView)findViewById(R.id.tvHello);
                location.setText(city + ", " + country);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

private class getForecast extends AsyncTask<Void, Void, Void> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(hasilActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected Void doInBackground(Void... arg0) {
        HttpHandler sh = new HttpHandler();
        Intent intent = getIntent();
        String citys = intent.getStringExtra("Kota");
        String jsonStr = sh.makeServiceCall(url + citys + "&mode=json&units=metric&APPID=389a47df45fd35c8a0c42eadb59f01f0");

        if (jsonStr != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);

                JSONObject city = jsonObject.getJSONObject("city");
                String kota = city.getString("name");
                String country = city.getString("country");
                kota1.add(kota);
                negara1.add(country);

                JSONArray arraylist = jsonObject.getJSONArray("list");

                for (int i = 0; i < arraylist.length(); i++) {

                    JSONObject c = arraylist.getJSONObject(i);

                    JSONObject temp = c.getJSONObject("temp");
                    String min_temp = temp.getString("min");
                    String max_temp = temp.getString("max");
                    Double temp1 = Double.parseDouble(min_temp);
                    Double temp2 = Double.parseDouble(max_temp);
                    Double temppagi = Double.parseDouble(temp.getString("morn"));
                    Double tempsiang = Double.parseDouble(temp.getString("day"));
                    Double tempsore = Double.parseDouble(temp.getString("eve"));
                    Double tempmalam = Double.parseDouble(temp.getString("night"));
                    String pagi = "Pagi: " + String.format("%.1f", temppagi);
                    String siang = "Siang: " + String.format("%.1f", tempsiang);
                    String sore = "Sore: " + String.format("%.1f", tempsore);
                    String malam = "Malam: " + String.format("%.1f", tempmalam);
                    String temps = String.format("%.1f", temp1) + "/" + String.format("%.1f", temp2);

                    JSONArray weather = c.getJSONArray("weather");
                    String main = weather.getJSONObject(0).getString("main");

                    sTemp.add(temps);
                    sPagi.add(pagi);
                    sSiang.add(siang);
                    sSore.add(sore);
                    sMalam.add(malam);
                    sMain.add(main);

                }


            } catch (final JSONException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Couldn't get json from server!",
                            Toast.LENGTH_LONG)
                            .show();
                }
            });
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        SimpleDateFormat curFormater = new SimpleDateFormat("EEE, MMM dd, yyyy");
        GregorianCalendar date = new GregorianCalendar();
        String[] dateStringArray = new String[7];

        for (int day = 0; day < 7; day++) {
            String tanggal1 = curFormater.format(date.getTime());
            date.roll(Calendar.DAY_OF_YEAR, true);
            tanggal.add(tanggal1);
        }
        // Dismiss the progress dialog
        if (pDialog.isShowing())
            pDialog.dismiss();

        /**
         * Updating parsed JSON data into ListView
         * */
        CustomListAdapter adapter = new CustomListAdapter(hasilActivity.this, tanggal, sMain, sTemp, sPagi, sSiang, sSore, sMalam);
        lv.setAdapter(adapter);

    }
}
}
