package com.pierrethelusma.magicnumber;

import java.util.Random;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity 
{
	
	private Thread threads[] = new Thread[2];
	private ProgressDialog progDailog;
	private Random random;
	private volatile boolean isComplete; 

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		random = new Random();
		isComplete = false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onStart() 
	{
		super.onStart();
		
		progDailog = ProgressDialog.show(this, "Please wait . . .", "Finding magic number . . .", true);
		
		for(Thread thread : threads)
		{
			thread = new Thread(RandomNumberGenerator);
			thread.setName(Long.toString(thread.getId()));
			thread.start();
		}	
	}
	
	@Override
	public void onStop() 
	{
		super.onStop();
	}
	
	public Runnable RandomNumberGenerator = new Runnable() 
	{
		
		@Override
		public void run() 
		{
		   	try 
		   	{
	   			while(!isComplete)
	   			{
	   				
					Thread.sleep(1000);
					int randomNumber = generateRandomFourDigitNumber();
					Log.i("Message", "Thread "  + Thread.currentThread().getName() + " generated number " + Integer.toString(randomNumber));
					
				   	MyThread myThread  = new MyThread((int)(long)Thread.currentThread().getId(), Thread.currentThread().getName(), randomNumber);
					Message msg = handler.obtainMessage((int)(long)Thread.currentThread().getId(), myThread);
					
					if(!isComplete)
						handler.sendMessage(msg);
	   			
	   			}
			} catch (Exception e) 
			{
				e.printStackTrace();
			}	
			
		}
	};
    
	Handler handler = new Handler() 
	{
		
		public void handleMessage(Message msg) 
		{
			
			MyThread myThread = (MyThread)msg.obj;
			
			if(isMagicNumber(myThread.getRandomNumber()) && !isComplete)
			{
				
				MyToast.makeToast(Integer.toString(myThread.getRandomNumber())  + " is a magic number from thread " + myThread.getName(), getApplicationContext());
				
				threads = null;
				isComplete = true;
				
				progDailog.dismiss();
			
			}
		}
	};
	
	public boolean isMagicNumber(int value)
	{
		return isMultipleOfSeven(value) || isLastDigitTwo(value);
	}
	
	public boolean isMultipleOfSeven(int value)
	{
		return (value % 7 == 0);
	}
	
	public boolean isLastDigitTwo(int value)
	{
		return Integer.toString(value).endsWith("2");
	}
	
	public int generateRandomFourDigitNumber()
	{
		return random.nextInt(9000) + 1000;
	}
	
}
