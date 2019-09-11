package com.example.android.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCream = findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolate = findViewById(R.id.chocolate_checkbox);
        EditText name = findViewById(R.id.name_edit_text);
        boolean hasWhippedCream = whippedCream.isChecked();
        boolean hasChocolate = chocolate.isChecked();
        String customerName = name.getText().toString();
        int total = calculateOrderPrice(quantity,hasWhippedCream,hasChocolate);
        createOrderSummary(total, hasWhippedCream, hasChocolate, customerName);
    }


    /**
     * Increments the value of number of coffee up to 3
     */
    public void increment(View view){
        quantity++;
        displayQuantity(quantity);
    }

    /**
     * Decrement the value of number of coffee to 1
     */
    public void decrement(View view){
        if(quantity >= 2){
            quantity--;
            displayQuantity(quantity);
        }
        else{
            Toast lowCoffeeMessage = Toast.makeText(this, R.string.low_coffee,Toast.LENGTH_SHORT);
            lowCoffeeMessage.show();
        }
    }

    /**
     * Change the text on the price of the coffee

     */
    private void displayMessage(String message){
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        String quantity = Integer.toString(number);
        quantityTextView.setText(quantity);
    }

    /**
     * Calculate the total price of the order based off the number of coffees
     * @param quantity numbers of coffees ordered
     */
    private int calculateOrderPrice(int quantity, boolean hasWhippedCream, boolean hasChocolate){
        int baseCoffeePrice = 5;
        if(hasWhippedCream){
            baseCoffeePrice += 1;
        }
        if(hasChocolate){
            baseCoffeePrice += 2;
        }
        return baseCoffeePrice*quantity;
    }

    /**
     * displays complete order summary to the screen
     * @param totalPrice final price of order
     */
    private void createOrderSummary(int totalPrice, boolean hasWhippedCream, boolean hasChocolate, String customerName){
        String orderSummary = "Name: " + customerName +
                                "\nAdd Whipped Cream? "+ hasWhippedCream +
                                "\nAdd Chocolate? " +  hasChocolate +
                                "\nQuantity: " + quantity +
                                "\nTotal: $" + totalPrice +
                                "\nThank you!";
        displayMessage(orderSummary);
        emailOrder(orderSummary);
    }

    private void emailOrder(String message){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","cortez.mccrary.codes@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order");
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        }

    }
}
