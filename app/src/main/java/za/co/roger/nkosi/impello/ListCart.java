package za.co.roger.nkosi.impello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class ListCart extends AppCompatActivity implements APIController.GetCartCallBackListener{

    private APIController controller;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cart);
        controller = new APIController(this);
        controller.fetchcart();
    }

    @Override
    public void onFetchStart() {

    }

    @Override
    public void onFetchProgress(CartModel model) {

    }

    @Override
    public void onFetchProgress(List<CartModel> cartModels) {

    }

    @Override
    public void onFetchComplete() {

    }

    @Override
    public void onFetchFailed() {

    }
}