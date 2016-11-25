package alterationstudio.weatherindonesia;

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
import java.util.Arrays;
import java.util.Date;

public class hasilActivity extends AppCompatActivity {

    private ListView daftar;
    //private String[] jadwalCuaca;
    /*private String[]    jadwalCuaca = new String[]{
            "Rabu, 23 Nov 2016 - Cerah","Kamis, 24 Nov 2016 - Hujan","Jumat, 25 Nov 2016 - Berawan",
            "Sabtu, 26 Nov 2016 - Cerah","Minggu, 27 Nov 2016 - Berawan","Senin, 28 Nov 2016 - Hujan",
            "Selasa, 29 Nov 2016 - Cerah","Rabu, 30 Nov 2016 - Hujan"
    };*/



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


        daftar = (ListView) findViewById(R.id.listCuaca);
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (hasilActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, jadwalCuaca);
        daftar.setAdapter(adapter);
        daftar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int arrayke, long id) {
                //Toast.makeText(MainActivity.this, "Kamu memilih :"+bahasa[arrayke], Toast.LENGTH_LONG).show();

            }
        });*/

        Intent intent = getIntent();
        String city = intent.getStringExtra("Kota");
        //String cityReplace = System.out.println(city.replaceFirst("\\s++$", ""));
        String cutSpace = city.replace("+", " ");
        TextView location = (TextView)findViewById(R.id.tvHello);
        location.setText(cutSpace.replaceAll("\\s+", " ") + ", " + "ID");

        Log.e("Intent :", city);

        String URL1 = "http://api.openweathermap.org/data/2.5/forecast/weather?q=" + city + ",ID&units=metric&APPID=389a47df45fd35c8a0c42eadb59f01f0";
        new hasilActivity.ReadPlacesFeedTask().execute(URL1);

        DateFormat df = new SimpleDateFormat("MMM d, yyyy");
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
                Log.e("Data result: ",result);
                JSONObject jobj =new JSONObject(result);

                //get forcast list cuaca
                JSONArray listCuaca     = jobj.getJSONArray("list");
                String[] jadwalCuaca    = new String[listCuaca.length()];
                String[] fjadwalCuaca   = new String[listCuaca.length()];
                float temperature       = 0;

                Date date               = null;
                long dateTime           = 0;

                long firstDate          = 0;
                Date firstDateTime      = null;

                String weatherString    = null;
                String firsWeatherString = null;
                float ftemperature       = 0;

                for(int i = 0; i < listCuaca.length(); i++)
                {
                    JSONObject data         = listCuaca.getJSONObject(i);
                    dateTime                = data.getLong("dt");
                    date                    = new Date(dateTime * 1000);
                    temperature             = (float) data.getJSONObject("main").getDouble("temp");

                    weatherString           = data.getJSONArray("weather").getJSONObject(0).getString("main");
                    String icon             = data.getJSONArray("weather").getJSONObject(0).getString("icon");
                    jadwalCuaca[i]          = String.valueOf(date)+" - "+weatherString;

                    //Log.e("RESULT ARRAY", Arrays.toString(strArr));
                    //Log.e("Result date", String.valueOf(date)+" - "+weatherString+" - "+temperature);

                    //Log.e("Result temperature", String.valueOf(temperature));
                    //Log.e("Result weatherString", weatherString);
                    //Log.e("Result icon", icon);


                }

                for(int i = 0; i < 1; i++)
                {
                    JSONObject data         = listCuaca.getJSONObject(i);
                    firstDate               = data.getLong("dt");
                    firstDateTime           = new Date(firstDate * 1000);
                    ftemperature            = (float) data.getJSONObject("main").getDouble("temp");

                    firsWeatherString         = data.getJSONArray("weather").getJSONObject(0).getString("main");
                   // String icon             = data.getJSONArray("weather").getJSONObject(0).getString("icon");
                   // fjadwalCuaca[i]         = String.valueOf(firstDateTime)+" - "+firsWeatherString;
                }

                Log.e("DateF", String.valueOf(firsWeatherString));
                // TextView location = (TextView)findViewById(R.id.tvHello);
                TextView weatherCondtion = (TextView)findViewById(R.id.textView2);
                TextView weatherDate = (TextView)findViewById(R.id.textView3);
                TextView weatherDerajad = (TextView)findViewById(R.id.derajad);

                weatherCondtion.setText(String.valueOf(firsWeatherString));
                weatherDate.setText(String.valueOf(firstDateTime));
                weatherDerajad.setText(String.valueOf(ftemperature)+'C');

                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        (hasilActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, jadwalCuaca);
                daftar.setAdapter(adapter);
                daftar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int arrayke, long id) {
                        //Toast.makeText(MainActivity.this, "Kamu memilih :"+bahasa[arrayke], Toast.LENGTH_LONG).show();

                    }
                });


                //get city, Country
                JSONArray getPlaces = jobj.getJSONArray("city");
                for (int i = 0; i < getPlaces.length(); i++)
                {
                    JSONObject dataPlaces       = getPlaces.getJSONObject(i);

                    String dataCity             = dataPlaces.getJSONArray("Value").getJSONObject(0).getString("name");
                    String flagCountry          = dataPlaces.getString("country");
                    Log.e("Result city :", dataCity);
                    Log.e("result country :", flagCountry);
                }



                JSONArray arr2 = jobj.getJSONArray("weather");
                String icon_url="";
                for (int i = 0; i < arr2.length(); i++)
                {
                    icon_url = arr2.getJSONObject(i).getString("icon");
                    Log.e("icon_url:",icon_url);
                }

                String ImgUrl="http://openweathermap.org/img/w/"+icon_url+".png";
                new DownloadImageTask().execute(ImgUrl);

                String temp=jobj.getJSONObject("main").getString("temp");

                float temp1=Float.valueOf(temp);
                float temp11= (float) (temp1-272.15);

                Log.e("Temp:", String.valueOf(temp11));
                TextView derajad = (TextView)findViewById(R.id.derajad);
                String temperatur = String.valueOf(temp11);
                String DEGREE  = "\u00b0";
                Double temps = Double.parseDouble(temperatur);
                derajad.setText(String.format("%.1f", temps)+ DEGREE + "C");


                JSONArray arr = jobj.getJSONArray("weather");
                for (int i = 0; i < arr.length(); i++)
                {
                    String main = arr.getJSONObject(i).getString("main");
                    Log.e("descROCK",main);
                    String s1=main;
//                    char[] chars = s1.toCharArray();
//                    chars[0] = Character.toUpperCase(chars[0]);
//                    s1= new String(chars);

                    TextView textView2=(TextView)findViewById(R.id.textView2);
                    if (s1.equals("Rain")){
                        textView2.setText("Hujan");
                    }else if (s1.equals("Clear")){
                        textView2.setText("Cerah");
                    }else if (s1.equals("Clouds")){
                        textView2.setText("Berawan");
                    }else if (s1.equals("Thunderstorm")){
                        textView2.setText("Badai Petir");
                    }else if (s1.equals("Haze")){
                        textView2.setText("Berkabut");
                    }else if (s1.equals("Mist")){
                        textView2.setText("Berembun");
                    }else{
                        textView2.setText(s1);
                    }
                }

                String country = jobj.getJSONObject("sys").getString("country");
                String city = jobj.getString("name");

                // String
                Log.e("Negara:", country);
                Log.e("Kota: ", city);

                /*TextView location = (TextView)findViewById(R.id.tvHello);
                location.setText(city + ", " + country);*/


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        protected Bitmap doInBackground(String... urls) {
            try {
                return loadImageFromNetwork(urls[0]);
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap result) {

            ImageView mImageView = (ImageView) findViewById(R.id.imageView);
            mImageView.setImageBitmap(result);

        }

        private Bitmap loadImageFromNetwork(String url)
                throws MalformedURLException, IOException {
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(
                    url).getContent());
            return bitmap;
        }

    }
}
