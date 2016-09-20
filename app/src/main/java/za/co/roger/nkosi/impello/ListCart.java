package za.co.roger.nkosi.impello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ListCart extends AppCompatActivity implements APIController.GetCartCallBackListener{

    private APIController controller;
    private RecyclerView recyclerView;
    private CartAdapter adapter;

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