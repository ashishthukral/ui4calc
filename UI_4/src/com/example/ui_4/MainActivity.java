package com.example.ui_4;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnRatingBarChangeListener{

	private static final String TAG="ui4";
	private static final int RATING_BAR=R.id.ratingBar1;
	private static final int HEAD_TEXT=R.id.textView1;
	private static final int PREV_BUTTON=R.id.imageButton2;
	private static final int PREV_BUTTON_TEXT=R.id.textView3;

	private static final int NEXT_BUTTON=R.id.imageButton1;
	private static final int NEXT_BUTTON_TEXT=R.id.textView2;

	private static final int EXIT_BUTTON=R.id.button1;
	private static final int RESET_ALL_BUTTON=R.id.button2;


	private static final int EDIT_TEXT1=R.id.editText1;
	private static final int EDIT_TEXT2=R.id.editText2;
	private static final int EDIT_TEXT3=R.id.editText3;
	private static final int RESULT_TEXT=R.id.textView4;


	Map<String,Integer> _map=new HashMap<String,Integer>();


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		_map.put("+", 0);
		// space char put as + interpreted as space on some devices
		_map.put(" ", 0);
		_map.put("-", 1);
		_map.put("/", 2);
		_map.put("*", 3);
		RatingBar aRatingBar = (RatingBar)findViewById(RATING_BAR);
		aRatingBar.setOnRatingBarChangeListener(this);
		aRatingBar.setMax(5);
		aRatingBar.setRating(1);
		aRatingBar.setStepSize(1F);
		registerButtons();
		EditText editText1=(EditText)findViewById(EDIT_TEXT1);
		EditText editText2=(EditText)findViewById(EDIT_TEXT2);
		EditText editText3=(EditText)findViewById(EDIT_TEXT3);
		editText1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL| InputType.TYPE_NUMBER_FLAG_SIGNED);
		editText3.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL| InputType.TYPE_NUMBER_FLAG_SIGNED);
		editText1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)}); 
		editText3.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)}); 

		
		InputFilter filter = new InputFilter() {
			List<String> list=Arrays.asList("+","-","/","*"," ");
			public CharSequence filter(CharSequence source, int start, int end,Spanned dest, int dstart, int dend) { 
				for (int i = start; i < end; i++) { 
					if (!list.contains(source.charAt(i)+"")) { 
						Log.i(TAG, "rej="+source.charAt(i)+","+(int)source.charAt(i));
						return ""; 
					}
					Log.i(TAG, "good="+source.charAt(i)+","+(int)source.charAt(i));
				}
				return null; 
			} 
		};
		editText2.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(1)}); 
		editText2.setInputType(InputType.TYPE_CLASS_TEXT);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void registerButtons(){
		ImageButton previousImageButton=(ImageButton)findViewById(PREV_BUTTON);
		previousImageButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View iV) {
				RatingBar theRatingBar = (RatingBar)findViewById(RATING_BAR);
				int theRatingStep=(int)theRatingBar.getRating();
				theRatingBar.setRating((float)(theRatingStep-1));
			}
		});
		ImageButton nextImageButton=(ImageButton)findViewById(NEXT_BUTTON);
		nextImageButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View iV) {
				RatingBar theRatingBar = (RatingBar)findViewById(RATING_BAR);
				int theRatingStep=(int)theRatingBar.getRating();
				theRatingBar.setRating((float)(theRatingStep+1));
			}
		});

		Button menuButton=(Button)findViewById(EXIT_BUTTON);
		menuButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View iV) {
				finish();
			}
		});
		Button resetAllButton=(Button)findViewById(RESET_ALL_BUTTON);
		resetAllButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View iV) {
				RatingBar theRatingBar = (RatingBar)findViewById(RATING_BAR);
				theRatingBar.setRating(1F);
				EditText editText1=(EditText)findViewById(EDIT_TEXT1);
				EditText editText2=(EditText)findViewById(EDIT_TEXT2);
				EditText editText3=(EditText)findViewById(EDIT_TEXT3);
				TextView resultText=(TextView)findViewById(RESULT_TEXT);
				editText1.setText("");
				editText2.setText("");
				editText3.setText("");
				resultText.setText("");
			}
		});

	}

	private void renderScreen(int iRating){
		TextView theHeadText = (TextView)findViewById(HEAD_TEXT);
		TextView previousImageButtonText=(TextView)findViewById(PREV_BUTTON_TEXT);
		TextView nextImageButtonText=(TextView)findViewById(NEXT_BUTTON_TEXT);
		TextView theCenterText = (TextView)findViewById(RESULT_TEXT);

		renderNavButtonsText(iRating);
		switch(iRating){
		case 1:
			theHeadText.setText("Simple Calculator App");
			previousImageButtonText.setText("");
			nextImageButtonText.setText("Operand 1");
			break;
		case 2:
			theHeadText.setText("Enter Operand 1");
			previousImageButtonText.setText("Main Screen");
			nextImageButtonText.setText("Operation");
			break;
		case 3:
			theHeadText.setText("Choose Operation");
			theCenterText.setText("Permitted Chars: + - / *");
			previousImageButtonText.setText("Operand 1");
			nextImageButtonText.setText("Operand 2");
			break;
		case 4:
			theHeadText.setText("Enter Operand 2");
			previousImageButtonText.setText("Operation");
			nextImageButtonText.setText("Result");
			break;
		case 5:
			theHeadText.setText("Result");
			previousImageButtonText.setText("Operand 2");
			nextImageButtonText.setText("");
			break;
		}
		renderCenterEditText(iRating);
		//		System.out.println("renderScreen="+iRating);
	}

	public void onRatingChanged(RatingBar iRatingBar, float iRating,boolean iFromUser) {
		int theRatingStep=(int)iRating;
		//		System.out.println("onRatingChanged="+theRatingStep);
		if(theRatingStep==0){
			RatingBar aRatingBar = (RatingBar)findViewById(RATING_BAR);
			aRatingBar.setRating(1);
		}else{
			renderScreen(theRatingStep);
		}
	}

	private void renderCenterEditText(int iRating){

		EditText editText1=(EditText)findViewById(EDIT_TEXT1);
		EditText editText2=(EditText)findViewById(EDIT_TEXT2);
		EditText editText3=(EditText)findViewById(EDIT_TEXT3);
		TextView resultText=(TextView)findViewById(RESULT_TEXT);

		switch(iRating){
		case 1:
			editText1.setVisibility(View.INVISIBLE);
			editText2.setVisibility(View.INVISIBLE);
			editText3.setVisibility(View.INVISIBLE);
			resultText.setVisibility(View.INVISIBLE);
			break;
		case 2:
			editText1.setVisibility(View.VISIBLE);
			editText2.setVisibility(View.INVISIBLE);
			editText3.setVisibility(View.INVISIBLE);
			resultText.setVisibility(View.INVISIBLE);
			break;
		case 3:
			editText1.setVisibility(View.INVISIBLE);
			editText2.setVisibility(View.VISIBLE);
			editText3.setVisibility(View.INVISIBLE);
			resultText.setVisibility(View.VISIBLE);
			break;
		case 4:
			editText1.setVisibility(View.INVISIBLE);
			editText2.setVisibility(View.INVISIBLE);
			editText3.setVisibility(View.VISIBLE);
			resultText.setVisibility(View.INVISIBLE);
			break;
		case 5:
			editText1.setVisibility(View.INVISIBLE);
			editText2.setVisibility(View.INVISIBLE);
			editText3.setVisibility(View.INVISIBLE);
			resultText.setVisibility(View.VISIBLE);
			calc();
			break;
		}

	}

	private void calc(){
		int ratingScreen=-1;
		EditText editText1=(EditText)findViewById(EDIT_TEXT1);
		EditText editText2=(EditText)findViewById(EDIT_TEXT2);
		EditText editText3=(EditText)findViewById(EDIT_TEXT3);
		TextView resultText=(TextView)findViewById(RESULT_TEXT);
		float op1=0,op2=0;
		Integer operation=null;
		Float result=null;
		try{
			op1=Float.valueOf(editText1.getText().toString());
		}catch(NumberFormatException e){
			Toast.makeText(MainActivity.this, "Enter Operand 1", Toast.LENGTH_LONG).show();
			ratingScreen=2;
		}
		if(ratingScreen==-1){
			operation=_map.get(editText2.getText().toString());
			if(operation==null){
				Toast.makeText(MainActivity.this, "Enter Operation", Toast.LENGTH_LONG).show();
				ratingScreen=3;
			}
		}
		if(ratingScreen==-1){
			try{
				op2=Float.valueOf(editText3.getText().toString());
			}catch(NumberFormatException e){
				Toast.makeText(MainActivity.this, "Enter Operand 2", Toast.LENGTH_LONG).show();
				ratingScreen=4;
			}
		}
		if(ratingScreen==-1){
			result=0F;
			switch(operation){
			case 0:
				result=op1+op2;
				break;
			case 1:
				result=op1-op2;
				break;
			case 2:
				result=op1/op2;
				break;
			case 3:
				result=op1*op2;
				break;
			}
			resultText.setText(result.toString());
		}else{
			RatingBar aRatingBar = (RatingBar)findViewById(RATING_BAR);
			aRatingBar.setRating(ratingScreen);
		}
	}

	private void renderNavButtonsText(int iRating){
		ImageButton previousImageButton=(ImageButton)findViewById(PREV_BUTTON);
		TextView previousImageButtonText=(TextView)findViewById(PREV_BUTTON_TEXT);
		ImageButton nextImageButton=(ImageButton)findViewById(NEXT_BUTTON);
		TextView nextImageButtonText=(TextView)findViewById(NEXT_BUTTON_TEXT);
		if(iRating==1){
			previousImageButton.setVisibility(View.INVISIBLE);
			previousImageButtonText.setVisibility(View.INVISIBLE);
			nextImageButton.setVisibility(View.VISIBLE);
			nextImageButtonText.setVisibility(View.VISIBLE);
		}else if(iRating==5){
			previousImageButton.setVisibility(View.VISIBLE);
			previousImageButtonText.setVisibility(View.VISIBLE);
			nextImageButton.setVisibility(View.INVISIBLE);
			nextImageButtonText.setVisibility(View.INVISIBLE);
		}else{
			previousImageButton.setVisibility(View.VISIBLE);
			previousImageButtonText.setVisibility(View.VISIBLE);
			nextImageButton.setVisibility(View.VISIBLE);
			nextImageButtonText.setVisibility(View.VISIBLE);
		}
	}

}
