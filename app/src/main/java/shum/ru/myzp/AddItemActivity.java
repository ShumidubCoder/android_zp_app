package shum.ru.myzp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AddItemActivity extends AppCompatActivity {


    LinearLayout mLinearLayout;

    EditText etForMonth;
    EditText etType;
    EditText etDate;
    EditText etValue;
    EditText etStsDate;
    EditText etStsValue;

    Button btnAddItem;
    Button btnCancel;

    View.OnKeyListener keyListener;


    Validator validator;
    boolean haveErrorFromValidator;

    SQLDB db;


    String userStringForMonth;
    String userStringType;
    String userStringDate;
    String userStringValue;
    String userStringStsDate;
    String userStringStsValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_dialog_layout);




        mLinearLayout = findViewById(R.id.ll_new_dialog);

        etForMonth =  mLinearLayout.findViewById(R.id.et_mounth);
        etType = mLinearLayout.findViewById(R.id.et_type);
        etDate = mLinearLayout.findViewById(R.id.et_date);
        etValue = mLinearLayout.findViewById(R.id.et_value);
        etStsDate = mLinearLayout.findViewById(R.id.et_stsDate);
        etStsValue = mLinearLayout.findViewById(R.id.et_stsValue);

        btnAddItem = mLinearLayout.findViewById(R.id.btn_add_item);
        btnCancel = mLinearLayout.findViewById(R.id.btn_cancel);


//        final String [] MONTH = {"01","02","03","04","05","06","07","08","09","10","11","12"};
//        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MONTH);
//        etForMonth.setAdapter(monthAdapter);




        Intent intent = getIntent();

        if(intent.hasExtra("id")){

            if (intent.hasExtra("formonth")) etForMonth.setText(intent.getStringExtra("formonth"));
            if (intent.hasExtra("type")) etType.setText(intent.getStringExtra("type"));
            if (intent.hasExtra("date")) etDate.setText(intent.getStringExtra("date"));
            if (intent.hasExtra("value")) etValue.setText(intent.getStringExtra("value"));
            if (intent.hasExtra("rendate")) etStsDate.setText(intent.getStringExtra("rendate"));
            if (intent.hasExtra("renvalue")) etStsValue.setText(intent.getStringExtra("renvalue"));




        }






    }




    public void onClickAddButton(View v){


        if (getIntent().hasExtra("id")){
            addItemById(getIntent().getStringExtra("id"));
        } else addItemMainMethod();



    }


    public void onClickCancelButton(View v){

       onBackPressed();

    }

    @Override
    public void onBackPressed() {
        finish(); }



    public void addItemBodyMethod(){

        haveErrorFromValidator = false;

        // set input type
        etForMonth.setInputType(InputType.TYPE_CLASS_NUMBER);
        etType.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        etValue.setInputType(InputType.TYPE_CLASS_NUMBER);
        etStsValue.setInputType(InputType.TYPE_CLASS_NUMBER);
        etStsDate.setInputType(InputType.TYPE_CLASS_NUMBER);
        etDate.setInputType(InputType.TYPE_CLASS_NUMBER);

        etForMonth.setOnKeyListener(keyListener);
        etType.setOnKeyListener(keyListener);
        etDate.setOnKeyListener(keyListener);
        etValue.setOnKeyListener(keyListener);
        etStsDate.setOnKeyListener(keyListener);
        etStsValue.setOnKeyListener(keyListener);


        keyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {



                if (keyCode == KeyEvent.KEYCODE_ENTER){
                    view.requestFocus(view.getNextFocusDownId());
                    return true;
                }

                return false;
            }
        };


        // maybe upper and add list vipadausii
        validator = new Validator();

        if (validator.validateByValues(etForMonth, true, validator.MONTH) == validator.RESULT_ERROR) {
            etForMonth.setError("Should be only number value of month");
            haveErrorFromValidator = true;
        }


        if (validator.validateByValues(etType, validator.TYPE) == validator.RESULT_ERROR){
            etType.setError("Should be only AV or ZP value");
            haveErrorFromValidator = true;
        }


        if (validator.validateForNotNill(etValue) == validator.RESULT_ERROR){
            etValue.setError("Can not be nill");
            haveErrorFromValidator = true;
        } // haveErrorFrom Validator == false   or better cycle by start


        if (!haveErrorFromValidator) {

            userStringForMonth = etForMonth.getText().toString();
            userStringType = etType.getText().toString();
            userStringDate = etDate.getText().toString();
            userStringValue = etValue.getText().toString();
            userStringStsDate = etStsDate.getText().toString();
            userStringStsValue = etStsValue.getText().toString();


        } else Toast.makeText(this, "Enter the text correctly", Toast.LENGTH_LONG);


    }



    public void addItemMainMethod(){

        addItemBodyMethod();

        if (!haveErrorFromValidator) {
            if (db == null) db = new SQLDB(this);
            db.addItem(userStringForMonth, userStringType, userStringValue, userStringDate, userStringStsDate, userStringStsValue);

            Toast.makeText(AddItemActivity.this, "New item added", Toast.LENGTH_SHORT).show();
            setTextAsDefault(etForMonth, etType, etDate, etValue, etStsDate, etStsValue);
        }
    }




    public void addItemById(String id){

        addItemBodyMethod();

        if (!haveErrorFromValidator) {
            if (db == null) db = new SQLDB(this);
            db.addItemByRowID(id, userStringForMonth, userStringType, userStringValue, userStringDate, userStringStsDate, userStringStsValue);
            Message.shortToast(this, "Item have edit");
            startActivity(new Intent(this, MainActivity.class));
        }







    }

    public void setTextAsDefault(EditText ... editTexts){

        for (EditText editText : editTexts) editText.setText("");

    }



}























