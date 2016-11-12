package hu.ait.android.sprout;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by zoetiet on 11/12/16.
 */
public interface MintAPI {

    @GET("/api/v1/user/transactions")
    public void getTransactions(Callback<List<Transaction>> response);
}
