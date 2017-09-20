/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 *
 */

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
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

        EditText userNameEditText = (EditText) findViewById(R.id.user_name_edittext);
        String userName = userNameEditText.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean whippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean chocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(whippedCream, chocolate);
        String priceMessage = createOrderSummary(userName, price, whippedCream, chocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + userName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOne) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOne);
    }


    /**
     * This method is called when the plus(+) button is clicked.
     */
    public void increment(View view){

        if (quantity == 10) {

            Toast.makeText(this, "You can't have more than 10 cup's of coffee!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus(-) button is clicked.
     */
    public void decrement(View view){

        if (quantity == 1) {

            Toast.makeText(this, "You can't have less than 1 cup of coffee!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }

    /**
     * Calculates the price of the order.
     *
     * @param whippedCream is true, if the user checked it, otherwise it is false
     * @param chocolate is true, if the user checked it, otherwise it is false
     * @return an integer value with the price of the bill
     */
    private int calculatePrice(boolean whippedCream, boolean chocolate) {

        int basePrice = 5;

        if (whippedCream) {

            basePrice += 1;
        }

        if (chocolate) {

            basePrice += 2;
        }

        return (quantity * basePrice);
    }


    private String createOrderSummary(String userInput, int price, boolean addWhippedCream, boolean choco){

        String completeText = "Name: " + userInput;
        completeText += "\nAdd whipped cream for $1? " + addWhippedCream;
        completeText += "\nAdd chocolate for $2? " + choco;
        completeText += "\nQuantity: " + quantity;
        completeText += "\nTotal: $" + price;
        completeText += "\nThank you!";

        return completeText;
    }


}//end of class