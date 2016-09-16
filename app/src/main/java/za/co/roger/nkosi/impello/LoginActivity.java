package za.co.roger.nkosi.impello;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static EditText username, password;
    private static Button login;
    private static String data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Login().execute();
            }
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

    }


    class Login extends AsyncTask<String, String, JSONObject> {

        String username = LoginActivity.username.getText().toString();
        String password = LoginActivity.password.getText().toString();

        JSONParser jsonParser = new JSONParser();

        ProgressDialog progressDialog;

        String URL = "http://api.nkosiroger.co.za/impello/";

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Authenticating...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("method", "login");
                hashMap.put("username", username);
                hashMap.put("password", password);
                JSONObject object = jsonParser.makeHttpRequest(URL, "POST", hashMap);


                if (object != null){
                    Log.d("JSON result", object.toString());
                    return object;
                }


            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if (progressDialog != null){
                progressDialog.dismiss();
            }

            try{
                data = jsonObject.getString("logged");
                Log.e("data", data);

                if (data.equals("1")){
                    Intent i = new Intent(getApplicationContext(), Home.class);
                    startActivity(i);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
}

