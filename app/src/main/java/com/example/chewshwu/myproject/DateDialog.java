package com.example.chewshwu.myproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by CHEW SHWU on 5/25/2016.
 */
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    EditText editText;
    final Calendar calendar = Calendar.getInstance();
    public DateDialog(View view){
        editText = (EditText)view;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        calendar.set(year, month, day);
      //  String date = year+"-"+(month+1)+"-"+day;
        editText.setText(simpleDateFormat.format(calendar.getTime()));
    }



}
