package za.co.roger.nkosi.impello;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListCart extends AppCompatActivity implements APIController.GetCartCallBackListener, View.OnClickListener{

    private APIController controller;
    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private List<CartModel> list = new ArrayList<>();
    private Button checkout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cart);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        controller = new APIController(this);
        controller.fetchcart();

        recyclerView = (RecyclerView)findViewById(R.id.get_cart);
        checkout = (Button)findViewById(R.id.checkout);

        checkout.setOnClickListener(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        adapter = new CartAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFetchStart() {

    }

    @Override
    public void onFetchProgress(CartModel model) {
        adapter.populate(model);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }

    public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Holder>{
        private List<CartModel> cartModels;

        public CartAdapter(List<CartModel> cartModels) {
            this.cartModels = cartModels;
        }

        public void populate(CartModel model){
            cartModels.add(model);
            notifyDataSetChanged();

        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row, parent, false);
            return new Holder(row);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            final CartModel model = cartModels.get(position);
            holder.quantity.setText(model.quantity);
            holder.pname.setText(model.pname);
        }

        @Override
        public int getItemCount() {
            return cartModels.size();
        }

        public class Holder extends RecyclerView.ViewHolder {
            public TextView pname, quantity;
            public Holder(View itemView) {
                super(itemView);
                pname = (TextView)itemView.findViewById(R.id.p_name);
                quantity = (TextView)itemView.findViewById(R.id.quantity);
            }
        }
    }
}