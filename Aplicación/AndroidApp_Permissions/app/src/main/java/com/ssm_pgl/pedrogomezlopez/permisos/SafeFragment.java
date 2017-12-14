package com.ssm_pgl.pedrogomezlopez.permisos;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.EditText;

public class SafeFragment extends AppCompatActivity {

    EditText URL; //navigation url
    WebView webpage; // web browser
    public final String HostingURL="http://172.19.157.73:8080/Mitigation/User.html"; //hostname

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_safe);

        URL = (EditText) findViewById(R.id.URL);
        webpage = (WebView) findViewById(R.id.webView);


        // Enable Javascript
        webpage.getSettings().setJavaScriptEnabled(true);
        // Inject WebAppInterface methods into Web page by having Interface name 'Android'
        webpage.addJavascriptInterface(new WebAppInterface(), "Android");

        // button that click to go to url
        FloatingActionButton buClick = (FloatingActionButton) findViewById(R.id.search);
        //  event to navigate to website
        buClick.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                //check if the API>=23 to display runtime request permission
                if ((int) Build.VERSION.SDK_INT >= 23) {
                    // check if this permission is not grated yet
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE) !=
                            PackageManager.PERMISSION_GRANTED) {
                        //shouldShowRequestPermissionRationale(). This method returns true
                        // if the app has requested this permission previously and the user denied the request.
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {
                            // display request permission
                            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                                    REQUEST_CODE_ASK_PERMISSIONS);
                            return;

                        }

                        return;
                    }
                }
                //load the url that written in edittext to the webview
                LoadURL();
            }
        });
    }

    // Class to be injected in Web page
    public class WebAppInterface {
        //This method return user phone number to the javascript calls from website
        @JavascriptInterface
        public String GetPhoneNumber() {
            // Only send the phone to authorize website
            if(URL.getText().toString().indexOf(HostingURL)==0)
                return GetUserPhoneNumber();//
            else
                return null;
        }
    }

    /* Method for getting the user phone number from the device */
    String GetUserPhoneNumber() {
            TelephonyManager tMgr;
            tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String mPhoneNumber = tMgr.getLine1Number();
            return mPhoneNumber;
    }

    void LoadURL(){
        /*
        // we could enable javascript to be run only in our website
        if(URL.getText().toString().indexOf(HostingURL)==0)
            //Enable Javascript
            webpage.getSettings().setJavaScriptEnabled(true);
        else
            //Disable Javascript
            webpage.getSettings().setJavaScriptEnabled(false);
        //load the url that written in edittext to the webview
        */
        webpage.loadUrl(URL.getText().toString()); }

    //get access to mailbox
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    //request permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // load the url data
                    LoadURL();
                } else {
                    // Permission Denied
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
