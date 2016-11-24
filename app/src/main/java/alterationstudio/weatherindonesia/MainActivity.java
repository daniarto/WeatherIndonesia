package alterationstudio.weatherindonesia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.annotation.Target;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    Button result;
    String[] kota = {"Aceh","Padang","Jambi","Palembang","Lampung","Jakarta Timur","Jakarta Selatan",
            "Jakarta Pusat","Jakarta Barat","Bekasi","Depok","Tangerang","Bogor","Bandung"};

    AutoCompleteTextView listKota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,kota);
        listKota = (AutoCompleteTextView) findViewById(R.id.idKota);

        listKota.setThreshold(1);
        listKota.setAdapter(adapter);


        result = (Button)findViewById(R.id.buttonCari);
        result.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String kota = listKota.getText().toString().trim();
        kota = kota.replace(" ","+");
        kota = kota.replace(",","%2C");
        if(kota.isEmpty()) {
//            Intent hasil = new Intent(MainActivity.this, hasilActivity.class);//target = nama class
//            startActivity(hasil);

            Toast.makeText(this, "Masukkan Nama Kota", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(MainActivity.this, hasilActivity.class);
            intent.putExtra("Kota", kota);
            startActivity(intent);
        }
    }
}
