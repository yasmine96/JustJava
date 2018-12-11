package com.example.yasmine.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

   
     //This is method returns order summary in a message.
    public String createOrderSummary(int quantity , boolean checkBox , boolean checkBox2 , Editable edit , int price){
       String message = "Name: " + edit + "\nQuantity: " + quantity;
       message = message + "\nAdd Whipped Cream? " + checkBox ;
       message = message + "\nAdd Chocolate? " + checkBox2 ;
       message = message + "\nTotal: $ " + price ;
       message = message + "\nThank You!";
       return  message ;
    }

    
    
    int quantity = 0;

    int price = 0 ;

    //This method is called when the order button is clicked.
    public void submitOrder(View view) {

        CheckBox whippedCream = (CheckBox) findViewById(R.id.check_button);
       boolean isWhippedCream = whippedCream.isChecked();

        CheckBox chocolate = (CheckBox) findViewById(R.id.check_button2);
        boolean hasChocolate = chocolate.isChecked();

        EditText editText = (EditText) findViewById(R.id.edit_text);
        Editable edit = editText.getText();

        if (isWhippedCream && hasChocolate){

            price = (quantity * 8) ;
         }

         else if(isWhippedCream){

             price = (quantity * 6) ;
         }
         else if(hasChocolate){

             price = (quantity * 7) ;
         }


        else {

            price = (quantity * 5) ;
        }

       String message = createOrderSummary(quantity ,isWhippedCream , hasChocolate , edit ,price);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java for " + edit);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
       displayMessage(message);
    }

    
    //increment for quantities of coffees
    public void increment(View view) {
         quantity = quantity +1;
        display(quantity);
    }
    
    
    //decrement for quanities of coffees
    public void decrement(View view) {
       if (quantity <= 1){
           quantity = 1;
           Context context = getApplicationContext();
           CharSequence text= "You cannot order less than 1 coffee";
           int duration =Toast.LENGTH_SHORT;
           Toast toast = Toast.makeText(context , text , duration);
            toast.show();
             return;
       }

        quantity = quantity - 1;

        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity);
        quantityTextView.setText("" + number);
    }

    private void displayMessage(String message) {
        TextView ordersummaryTextView = (TextView) findViewById(R.id.order_summary);
        ordersummaryTextView.setText(message);
    }
}
