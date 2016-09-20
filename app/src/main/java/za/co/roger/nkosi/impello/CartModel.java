package za.co.roger.nkosi.impello;

/**
 * Created by gracepinto on 9/20/16.
 */
public class CartModel {
    String pname, quantity;

    public CartModel(CartBuilder builder) {
        this.pname = builder.p_name;
        this.quantity = builder.p_quantity;
    }

    public static class CartBuilder{
        String p_name, p_quantity;

        public CartBuilder setPName(String name){
            p_name = name;
            return CartBuilder.this;
        }

        public CartBuilder setQuantity(String q){
            p_quantity = q;
            return CartBuilder.this;
        }

        public CartModel buildCart(){
            return new CartModel(CartBuilder.this);
        }
    }
}
