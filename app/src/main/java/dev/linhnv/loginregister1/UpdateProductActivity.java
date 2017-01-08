package dev.linhnv.loginregister1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarException;

public class UpdateProductActivity extends AppCompatActivity implements View.OnClickListener{
    EditText et_id_update, et_name_update, et_price_update, et_des_update;
    Button btn_update, btn_delete;
    ProgressDialog progressDialog;
    String id;
    List<Product> list_product;
    Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        et_id_update = (EditText) findViewById(R.id.et_id_update);
        et_name_update = (EditText) findViewById(R.id.et_name_update);
        et_price_update = (EditText) findViewById(R.id.et_price_update);
        et_des_update = (EditText) findViewById(R.id.et_description_update);
        btn_update = (Button) findViewById(R.id.btn_updateProduct);
        btn_delete = (Button) findViewById(R.id.btn_deleteProduct);

        btn_update.setOnClickListener(this);
        btn_delete.setOnClickListener(this);

        list_product = new ArrayList<Product>();
        id = getIntent().getExtras().getString("id");

        new LoadProductById().execute(id);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_updateProduct:
                String id = et_id_update.getText().toString();
                String name = et_name_update.getText().toString();
                String price = et_price_update.getText().toString();
                String description = et_des_update.getText().toString();
                new UpdateProductById().execute(id, name, price, description);
                break;
            case R.id.btn_deleteProduct:
                String idDelete = et_id_update.getText().toString();
                new DeleteProductById().execute(idDelete);
                break;
        }
    }

    //Viết class load dữ liệu theo id
    class LoadProductById extends AsyncTask<String, String, String> {
        MyFunctions myFunctions;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(UpdateProductActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);//co the cancel bang phim back
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            String thanhcong = null;
            String pid = strings[0];
            try{
                myFunctions = new MyFunctions(getApplicationContext());
                JSONObject jsonObject = myFunctions.getProductById(id);
                thanhcong = jsonObject.getString("thanhcong");
                //đọc dữ liệu từ JSON bỏ vào LIST
                if(Integer.parseInt(thanhcong) == 1){
                    //truy mang id sanpham trong json
                    JSONArray jsonArray = jsonObject.getJSONArray("sanpham");
                    JSONObject item = jsonArray.getJSONObject(0); //0 vì mình chỉ lấy có 1 giá trị theo id
                    String name = item.getString("name");
                    int price = item.getInt("price");
                    String description = item.getString("description");
                    product = new Product();
                    product.id = Integer.parseInt(pid);
                    product.name = name;
                    product.price = price;
                    product.description = description;
                    list_product.add(product); //đưa giá trị vào class đối tượng để set dữ liệu lên editText
                }else{//nếu thất bại thi ta xem logcat
                    Log.d("error", "Khong lay dc du lieu");
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }
        //Xữ lý khi lấy được dữ liệu
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            et_id_update.setText(String.valueOf(product.id));
            et_id_update.setEnabled(false); //readonly field id
            et_name_update.setText(product.name);
            et_price_update.setText(String.valueOf(product.price));
            et_des_update.setText(product.description);
        }
    }
    //Viết class update dữ liệu lên data
    class UpdateProductById extends AsyncTask<String, String, String>{
        String id, name, price, description;
        MyFunctions myFunctions;
        String thanhcong = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(UpdateProductActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);//co the cancel bang phim back
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            id = strings[0];
            name = strings[1];
            price = strings[2];
            description = strings[3];
            try{
                myFunctions = new MyFunctions(getApplicationContext());
                JSONObject jsonObject = myFunctions.updateProduct(id, name, price, description);
                thanhcong = jsonObject.getString("thanhcong");
            }catch (JSONException e){
                Log.d("loi","khong cap nhat san pham duoc"+ e.toString());
            }
            return thanhcong;
        }

        @Override
        protected void onPostExecute(String thanhcong) {
            super.onPostExecute(thanhcong);
            progressDialog.dismiss();
            if(Integer.parseInt(thanhcong) == 1){
                startActivity(new Intent(UpdateProductActivity.this, AllProductActivity.class));
                finish();
            }else{
                Toast.makeText(UpdateProductActivity.this, "Khong update duoc", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //Viết clas để xóa dữ liệu theo id
    class DeleteProductById extends AsyncTask<String, String, String>{
        String id;
        MyFunctions myFunctions;
        String thanhcong = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(UpdateProductActivity.this);
            progressDialog.setMessage("Loading");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);//có thể cancel bằng phím back
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            id = strings[0];
            try{
                myFunctions = new MyFunctions(getApplicationContext());
                JSONObject jsonObject = myFunctions.deleteProduct(id);
                thanhcong = jsonObject.getString("thanhcong");
            }catch (JSONException e){
                Log.d("loi","khong xoa duoc san pham"+ e.toString());
            }
            return thanhcong;
        }

        @Override
        protected void onPostExecute(String thanhcong) {
            super.onPostExecute(thanhcong);
            progressDialog.dismiss();
            if(Integer.parseInt(thanhcong) == 1){
                startActivity(new Intent(UpdateProductActivity.this, AllProductActivity.class));
                finish();
            }else{
                Toast.makeText(UpdateProductActivity.this, "Khong xoa duoc san pham", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
