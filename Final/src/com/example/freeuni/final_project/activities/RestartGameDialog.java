package com.example.freeuni.final_project.activities;
import java.util.ArrayList;


import android.R.bool;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

public class RestartGameDialog extends DialogFragment{
	
    Context mContext;
    public RestartGameListener listener;
    String[] arr;
    
    interface  RestartGameListener {  
		 void setOnAnswerListener(String answer);  
	}  
    
    public RestartGameDialog() {
		
        mContext = getActivity();
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

    	
    	arr = new String[2];
    	arr[0] = "Play again";
    	arr[1] = "Exit";

    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    	
        builder.setItems(arr, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                	   listener.setOnAnswerListener(arr[which]);
						dismiss();
                   }
               
        });
        return builder.create();

    }
}
