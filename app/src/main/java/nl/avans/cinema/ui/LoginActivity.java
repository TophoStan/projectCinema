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


import nl.avans.cinema.R;
import nl.avans.cinema.dataacces.ContentViewModel;
import nl.avans.cinema.databinding.ActivityLoginBinding;
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

            WebView webview = new WebView(this);

            webview.reload();
            webview.setBackground(new ColorDrawable(getResources().getColor(R.color.black)));

            webview.setWebViewClient(new WebViewClient() {
                @Override
                public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                    if ("https://www.themoviedb.org/auth/access/approve".equals(webview.getUrl())) {
                        setContentView(binding.getRoot());
                        postLogin(results.getRequest_token());
                    }
                    super.doUpdateVisitedHistory(view, url, isReload);
                }
            });

            setContentView(webview);
            webview.loadUrl(url);
        });
    }

    public void postLogin(String requestToken) {

        mViewModel.generateLogin(requestToken).observe(this, results -> {
            User user = new User();
            user.setAccount_id(results.getAccount_id());
            user.setAccess_token(results.getAccess_token());
            mViewModel.insertUser(user);
            startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("user", user));
        });
    }

}