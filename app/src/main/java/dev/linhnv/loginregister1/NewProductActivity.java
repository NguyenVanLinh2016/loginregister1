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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class NewProductActivity extends AppCompatActivity {
    EditText et_name, et_price, et_description;
    Button btn_insertProduct;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        et_name = (EditText) findViewById(R.id.et_name);
        et_price = (EditText) findViewById(R.id.et_price);
        et_description = (EditText) findViewById(R.id.et_description);
        btn_insertProduct = (Button) findViewById(R.id.btn_insertProduct);
        btn_insertProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_name.getText().toString();
                String price = et_price.getText().toString();
                String des = et_description.getText().toString();
                new CreateProduct().execute(name, price, des);
            }
        });
    }
    class CreateProduct extends AsyncTask<String, String, String>{
        String name, price, description;
        MyFunctions myFunctions;
        String thanhcong = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(NewProductActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);//co the cancel bang phim back
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            name = strings[0];
            price = strings[1];
            description = strings[2];
            try {
                myFunctions = new MyFunctions(getApplicationContext());
                JSONObject jsonObject = myFunctions.createProduct(name, price, description);
                thanhcong = jsonObject.getString("thanhcong");
            } catch (JSONException e) {
                Log.d("loi","khong tao san pham moi duoc"+ e.toString());
            }
            return thanhcong;
        }

        @Override
        protected void onPostExecute(String thanhcong) {
            super.onPostExecute(thanhcong);
            progressDialog.dismiss();
            if(Integer.parseInt(thanhcong)==1){
                startActivity(new Intent(NewProductActivity.this, AllProductActivity.class));
                finish();
            }else{
                Toast.makeText(NewProductActivity.this, "Khong tao duoc", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
