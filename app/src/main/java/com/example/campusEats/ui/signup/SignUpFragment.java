package com.example.campusEats.ui.signup;

import android.app.ProgressDialog;
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
import com.example.campusEats.R;
import com.example.campusEats.ui.login.LoginFragment;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment {

    private SignUpViewModel signUpViewModel;
    //    開始定義輸入框的變數
    TextInputEditText textInputEditText_Fullname, textInputEditText_Username, textInputEditText_Password,textInputEditText_Check_Password, textInputEditText_Email;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar loading;
    String result_Fail;
    ProgressDialog progressDialog;
    Handler handler;

//    private  static String ip_address = "163.18.42.32";
//    private static String URL_SignUp = "https://" + ip_address + "/Code/LogIn-SignUp/signup.php";

        private static String URL_SignUp = "https://163.18.42.31/Hong_code/Android/LogIn-SignUp/signup.php";
//    private static String URL_SignUp = "https://172.20.3.26/code/Android_Studio/LogIn-SignUp/signup.php";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        signUpViewModel =
                ViewModelProviders.of(this).get(SignUpViewModel.class);
        View root = inflater.inflate(R.layout.fragment_signup, container, false);
        textInputEditText_Fullname = root.findViewById(R.id.fullname);
        textInputEditText_Username = root.findViewById(R.id.username);
        textInputEditText_Password = root.findViewById(R.id.password);
        textInputEditText_Check_Password = root.findViewById(R.id.confirm_pwd);
        textInputEditText_Email = root.findViewById(R.id.email);
        buttonSignUp = root.findViewById(R.id.buttonSignUp);
        textViewLogin = root.findViewById(R.id.loginText);
        loading = root.findViewById(R.id.progress);


        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //獲取fragment的實例 (看要去哪個 java檔【DepositFragment】)
                Fragment fragment=new LoginFragment();

                //獲取Fragment的管理器
                FragmentManager fragmentManager=getFragmentManager();

                //開啟fragment的事物,在這個對象裡進行fragment的增刪替換等操作。
                FragmentTransaction ft = fragmentManager.beginTransaction();

                //跳轉到fragment ，第一個參數為所要替換的位置id ，第二個參數是替換後的fragment
                ft.replace(R.id.nav_host_fragment, fragment, "login");

                //提交事物
                ft.commit();
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
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
                        signUp();
                    }
                }, 2000); // 3000 milliseconds delay
            }


        });



        return root;
    }
    public void signUp() {
        //Add HttpsTrustManager.allowAllSSL() before you make a https request:
        HttpsTrustManager.allowAllSSL();


//        loading.setVisibility(View.VISIBLE);
//        buttonSignUp.setVisibility(View.GONE);
        final String name = this.textInputEditText_Fullname.getText().toString().trim();
        final String username = this.textInputEditText_Username.getText().toString().trim();
        final String password = this.textInputEditText_Password.getText().toString().trim();
        final String confirm_pwd = this.textInputEditText_Check_Password.getText().toString().trim();
        final String email = this.textInputEditText_Email.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SignUp,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");

                            if (success.equals("1")) {
//                                loading.setVisibility("GONE");
                                Toast.makeText(getContext(), "註冊成功", Toast.LENGTH_SHORT).show();
                                textInputEditText_Fullname.setText("");
                                textInputEditText_Username.setText("");
                                textInputEditText_Password.setText("");
                                textInputEditText_Check_Password.setText("");
                                textInputEditText_Email.setText("");
                            } else {

                                // 利用 Switch/Case來捕捉message
                                switch (message) {
                                    case ("Empty Data"):
                                        result_Fail = "請確實填寫資料";
                                        break;
                                    case ("This name already exist"):
                                        result_Fail = "這個名字已經被使用過了";
                                        break;
                                    case ("This username already exist"):
                                        result_Fail = "這個帳號已經被使用過了";
                                        break;
                                    case ("This email already exist"):
                                        result_Fail = "這個Email已經被使用過了";
                                        break;
                                    case ("Password no match"):
                                        result_Fail = "請確認密碼是否一致";
                                        break;
                                    case ("Failed to Connect"):
                                        result_Fail = "註冊失敗：資料庫連線失敗";
                                        break;
                                }

                                Toast.makeText(getContext(), result_Fail, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "註冊失敗：" + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            buttonSignUp.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "註冊失敗：" + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        buttonSignUp.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("username", username);
                params.put("password", password);
                params.put("confirm_pwd", confirm_pwd);
                params.put("email", email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }





}