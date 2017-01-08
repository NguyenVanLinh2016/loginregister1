package dev.linhnv.loginregister1;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DevLinhnv on 12/17/2016.
 */

public class MyFunctions {
    JSONParser jsonParser;
    String loginurl = "http://192.168.1.7:8081/AndroidPhpWebService/index.php";
    String registerurl = "http://192.168.1.7:8081/AndroidPhpWebService/index.php";
    //Khai báo cho view all product
    String allProductUrl = "http://192.168.1.7:8081/AndroidPhpWebService/getallproducts.php";
    //khai bao cho add new product
    String createProductUrl = "http://192.168.1.7:8081/AndroidPhpWebService/createproduct.php";
    //khai bao nhan gia trị tu id de xoa sưa du lieu
    String getProductByIdUrl = "http://192.168.1.7:8081/AndroidPhpWebService/getproductdetail.php";
    //Khai báo để update dữ liệu lên database
    String updateProductUrl = "http://192.168.1.7:8081/AndroidPhpWebService/updateproduct.php";
    //khai báo để xóa data trên database
    String deleteProductUrl = "http://192.168.1.7:8081/AndroidPhpWebService/deleteproduct.php";
    String login_tag = "login";
    String register_tag = "register";
    Context context;
    //ham tao khoi ta doi tuong jsonparser
    public MyFunctions(Context context){
        jsonParser = new JSONParser();
        this.context = context;
    }
    //doc tu shared neu da log neu chua log tra ve false, log roi tra ve true
    public boolean checkLogin()
    {
        SharedPreferences lay=
                context.getSharedPreferences(null,context.MODE_WORLD_READABLE);
        String emaillogined=lay.getString("emaillogined","chualogin");
        if(emaillogined.equals("chualogin"))
            return false;
        else
            return true;
    }

    //lay email da login
    public String getEmail()
    {
        SharedPreferences lay=
                context.getSharedPreferences(null,context.MODE_WORLD_READABLE);
        String emaillogined=lay.getString("emaillogined","chualogin");
        return emaillogined;
    }

    //ghi du lieu lai cho emaillogined thanh "chua login"
    public boolean logOut()
    {
        SharedPreferences ghi=
                context.getSharedPreferences(null,context.MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor editor=ghi.edit();
        editor.putString("emaillogined", "chualogin");
        editor.commit();
        return true;
    }

    //khi da login thi luu lai email lenh shared de biet da log
    public boolean setemaillogin(String email)
    {
        SharedPreferences ghi=
                context.getSharedPreferences(null,context.MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor editor=ghi.edit();
        editor.putString("emaillogined", email);
        editor.commit();
        return true;
    }

    /* nhan du lieu goi ham va tra ve json
		chu y ArrayList va BasicNameValuePair
	*/
    public JSONObject loginUser(String email, String password){
        List<NameValuePair> cacdoiso = new ArrayList<NameValuePair>();
        cacdoiso.add(new BasicNameValuePair("tag", login_tag));
        cacdoiso.add(new BasicNameValuePair("email", email));
        cacdoiso.add(new BasicNameValuePair("password", password));
        //Log.d("emai: ","" + email +" password: "+ password); //kiểm tra xem có lấy được email vs password chưa?
        JSONObject jsonObject = jsonParser.getJSONFromUrl(loginurl, cacdoiso);
        setemaillogin(email); //rán giá trị email lên share để nhớ đã login rồi
        //Log.d("jsonObject","" + jsonObject); //kiểm tra xem có tạo được JSON object chưa?
        return jsonObject;
    }
    /*nhan du lieu dang ki
	 * chu y: tag, register_tag va doi so name
	 */
    public JSONObject registerUser(String name, String email, String password){
        List<NameValuePair> cacdoiso = new ArrayList<NameValuePair>();
        cacdoiso.add(new BasicNameValuePair("tag", register_tag));
        cacdoiso.add(new BasicNameValuePair("name", name));
        cacdoiso.add(new BasicNameValuePair("email", email));
        cacdoiso.add(new BasicNameValuePair("password", password));
        JSONObject jsonObject = jsonParser.getJSONFromUrl(registerurl, cacdoiso);
        return jsonObject;
    }
    //Trả về tất cả dữ liệu từ bảng product
    public JSONObject getAllProduct(){
        //POST khong can doi so nen ta de rong
        List<NameValuePair> cacdoiso = new ArrayList<NameValuePair>();
        JSONObject jsonObject = jsonParser.getJSONFromUrl(allProductUrl, cacdoiso);
        return jsonObject;
    }
    //Tạo hàm thêm sản phẩm mới
    public JSONObject createProduct(String name, String price, String description){
        //tạo các đối số
        List<NameValuePair> cacdoiso = new ArrayList<NameValuePair>();
        cacdoiso.add(new BasicNameValuePair("name", name));
        cacdoiso.add(new BasicNameValuePair("price", price));
        cacdoiso.add(new BasicNameValuePair("description", description));
        JSONObject jsonObject = jsonParser.getJSONFromUrl(createProductUrl, cacdoiso);
        return jsonObject;
    }
    //Trả về dữ liệu từ bảng product theo id
    public JSONObject getProductById(String id){
        List<NameValuePair> cacdoiso = new ArrayList<NameValuePair>();
        cacdoiso.add(new BasicNameValuePair("id", id));
        JSONObject jsonObject = jsonParser.getJSONFromUrl(getProductByIdUrl, cacdoiso);
        return jsonObject;
    }
    //viết hàm nhận các giá trị vào để update dữ liệu lên database
    public JSONObject updateProduct(String id, String name, String price, String descriotion){
        //tạo các đối số
        List<NameValuePair> cacdoiso = new ArrayList<NameValuePair>();
        cacdoiso.add(new BasicNameValuePair("id", id));
        cacdoiso.add(new BasicNameValuePair("name", name));
        cacdoiso.add(new BasicNameValuePair("price", price));
        cacdoiso.add(new BasicNameValuePair("description", descriotion));
        JSONObject jsonObject = jsonParser.getJSONFromUrl(updateProductUrl, cacdoiso);
        return jsonObject;
    }
    //viết hàm xóa data theo id truyền vào
    public JSONObject deleteProduct(String id){
        //Tạo các đối số
        List<NameValuePair> cacdoiso = new ArrayList<NameValuePair>();
        cacdoiso.add(new BasicNameValuePair("id", id));
        JSONObject jsonObject = jsonParser.getJSONFromUrl(deleteProductUrl, cacdoiso);
        return jsonObject;
    }
}






