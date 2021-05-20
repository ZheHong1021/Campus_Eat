package com.example.campusEats.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.campusEats.HttpsTrustManager;
import com.example.campusEats.MainActivity;
import com.example.campusEats.R;
import com.example.campusEats.ui.signup.SignUpFragment;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {
    //    開始定義輸入框的變數
    TextInputEditText textInputEditText_Username, textInputEditText_Password;
    Button buttonLogin;
    TextView textViewSignUp;
    ProgressBar loading;
    String result_Fail;
    ProgressDialog progressDialog;
    Handler handler;


    static String id;
    static String name;
    static String email;
//    private  static String ip_address = "163.18.42.32";
//    private static String URL_Login = "https://" + ip_address + "/code/LogIn-SignUp/login.php";
    private static String URL_Login = "https://163.18.42.31/Hong_code/Android/LogIn-SignUp/login.php";


//    private static String URL_Login = "https://172.20.3.26/code/Android_Studio/LogIn-SignUp/login.php";

    private LoginViewModel loginViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel =
                ViewModelProviders.of(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login, container, false);


        textInputEditText_Username = root.findViewById(R.id.username);
        textInputEditText_Password = root.findViewById(R.id.password);
        buttonLogin = root.findViewById(R.id.btnLogin);
        textViewSignUp = root.findViewById(R.id.signUpText);
        loading = root.findViewById(R.id.progressBar);

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //獲取fragment的實例 (看要去哪個 java檔【DepositFragment】)
                Fragment fragment=new SignUpFragment();

                //獲取Fragment的管理器
                FragmentManager fragmentManager=getFragmentManager();

                //開啟fragment的事物,在這個對象裡進行fragment的增刪替換等操作。
                FragmentTransaction ft = fragmentManager.beginTransaction();

                //跳轉到fragment ，第一個參數為所要替換的位置id ，第二個參數是替換後的fragment
                ft.replace(R.id.nav_host_fragment, fragment, "signup");

                //提交事物
                ft.commit();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Initialize Progress Dialog
                progressDialog = new ProgressDialog(getContext());

                // Show Dialog
                progressDialog.show();

                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 2000); // 3000 milliseconds delay


                // Set Content View
                progressDialog.setContentView(R.layout.progress_dialog);

                // Set Transparent Background
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );


                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Login();
                    }
                }, 2000); // 3000 milliseconds delay
            }
        });


        return root;
    }

    private void Login(){
        //Add HttpsTrustManager.allowAllSSL() before you make a https request:
        HttpsTrustManager.allowAllSSL();


//        loading.setVisibility(View.VISIBLE);
//        buttonSignUp.setVisibility(View.GONE);
        final String username = this.textInputEditText_Username.getText().toString().trim();
        final String password = this.textInputEditText_Password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");

                            if(success.equals("1")){

                                id = jsonObject.getString("id");
                                name = jsonObject.getString("name");
                                email = jsonObject.getString("email");
                                Toast.makeText(getContext(), "登入成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent( getContext(), MainActivity.class);
                                intent.putExtra("is_Login", true);

                                intent.putExtra("id", id);
                                intent.putExtra("name", name);
                                intent.putExtra("email", email);
                                startActivity(intent);

                            }else{

                                // 利用 Switch/Case來捕捉message
                                switch (message){
                                    case ("Empty Data"):
                                        result_Fail = "請確實填寫資料";
                                        break;
                                    case ("Failed to Connect"):
                                        result_Fail = "登入失敗：資料庫連線失敗";
                                        break;
                                    case ("Input Error"):
                                        result_Fail = "帳號或密碼有誤";
                                        break;

                                }

                                Toast.makeText(getContext(), result_Fail, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "登入失敗：" + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            buttonLogin.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "登入失敗：" + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        buttonLogin.setVisibility(View.VISIBLE);
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username" ,username);
                params.put("password", password);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }




}