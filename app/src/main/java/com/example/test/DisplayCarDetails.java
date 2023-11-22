package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.razorpay.Checkout;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DisplayCarDetails extends AppCompatActivity {

    Button buynow1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_car_details);

        Intent intent = getIntent();

        String carname = intent.getStringExtra("carName");
        String price = intent.getStringExtra("price");
        String rating = intent.getStringExtra("rating");
        int pictureid = intent.getIntExtra("picture",0);

        final int PRICE = Integer.valueOf(price);

        // Get the current date
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);


        TextView priceD = findViewById(R.id.price);
        TextView carnameD = findViewById(R.id.car_name);
        TextView ratingD = findViewById(R.id.rating);
        ImageView carpicD = findViewById(R.id.car_img);

        priceD.setText(price + "₹/Day");
        carnameD.setText(carname);
        ratingD.setText(rating);
        carpicD.setImageResource(pictureid);

        EditText pickupDatePicker = findViewById(R.id.pickupDate);
        EditText returnDatePicker = findViewById(R.id.returnDate);
        TextView amount = findViewById(R.id.amt);

        pickupDatePicker.setText( year + "-" + month + "-" + day);
        returnDatePicker.setText( year + "-" + month + "-" + day);

        amount.setText("" + PRICE);

        pickupDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(pickupDatePicker, year, month-1, day, PRICE, pickupDatePicker, returnDatePicker);
            }
        });

        returnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(returnDatePicker, year, month-1, day, PRICE, pickupDatePicker, returnDatePicker);

            }
        });

        Checkout.preload(getApplicationContext());

        buynow1 = (Button) findViewById(R.id.buynow);

        buynow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makepayment();
            }
        });

    }
    private void showDatePickerDialog(EditText editText, int year, int month, int day, int PRICE,EditText pickupDatePicker,EditText returnDatePicker ) {

        // Create a date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Handle the date selection
                        String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        editText.setText(selectedDate);
                        CalculateAmt(PRICE, pickupDatePicker, returnDatePicker);
                    }
                },
                year,
                month,
                day);

        // Show the date picker dialog
        datePickerDialog.show();

    }

    public int CalculateAmt(int PRICE, EditText pickUp, EditText returnDate) {

        TextView amount = findViewById(R.id.amt);
        TextView daysView = findViewById(R.id.days);

        String day1 = pickUp.getText().toString();
        String day2 = returnDate.getText().toString();

        long days = calculateDays(day1,day2);

        if (days > 0) {
            long amt = days * PRICE;
            daysView.setText(""+days);
            amount.setText(String.valueOf(amt));
        }
        else if(days==0){
            long amt = PRICE;
            daysView.setText(""+days);
            amount.setText(String.valueOf(amt));
        }
        else{
            daysView.setText("X");
            amount.setText("XXXX");
        }

        return (int)days*PRICE;

    }
    long calculateDays(String dateStr1, String dateStr2) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {

            Date date1 = dateFormat.parse(dateStr1);

            Date date2 = dateFormat.parse(dateStr2);

            // Calculate the difference in days
            long differenceInMilliseconds = date2.getTime() - date1.getTime();
            long differenceInDays = TimeUnit.DAYS.convert(differenceInMilliseconds, TimeUnit.MILLISECONDS);

            return differenceInDays;


        } catch (Exception e) {

        }
        return 0;
    }

    private void makepayment () {

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_Q7sRirDMtIydqc");


        checkout.setImage(R.drawable.logo_car);


        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();

            options.put("name", "C-Rent");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "500");//pass amount in currency subunits
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact", "7219727349");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);


            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);

        }

    }
}