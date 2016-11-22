package alterationstudio.weatherindonesia;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * Created by Dani Simanjuntak on 21/11/2016.
 */
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        EditText idTanggal;
        public DateDialog(View view){
            idTanggal=(EditText)view;
        }

        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar c=Calendar.getInstance();
            int tahun=c.get(Calendar.YEAR);
            int bulan=c.get(Calendar.MONTH);
            int hari=c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this,tahun,bulan,hari);

        }

        public void onDateSet(DatePicker view, int tahun, int bulan, int hari){
            String date=hari+"-"+(bulan+1)+"-"+tahun;
            idTanggal.setText(date);
        }

}
