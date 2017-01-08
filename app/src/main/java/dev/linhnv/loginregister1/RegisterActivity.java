package dev.linhnv.loginregister1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    EditText et_name, et_email, et_password;
    Button btn_login, btn_register;
    TextView tv_notice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_name = (EditText)findViewById(R.id.et_register_name);
        et_email = (EditText)findViewById(R.id.et_register_email);
        et_password = (EditText)findViewById(R.id.et_register_password);
        tv_notice = (TextView) findViewById(R.id.tv_notice);
        btn_register = (Button) findViewById(R.id.btn_register_1);
        btn_login = (Button) findViewById(R.id.btn_register_login);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String pass = et_password.getText().toString();
                new ThucthiRegister().execute(name, email, pass);
            }
        });
        //quay về activity để login
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            }
        });
    }
    class ThucthiRegister extends AsyncTask<String,String,String>{
        MyFunctions myFunctions;
        String name;
        String email;
        String password;
        @Override
        protected String doInBackground(String... strings) {
            String thanhcong = null;
            name = strings[0];
            email = strings[1];
            password = strings[2];
            try{
                myFunctions = new MyFunctions(getApplicationContext());
                JSONObject jsonObject = myFunctions.registerUser(name, email, password);
                thanhcong = jsonObject.getString("thanhcong");
            }catch(Exception e){
                Log.d("loi", "Khong tao JSON duoc, khong dang ki dc" +e.toString());
            }
            return thanhcong;
        }

        @Override
        protected void onPostExecute(String thanhcong) {
            super.onPostExecute(thanhcong);
            if(Integer.parseInt(thanhcong)==1){ //dang ky thanh cong
                tv_notice.setText("Dang ki thanh cong với email là: " +email);
            }else{
                tv_notice.setText("Dang ki khong thanh cong hoac email da ton tai");
            }
        }
    }
}
