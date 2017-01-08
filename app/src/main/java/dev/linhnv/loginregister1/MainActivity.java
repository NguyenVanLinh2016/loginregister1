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
import android.widget.Toast;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText et_email, et_password;
    Button btn_login, btn_register;
    MyFunctions myFunctions;
    //Khai báo sử dụng class SessionManager
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_email = (EditText) findViewById(R.id.et_login_email);
        et_password = (EditText) findViewById(R.id.et_login_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);

        //sử dụng Session manager
        sessionManager = new SessionManager(getApplicationContext());
        // Check if user is already logged in or not
        if (sessionManager.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(MainActivity.this, LoginedActivity.class);
            startActivity(intent);
            finish();
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email.getText().toString();
                String pass = et_password.getText().toString();
                new Thucthilogin().execute(email, pass);
                sessionManager.setLogin(true); //rán giá trị vào hàm setLogin = true để biết đã login rồi
            }
        });
        btn_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                finish();
                break;
        }
    }
    class Thucthilogin extends AsyncTask<String, String, String>{
        String email;
        String password;
        @Override
        protected String doInBackground(String... strings) {
            String thanhcong = null;
            email = strings[0];
            password = strings[1];
            try{
                myFunctions = new MyFunctions(getApplicationContext());
                JSONObject jo = myFunctions.loginUser(email, password);
                thanhcong = jo.getString("thanhcong");
            }catch(Exception e){
                Log.d("loi", "khong tao duoc json " +e.toString());
            }
            return thanhcong;
        }
        @Override
        protected void onPostExecute(String thanhcong) {
            super.onPostExecute(thanhcong);
            if(Integer.parseInt(thanhcong)==1){ //đăng nhập thành công
                myFunctions.setemaillogin(email); //lưu lại email
                startActivity(new Intent(MainActivity.this, LoginedActivity.class));
                finish();
            }else{ //đăng nhập thất bại
                Toast.makeText(MainActivity.this, "Khong dang nhap duoc, email or pass incorrect", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
