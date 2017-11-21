package cn.com.luckytry.interview.ui.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.com.luckytry.interview.R;
import cn.com.luckytry.interview.bean.InterViewUser;
import cn.com.luckytry.interview.ui.main.MainActivity;
import cn.com.luckytry.interview.util.LUtil;

/**
 * 全屏登录
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String USER_NAME_REGEX = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    private static final String PASSWORD_REGEX = "^[a-zA-Z]\\w{5,17}$";
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            // Translucent status bar
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);


        Button btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        Button btnRigster = (Button) findViewById(R.id.btn_rigsiter);
        btnRigster.setOnClickListener(this);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        InterViewUser user = BmobUser.getCurrentUser(InterViewUser.class);
        if(user!=null){
            start();
        }
    }

    private void attemptLoginOrRigster(boolean isLogin) {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            isLogin(isLogin, email, password);

        }
    }

    private boolean isEmailValid(String email) {

        return email.matches(USER_NAME_REGEX);
    }

    private boolean isPasswordValid(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    private void isLogin(final boolean isLogin, String email, String password) {

            final BmobUser user = new BmobUser();
            user.setUsername(email);
            user.setPassword(password);
            if(isLogin){
                user.login(new SaveListener<InterViewUser>() {
                    @Override
                    public void done(InterViewUser o, BmobException e) {
                        if(e== null){
                            start();
                        }else{
                            LUtil.e("登录失败："+e.getErrorCode()+e.getMessage());
                        }
                    }
                });
            }else{
                user.signUp(new SaveListener<InterViewUser>() {
                            @Override
                            public void done(InterViewUser o, BmobException e) {
                                if(e== null){
                                    start();
                                }else{
                                    LUtil.e("注册失败："+e.getErrorCode()+e.getMessage());
                                }
                            }
                        });
            }

//            new Thread(){
//                @Override
//                public void run() {
//                    super.run();
//                    if(isLogin){
//
//                    }else{
//                        user.signUp(new SaveListener<InterViewUser>() {
//                            @Override
//                            public void done(InterViewUser o, BmobException e) {
//                                if(e== null){
//                                    start();
//                                }else{
//                                    LUtil.e("注册失败："+e.getErrorCode()+e.getMessage());
//                                }
//                            }
//                        });
//                    }
//                }
//            }.start();


    }




    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.btn_login){
            attemptLoginOrRigster(true);
        }else if(id == R.id.btn_rigsiter){
            attemptLoginOrRigster(false);
        }

    }
    private void start() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
