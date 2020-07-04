package com.yassinetaboubi.android.magazines;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;


import com.yassinetaboubi.android.magazines.R;

import static com.yassinetaboubi.android.magazines.Constants.The_Boy_Who_Cried_Wolf;
import static com.yassinetaboubi.android.magazines.Constants.The_Golden_Egg;
import static com.yassinetaboubi.android.magazines.Constants.The_Midas_Touch;
import static com.yassinetaboubi.android.magazines.Constants.The_Miser_And_His_Gold;

public class DescriptionActivity extends AppCompatActivity {

    private static final String TAG = "DescriptionActivity";
    private Context mContext;
    private Bundle extras;
    private WebView webView;
    private WebView webView1;
    private Button show;
    private String url1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        mContext = DescriptionActivity.this;

        show = (Button) findViewById(R.id.show);
        webView = (WebView) findViewById(R.id.simpleWebView);
        webView1 = (WebView) findViewById(R.id.translatedsimpleWebView);

        extras = getIntent().getExtras();
        if(!extras.equals(null)){

            final String data = extras.getString("titles");

            Log.d(TAG, "coming data " + data );

            String url = "file:///android_asset/"+data+"_t"+".html";
            webView.loadUrl(url);


            show.setOnClickListener(new View.OnClickListener() {
                boolean visible;
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View view) {

                    if (data.equals(The_Boy_Who_Cried_Wolf)) {
                        url1 = "file:///android_asset/The_Boy_Who_Cried_Wolf.html";
                    }
                    else if(data.equals(The_Midas_Touch)){
                        url1 = "file:///android_asset/The_Midas_Touch.html";
                    }
                    else if(data.equals(The_Golden_Egg)){
                        url1 = "file:///android_asset/The_Golden_Egg.html";
                    }
                    else  if(data.equals(The_Miser_And_His_Gold)){
                        url1 = "file:///android_asset/The_Miser_And_His_Gold.html";
                    }
                        webView1.loadUrl(url1);
                        ViewGroup viewGroup = findViewById(R.id.storyDescription);
                        TransitionManager.beginDelayedTransition(viewGroup);
                        visible = !visible;
                        webView1.setVisibility(visible ? View.VISIBLE : View.GONE);

                        WebSettings webSettings1 = webView1.getSettings();
                        webSettings1.setBuiltInZoomControls(true);
                        webSettings1.setDisplayZoomControls(false);
                        webSettings1.setJavaScriptEnabled(true);
                    }
            });

            WebSettings webSettings = webView.getSettings();
            webSettings.setBuiltInZoomControls(true);
            webSettings.setDisplayZoomControls(false);
            webSettings.setJavaScriptEnabled(true);
        }
    }
}
