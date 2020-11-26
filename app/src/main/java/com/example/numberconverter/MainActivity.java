
package com.example.numberconverter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.savedstate.SavedStateRegistryOwner;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {
    int from= 10;
    int to = 10;
    EditText input;
    TextView output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.input_text);
        output = findViewById(R.id.result);
        input.addTextChangedListener(textWatcher);
    }
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String string = s.toString();
            //checking if valid input first
            if(checkIfValidInput(string)) {
                if (string.length() == 0)
                {
                    // if there's no number typed in keep printing the default text
                    output.setText("the result");
                }
                else {
                    // if number string has changed and is not null then convert to correct base
                    output.setText(convert(string));
                }
            }
            else {
                output.setText("Invalid Input");
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            //nothing
        }
    };
    //"from" radiobutton group on click event
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which "from" radio button was clicked
        switch(view.getId()) {
            case R.id.from_bin:
                if (checked) {
                    from = 2;
                    input.setInputType(2);
                }

                    break;
            case R.id.from_oct:
                if (checked) {
                    from = 8;
                    input.setInputType(2);
                }
                    break;
            case R.id.from_dec:
                if (checked) {
                    from = 10;
                    input.setInputType(2);
                }
                    break;
            case R.id.from_hex:
                if (checked) {
                    // change input type to support letters and not just numbers
                    input.setInputType(1);
                   from = 16;
                }

                    break;
        }
        // if the "from" radio button has changed must update the result
        String s = input.getText().toString();
        if(checkIfValidInput(s)) {
            if (s.length() == 0)
            {
                output.setText("the result");
            }
            else {
                output.setText(convert(s));
            }
        }
        else {
            output.setText("Invalid Input");
        }

    }
    //"to" radiobutton group on click event
    public void onSecondRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {

            case R.id.to_bin:
                if (checked) {
                   to = 2;
                }

                    break;
            case R.id.to_oct:
                if (checked) {
                  to = 8;

                }
                    break;
            case R.id.to_dec:
                if (checked) {
                  to = 10;
                }

                    break;
            case R.id.to_hex:
                if (checked) {
                  to = 16;
                }

                    break;
        }
        // if the "to"" radio button has changed must update the result
        String s = input.getText().toString();
        if(checkIfValidInput(s)) {
            if (s.length() == 0)
            {
                output.setText("the result");
            }
            else {
                output.setText(convert(s));
            }
        }
        else {
            output.setText("Invalid Input");
        }
    }
    //checking for valid input
    public boolean checkIfValidInput (String num) {
        boolean valid = true;
        int n = num.length();
        if (n > 0) {
        int numero = 0;
        if (from == 16) {
            // if any unallowed character has been typed in then input isn't valid
            for (int i = 0; i < n; i++) {
                if(num.charAt(i) != 'A' &&
                        num.charAt(i) != 'a' &&
                        num.charAt(i) != 'B' &&
                        num.charAt(i) != 'b' &&
                        num.charAt(i) != 'C' &&
                        num.charAt(i) != 'c' &&
                        num.charAt(i) != 'D' &&
                        num.charAt(i) != 'd' &&
                        num.charAt(i) != 'E' &&
                        num.charAt(i) != 'e' &&
                        num.charAt(i) != 'F' &&
                        num.charAt(i) != 'f' &&
                        num.charAt(i) != '0' &&
                        num.charAt(i) != '1' &&
                        num.charAt(i) != '2' &&
                        num.charAt(i) != '3' &&
                        num.charAt(i) != '4' &&
                        num.charAt(i) != '5' &&
                        num.charAt(i) != '6' &&
                        num.charAt(i) != '7' &&
                        num.charAt(i) != '8' &&
                        num.charAt(i) != '9')
                {
                    valid = false;
                }
            }
        }
        else {
            for (int i = 0; i < n; i++) {
                // check if each character in the number string isn't superior to the "from" numeral system number minus 1
                numero = Character.getNumericValue(num.charAt(i));
                if (numero > (from -1)) {
                    valid = false;
                }
            }
        }

    }
        return valid;
    }

    //converting
    public String convert (String num) {
        // if the string number isn't null
        if (num.length() > 0) {
           try {
               // take the input number and convert it to numeral system 10
               long number = Long.parseLong(num, from);
            String result = String.valueOf(number);
            switch (to) {
                // then either leave the number in the numeral system 10 or convert it to one of the following numeral systems
                case 2:
                    result = Long.toBinaryString(number);
                    break;
                case 8:
                    result = Long.toOctalString(number);
                    break;
                case 16:
                    result = Long.toHexString(number);
                    break;
            }
            return result;} catch (NumberFormatException e) {
               // if the number is too long the parseLong method throws an exception
               return "the number is too long";
           }
        }
        else {
            // if the string number is empty return it as is
            return num;
        }
    }

}
