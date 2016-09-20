package za.co.roger.nkosi.impello;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by gracepinto on 9/15/16.
 */
public class APIController {
    private final String TAG = APIController.class.getSimpleName();
    private HomeCallBackListener listener;
    private GetCartCallBackListener getCartCallBackListener;
    private RestManager restManager;

    public APIController(HomeCallBackListener listener) {
        this.listener = listener;
        restManager =  new RestManager();
    }

    public APIController(GetCartCallBackListener listener) {
        this.getCartCallBackListener = listener;
        restManager =  new RestManager();
    }

    public void fetchProducts(){
        HashMap<String, String> map = new HashMap<>();
        map.put("method", "listProducts");
        restManager.getApi().getProducts(map, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Log.e(TAG, "JSON :: " + s);

                try{
                    JSONArray array = new JSONArray(s);
                    for (int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        ProductsModel model = new ProductsModel.ProductBuilder()
                                .setppImg(object.getString("imageUrl"))
                                .setppName(object.getString("productName"))
                                .setppPrice(object.getString("price"))
                                .setpID(object.getInt("productID"))
                                .buildProduct();
                        listener.onFetchProgress(model);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                listener.onFetchComplete();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "Error :: " + error.getMessage());
                listener.onFetchComplete();
            }
        });
    }

    public void fetchcart(){
        HashMap<String, String> map = new HashMap<>();
        map.put("method", "getCart");
        map.put("user_id", "25");
        restManager.getApi().getCart(map, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Log.e(TAG, "JSON :: " + s);
                try {
                    JSONArray array = new JSONArray(s);
                    for (int i =0; i<array.length(); i++){
                        JSONObject jsonObject = array.getJSONObject(i);
                        CartModel model = new CartModel.CartBuilder()
                                .setPName(jsonObject.getString("productName"))
                                .setQuantity(jsonObject.getString("quantity"))
                                .buildCart();
                        getCartCallBackListener.onFetchProgress(model);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "Error :: " + error.getMessage());
                getCartCallBackListener.onFetchComplete();
            }
        });
    }

    public interface GetCartCallBackListener{
        void onFetchStart();
        void onFetchProgress(CartModel model);
        void onFetchProgress(List<CartModel> cartModels);
        void onFetchComplete();
        void onFetchFailed();
    }


    public interface HomeCallBackListener{
        void onFetchStart();
        void onFetchProgress(ProductsModel model);
        void onFetchProgress(List<ProductsModel> modelList);
        void onFetchComplete();
        void onFetchFailed();
    }
}
