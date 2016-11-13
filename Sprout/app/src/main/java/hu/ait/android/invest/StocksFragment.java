package hu.ait.android.invest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hu.ait.android.sprout.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StocksFragment extends Fragment {
     private TextView text_view_invest_tab_2;
     private int balance;

    public StocksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_stocks, container, false);
        //balance = this.getArguments().getInt("BUNDLE_BALANCE", 0);
//        balance = 2;
//        double earnings = balance * 4.5;
//         text_view_invest_tab_2 = (TextView) view.findViewById(R.id.text_view_invest_tab_2);
//        String balanceString = Double.toString(earnings);
//        text_view_invest_tab_2.setText("If you invested in AAPL five years ago with half your balance, you would have made $" + balanceString + ".");

        return view;
    }

    public void setBalance(int balance){
        this.balance = balance;
    }

//    public void setText(String string) {
//        text_view_invest_tab_2.setText(string);
//    }


}
