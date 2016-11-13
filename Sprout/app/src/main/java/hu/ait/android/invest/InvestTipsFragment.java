package hu.ait.android.invest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import hu.ait.android.sprout.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InvestTipsFragment extends Fragment {
    private TextView text_view_invest_tab_1;
    private int balance;

    public InvestTipsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invest_tips, container, false);
//         Inflate the layout for this fragment
//        Bundle bundle = getArguments();
//        if (bundle == null) {
//            Log.e("gi", "gi");
//        } else {
//            Log.e("hi", "this is bundle not null" + bundle.getInt("BUNDLE_BALANCE)"));
//        }
//        balance = this.getArguments().getInt("BUNDLE_BALANCE", 0);
//        balance = 2;
//        double earnings =  balance * 5.3;
//        text_view_invest_tab_1 = (TextView) view.findViewById(R.id.text_view_invest_tab_1);
//        String balanceString = Double.toString(earnings);
//        text_view_invest_tab_1.setText("If you invested in GOOG five years ago with half your balance, you would have made $" + balanceString + ".");


        return view;
    }

    public void setBalance(int balance){
        this.balance = balance;
    }

//    public void setText(String string) {
//        text_view_invest_tab_1 = (TextView) getView().findViewById(R.id.text_view_invest_tab_1);
//        text_view_invest_tab_1.setText(string);
//    }


}
