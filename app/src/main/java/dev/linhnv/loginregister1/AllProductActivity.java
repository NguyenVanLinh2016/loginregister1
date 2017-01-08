package dev.linhnv.loginregister1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllProductActivity extends AppCompatActivity {

    List<Product> list_product;
    ProgressDialog progressDialog;
    ListView lv;
    ProductAdapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);

        lv = (ListView) findViewById(R.id.lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product product = list_product.get(i);
                Intent intent = new Intent(AllProductActivity.this, UpdateProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", product.id+"");
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        list_product = new ArrayList<Product>();
        new LoadAllProducts().execute();
    }
    //viết class nội để lấy toàn bộ dữ liệu đổ lên listview
    class LoadAllProducts extends AsyncTask<String, String, String>{
        MyFunctions myFunctions;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AllProductActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);//co the cancel bang phim back
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String thanhcong = null;
            try{
                myFunctions = new MyFunctions(getApplicationContext());
                JSONObject jsonObject = myFunctions.getAllProduct();
                thanhcong = jsonObject.getString("thanhcong");
                //đọc tất cả dữ liệu từ JSON bỏ vào LIST
                if(Integer.parseInt(thanhcong) == 1){
                    //truy mang ten sanpham trong json
                    JSONArray jsonArray = jsonObject.getJSONArray("sanpham");
                    //duyet mang
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = item.getInt("id");
                        String name = item.getString("name");
                        int price = item.getInt("price");
                        //đưa dữ liệu vào đối tượng
                        Product product = new Product();
                        product.id = id;
                        product.name = name;
                        product.price = price;
                        list_product.add(product);
                    }
                }else{//nếu thất bại là chưa có dữ liệu quay về NewProductActivity để nhập thêm dữ liệu mới
                    startActivity(new Intent(AllProductActivity.this, NewProductActivity.class));
                    finish();
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            productAdapter = new ProductAdapter(AllProductActivity.this, R.layout.list_item, list_product);
            lv.setAdapter(productAdapter);
            super.onPostExecute(s);
        }
    }
}
