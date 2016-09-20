package za.co.roger.nkosi.impello;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Home extends AppCompatActivity implements APIController.HomeCallBackListener{

    private APIController controller;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<ProductsModel> list =  new ArrayList<>();
    private String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        controller = new APIController(this);
        controller.fetchProducts();

        recyclerView = (RecyclerView)findViewById(R.id.products_list);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        adapter = new ProductAdapter(list);
        recyclerView.setAdapter(adapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.viewcart) {
            Intent intent = new Intent(this, ListCart.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFetchStart() {

    }

    @Override
    public void onFetchProgress(ProductsModel model) {
        adapter.populate(model);
    }

    @Override
    public void onFetchProgress(List<ProductsModel> modelList) {

    }

    @Override
    public void onFetchComplete() {

    }

    @Override
    public void onFetchFailed() {

    }


    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Holder>{
        public String TAG = ProductAdapter.class.getSimpleName();
        private List<ProductsModel> productsModelList;

        public ProductAdapter(List<ProductsModel> productsModelList) {
            this.productsModelList = productsModelList;
        }

        public void populate(ProductsModel model){
            productsModelList.add(model);
            notifyDataSetChanged();
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_row, parent, false);
            return new Holder(row);
        }

        @Override
        public void onBindViewHolder(Holder holder, final int position) {
            final ProductsModel model = productsModelList.get(position);
            holder.name.setText(model.pname);
            holder.price.setText(model.pprice);

            Picasso.with(holder.itemView.getContext()).load(Constants.PHOTO_URL + model.pimg).into(holder.image);


            holder.itemView.setSelected(productsModelList.contains(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                int pid = model.pid;
                @Override
                public void onClick(View view) {
                    SharedPreferences preferences = getSharedPreferences("impello_memID", Context.MODE_PRIVATE);
                    String mid = preferences.getString("memID", "");
                    preferences.getString("memID", null);
                    new AddCart().execute(""+pid, mid, "1");
                }
            });
        }


        class AddCart extends AsyncTask<String, String, JSONObject> {

            JSONParser jsonParser = new JSONParser();

            ProgressDialog progressDialog;

            String URL = "http://api.nkosiroger.co.za/impello/";

            private static final String TAG_SUCCESS = "success";
            private static final String TAG_MESSAGE = "message";

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(Home.this);
                progressDialog.setMessage("Updating cart...");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(true);
                progressDialog.show();
            }

            @Override
            protected JSONObject doInBackground(String... strings) {
                try{
                    HashMap<String, String> map = new HashMap<>();
                    map.put("product_id", strings[0]);
                    map.put("user_id", strings[1]);
                    map.put("quantity", strings[2]);
                    map.put("method", "addCart");

                    JSONObject object = jsonParser.makeHttpRequest(URL, "POST", map);

                    if (object != null){
                        Log.d("JSON result", object.toString());
                        return object;
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                super.onPostExecute(jsonObject);
                if (progressDialog != null){
                    progressDialog.dismiss();
                }

                try{
                    response = jsonObject.getString("message");
                    Toast.makeText(Home.this, response, Toast.LENGTH_SHORT).show();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }

        @Override
        public int getItemCount() {
            return productsModelList.size();
        }

        public class Holder extends RecyclerView.ViewHolder{
            public TextView name, price;
            public ImageView image;
            public View parentView;

            public Holder(View itemView) {
                super(itemView);
//                this.parentView = itemView;
                name = (TextView)itemView.findViewById(R.id.c_name);
                price = (TextView)itemView.findViewById(R.id.c_price);
                image = (ImageView)itemView.findViewById(R.id.pimage);
            }
        }
    }
}
