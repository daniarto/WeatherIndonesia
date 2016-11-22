package alterationstudio.weatherindonesia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class hasilActivity extends AppCompatActivity {

    private ListView daftar;
    private String[] jadwalCuaca = new String[]{
            "Rabu, 23 Nov 2016 - Cerah","Kamis, 24 Nov 2016 - Hujan","Jumat, 25 Nov 2016 - Berawan",
            "Sabtu, 26 Nov 2016 - Cerah","Minggu, 27 Nov 2016 - Berawan","Senin, 28 Nov 2016 - Hujan",
            "Selasa, 29 Nov 2016 - Cerah","Rabu, 30 Nov 2016 - Hujan"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);

        daftar = (ListView) findViewById(R.id.listCuaca);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (hasilActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, jadwalCuaca);
        daftar.setAdapter(adapter);
        daftar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int arrayke, long id) {
                //Toast.makeText(MainActivity.this, "Kamu memilih :"+bahasa[arrayke], Toast.LENGTH_LONG).show();

            }
        });
    }
}
