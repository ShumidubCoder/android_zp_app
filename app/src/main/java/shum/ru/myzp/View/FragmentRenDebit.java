

package shum.ru.myzp.View;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import shum.ru.myzp.Model.SPhelper;
import shum.ru.myzp.R;

import static shum.ru.myzp.Model.SPhelper.getSharedPreference;
import static shum.ru.myzp.Model.SPhelper.putSharedPreference;

public class FragmentRenDebit extends Fragment implements View.OnClickListener {

    int debit;
    final String KEY_DEBIT = "DEBIT";
    Activity mActivity;
    TextView tvDebit;

    Button btn50;
    Button btn10;
    Button btnReset;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment_ren_debit, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        btn50 = getView().findViewById(R.id.FiftyButton);
        btn10 = getView().findViewById(R.id.TenButton);
        btnReset = getView().findViewById(R.id.ResetButton);

        btn50.setOnClickListener(this);
        btn10.setOnClickListener(this);;
        btnReset.setOnClickListener(this);;

        super.onViewCreated(view, savedInstanceState);
        mActivity = getActivity();
        debit =  getSharedPreference(mActivity, KEY_DEBIT, 0);
        tvDebit = getView().findViewById(R.id.tv_debit);
        tvDebit.setText(String.valueOf(debit));

    }




    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }


    //todo присвоить clicker

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.FiftyButton):
                putSharedPreference(mActivity, KEY_DEBIT, debit + 50);
                break;
            case (R.id.TenButton):
                putSharedPreference(mActivity, KEY_DEBIT, debit + 10);
                break;
            case (R.id.ResetButton):
                putSharedPreference(mActivity, KEY_DEBIT, 0);
                break;

        }

        debit =  getSharedPreference(mActivity, KEY_DEBIT, 0);
        tvDebit.setText(String.valueOf(debit));


    }
}
