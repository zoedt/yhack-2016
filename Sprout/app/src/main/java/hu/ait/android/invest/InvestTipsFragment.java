package hu.ait.android.invest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.ait.android.sprout.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InvestTipsFragment extends Fragment {


    public InvestTipsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invest_tips, container, false);
    }


}
