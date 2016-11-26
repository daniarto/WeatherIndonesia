package alterationstudio.weatherindonesia;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dharmaputera on 11/27/2016.
 */

public class CustomListAdapter extends ArrayAdapter<String> {
    private final Activity activity;
    private final List<String> sTanggal;
    private final List<String> sMain;
    private final List<String>sTemp;
    private final List<String>sPagi;
    private final List<String>sSiang;
    private final List<String>sSore;
    private final List<String>sMalam;


    public CustomListAdapter(Activity activity, List<String>sTanggal, List<String>sMain, List<String>sTemp, List<String>sPagi, List<String>sSiang,
                             List<String>sSore, List<String>sMalam){
        super(activity, R.layout.list_item, sTemp);

        this.activity = activity;
        this.sTanggal = sTanggal;
        this.sMain = sMain;
        this.sTemp = sTemp;
        this.sPagi = sPagi;
        this.sSiang = sSiang;
        this.sSore = sSore;
        this.sMalam = sMalam;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item, null, true);

        String DEGREE  = "\u00b0";

        TextView ttanggal = (TextView)rowView.findViewById(R.id.date);
        TextView tmain = (TextView)rowView.findViewById(R.id.weather);
        TextView ttemp = (TextView)rowView.findViewById(R.id.degree);
        TextView tpagi = (TextView)rowView.findViewById(R.id.pagi);
        TextView tsiang = (TextView)rowView.findViewById(R.id.siang);
        TextView tsore = (TextView)rowView.findViewById(R.id.sore);
        TextView tmalam = (TextView)rowView.findViewById(R.id.malam);

        if (sMain.get(position).equals("Rain")){
            tmain.setText("Hujan");
        }else if (sMain.get(position).equals("Clouds")){
            tmain.setText("Berawan");
        }else if (sMain.get(position).equals("Clear")){
            tmain.setText("Cerah");
        }else if (sMain.get(position).equals("Haze")){
            tmain.setText("Berkabut");
        }else if (sMain.get(position).equals("Mist")){
            tmain.setText("Berembun");
        }else {
            tmain.setText(sMain.get(position));
        }

        ttanggal.setText(sTanggal.get(position));
        ttemp.setText(sTemp.get(position)+ DEGREE + "C");
        tpagi.setText(sPagi.get(position)+ DEGREE + "C");
        tsiang.setText(sSiang.get(position)+ DEGREE + "C");
        tsore.setText(sSore.get(position)+ DEGREE + "C");
        tmalam.setText(sMalam.get(position)+ DEGREE + "C");
        return rowView;
    }
}
