package dev.linhnv.loginregister1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginedActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tv_account;
    Button btn_logout;
    MyFunctions myFunctions;
    //Khai báo sử dụng class SessionManager
    SessionManager sessionManager;
    //khai báo 2 button điều hướng
    Button btn_viewAllProduct, btn_addNewProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logined);

        btn_viewAllProduct = (Button) findViewById(R.id.btn_viewAllProduct);
        btn_addNewProduct = (Button) findViewById(R.id.btn_AddNewProduct);

        btn_viewAllProduct.setOnClickListener(this);
        btn_addNewProduct.setOnClickListener(this);

        tv_account = (TextView) findViewById(R.id.tv_account);
        btn_logout = (Button) findViewById(R.id.btn_logout);
        myFunctions = new MyFunctions(getApplicationContext());
        String s = tv_account.getText().toString();
        tv_account.setText(s+"\n" +myFunctions.getEmail());

        //khai báo sử dụng sessionManager để loagout
        sessionManager = new SessionManager(getApplicationContext());
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myFunctions.logOut();
                sessionManager.setLogin(false); //gọi hàm setLogin rán giá trị là false để quay về chưa login
                startActivity(new Intent(LoginedActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_viewAllProduct:
                startActivity(new Intent(LoginedActivity.this, AllProductActivity.class));
                break;
            case R.id.btn_AddNewProduct:
                startActivity(new Intent(LoginedActivity.this, NewProductActivity.class));
                break;
        }
    }
}
