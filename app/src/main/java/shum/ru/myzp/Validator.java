package shum.ru.myzp;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;

import static java.security.AccessController.getContext;


/**
 * Created by user on 10/10/17.
 */

public class Validator {


    final String [] MONTH = {"01","02","03","04","05","06","07","08","09","10","11","12"};
    final String [] TYPE = {"AV", "ZP"};

    final int RESULT_OK = 5;
    final int RESULT_ERROR = 7;

    boolean textIsValide;


    public int validateByValues(EditText et, String... equalValues) {

        this.textIsValide = false;

        for (int i = 0; i < equalValues.length; i++) {
            if (et.getText().toString().equals(equalValues[i])) {
                return RESULT_OK;
            }
        }
        return RESULT_ERROR;
    }


    public int validateByValues(EditText et, boolean asInteger, String... equalValues) {

        this.textIsValide = false;

        for (int i = 0; i < equalValues.length; i++) {
            String expected = et.getText().toString();
            if (expected.equals(equalValues[i])) {
                return RESULT_OK;
            }
        }
        return RESULT_ERROR;
    }


    public int validateForNotNill(EditText et){
        if (!et.getText().toString().isEmpty()) return RESULT_OK;
        else return RESULT_ERROR;
    }

    public void defaultValidator(){ }
}









//
//
//
//            if ( textIsValide == false & et.getText().toString() != equalValues[i]) {
//                    this.textIsValide = false;
//            } else if (textIsValide == true | et.getText().toString() == equalValues[i] ) {
//                    this.textIsValide = true;
//                }
//            }
//
//
//
//
//
//            if (this.textIsValide == false) {
//                return RESULT_ERROR;
//            }
//
//
//        } else if (this.textIsValide == true) return RESULT_OK;
//
//
//
//
//
//
//
//        return RESULT_OK;
//
//
//    }
//
//}
//







//
//                Validator validator = new Validator();
//
//                String [] month = {"01","02","03","04","05","06","07","08","09","10","11","12"};
//                if (validator.validate(etForMonth, "The value is not the number value of month", month) == false) {
//                    etForMonth.requestFocus();
//                    etForMonth.setError("The value is not the number value of month");
//                    allItemsIsValid = false;
//                }
//                if (validator.validate(etType, "The value can only been as AV or ZP", "AV", "ZP") == false){
//                    etType.requestFocus();
//                    etType.setError("The value can only been as AV or ZP");
//                    allItemsIsValid = false;
//                }



//                if (allItemsIsValid == true) {
//                    mydb.addItem(userStringForMonth, userStringType, userStringValue, userStringDate, userStringStsDate, userStringStsValue);
//                    initializeData();
//                    initializeAdapter();
//                    Toast.makeText(MainActivity.this, "All is valid", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(MainActivity.this, "Something is not valid", Toast.LENGTH_LONG).show();
//                }


