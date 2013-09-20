package com.pierrethelusma.magicnumber;

import android.content.Context;
import android.widget.Toast;

public class MyToast
{
	public static void makeToast(String toast, Context context)
	{
		Toast.makeText(context, toast, Toast.LENGTH_LONG).show();
	}
}