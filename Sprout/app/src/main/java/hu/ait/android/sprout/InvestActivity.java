package hu.ait.android.sprout;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import hu.ait.android.invest.InvestTipsFragment;
import hu.ait.android.invest.StocksFragment;

public class InvestActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private TextView mBalance;
    private int balance;
    private TextView text_view_invest_tab_1;
    private TextView text_view_invest_tab_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invest);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            balance = extras.getInt("KEY_BALANCE");
        }

        TextView balanceView = (TextView) findViewById(R.id.text_view_invest_balance);
        balanceView.setText("Balance: $" + balance);

        double earningsGOOG = (balance*5.3)/200;
        double earningsAAPL = (balance*4.5)/200;

        text_view_invest_tab_1 = (TextView) findViewById(R.id.text_view_invest_tab_1);
        text_view_invest_tab_1.setText("If you invested in GOOG 18 days ago with half your balance, you would have made $" + Double.toString(earningsGOOG)+".");
        text_view_invest_tab_2 = (TextView) findViewById(R.id.text_view_invest_tab_2);
        text_view_invest_tab_2.setText("If you invested in AAPL 18 days ago with half your balance, you would have made $" + Double.toString(earningsAAPL)+".");


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("GOOG"));
        tabLayout.addTab(tabLayout.newTab().setText("AAPL"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        InvestTipsFragment fragobj1 = new InvestTipsFragment();
        StocksFragment fragobj2 = new StocksFragment();
        //fragobj2.setText("If you invested in AAPL five years ago with half your balance, you would have made $" + Double.toString(balance * 4.5) + ".");

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        final PagerAdapter adapter = new hu.ait.android.sprout.PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }
}
