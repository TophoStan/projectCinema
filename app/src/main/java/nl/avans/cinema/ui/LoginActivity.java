package nl.avans.cinema.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import nl.avans.cinema.dataacces.ContentViewModel;
import nl.avans.cinema.dataacces.api.calls.AccessTokenRequest;
import nl.avans.cinema.dataacces.api.calls.RequestTokenResult;
import nl.avans.cinema.databinding.ActivityLoginBinding;

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

        mViewModel = new ViewModelProvider(this).get(ContentViewModel.class);

    }

    public void startLoginProcess(){
        mViewModel.generateRequestToken().observe(this, results -> {
            //login(results.getRequest_token());
            String url = "https://www.themoviedb.org/auth/access?request_token=" + results.getRequest_token();
            Log.d(LOG_TAG, results.getRequest_token());

            WebView webview = new WebView(this);

            webview.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    if("https://www.themoviedb.org/auth/access/approve".equals(webview.getUrl())){
                        setContentView(binding.getRoot());
                        postLogin(results.getRequest_token());
                    }
                    super.onProgressChanged(view, newProgress);
                }
            });

            setContentView(webview);
            webview.loadUrl(url);
        });
    }


    public void postLogin(String requestToken){

        mViewModel.generateLogin(requestToken).observe(this, results -> {

            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        });
    }

}