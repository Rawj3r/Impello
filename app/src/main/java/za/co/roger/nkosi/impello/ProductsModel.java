package za.co.roger.nkosi.impello;

/**
 * Created by gracepinto on 9/15/16.
 */
public class ProductsModel {
    String pname, pprice, pimg;
    int pid;

    public ProductsModel(ProductBuilder builder) {
        pname = builder.ppname;
        pprice = builder.ppprice;
        pimg = builder.ppimg;
        pid = builder.ppid;

    }

    public static class ProductBuilder{
        String ppname, ppprice, ppimg;
        int ppid;

        public ProductBuilder setppName(String name){
            ppname = name;
            return ProductBuilder.this;
        }

        public ProductBuilder setppPrice(String price){
            ppprice = price;
            return ProductBuilder.this;
        }

        public ProductBuilder setppImg(String image){
            ppimg = image;
            return ProductBuilder.this;
        }

        public ProductBuilder setpID(int i){
            ppid = i;
            return ProductBuilder.this;
        }

        public ProductsModel buildProduct(){
            return new ProductsModel(ProductBuilder.this);
        }
    }
}
