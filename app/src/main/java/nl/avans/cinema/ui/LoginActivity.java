package nl.avans.cinema.ui;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import android.view.View;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import nl.avans.cinema.R;
import nl.avans.cinema.dataacces.ContentViewModel;
import nl.avans.cinema.databinding.ActivityLoginBinding;
import nl.avans.cinema.domain.Movie;
import nl.avans.cinema.domain.User;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private ContentViewModel mViewModel;
    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.LoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startLoginProcess();
            }
        });

        binding.LoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themoviedb.org/signup")));
            }
        });

        binding.LoginGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.guest_login_message), Toast.LENGTH_SHORT).show();
                startGuestProcess();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

        mViewModel = new ViewModelProvider(this).get(ContentViewModel.class);
        mViewModel.deleteUsers();
    }

    public void startLoginProcess() {
        mViewModel.generateRequestToken().observe(this, results -> {
            //login(results.getRequest_token());
            String url = "https://www.themoviedb.org/auth/access?request_token=" + results.getRequest_token();
            Log.d(LOG_TAG, results.getRequest_token());
            for (int i = 0; i < binding.getRoot().getChildCount(); i++) {
                binding.getRoot().getChildAt(i).setVisibility(View.INVISIBLE);
            }
            binding.webviewLogin.getSettings().setJavaScriptEnabled(true);
            binding.webviewLogin.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            binding.webviewLogin.setVisibility(View.VISIBLE);
            binding.webviewLogin.reload();
            binding.webviewLogin.setBackground(new ColorDrawable(getResources().getColor(R.color.black)));
            binding.webviewLogin.setWebViewClient(new WebViewClient() {
                @Override
                public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                    if ("https://www.themoviedb.org/auth/access/approve".equals(binding.webviewLogin.getUrl())) {
                        setContentView(binding.getRoot());
                        postLogin(results.getRequest_token());
                    }
                    super.doUpdateVisitedHistory(view, url, isReload);

                }
            });

            binding.webviewLogin.loadUrl(url);
        });
    }

    public void postLogin(String requestToken) {

        mViewModel.generateLogin(requestToken).observe(this, results -> {
            User user = new User();
            user.setAccount_id(results.getAccount_id());
            user.setAccess_token(results.getAccess_token());
            user.setGuest(false);
            mViewModel.insertUser(user);
            Toast.makeText(LoginActivity.this, getResources().getString(R.string.user_login_message), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("user", user));
        });
    }

    public void startGuestProcess(){
        mViewModel.generateGuestSession().observe(this, guestResult -> {
            User user = new User();
            user.setGuest(true);
            user.setAccount_id(guestResult.getGuest_session_id());
            mViewModel.insertUser(user);
        });
    }

    @Override
    public void onBackPressed() {}
}