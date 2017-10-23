

package shum.ru.myzp.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import shum.ru.myzp.Controller.RVAdapter;
import shum.ru.myzp.Controller.RVAdapterDataMeters;
import shum.ru.myzp.R;

import static shum.ru.myzp.View.FragmentMyZP.myZPItems;

public class FragmentMetersData extends Fragment {


    static RecyclerView rv;
    EditText etDataMeters;

    RecyclerView.LayoutManager rvLayoutManager;
    static RecyclerView.Adapter rvAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_meters_data, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        etDataMeters = getView().findViewById(R.id.etDataMeters);




    }









}
