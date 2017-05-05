package com.palazzisoft.ligabalonpie.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.search.SearchAuth;
import com.palazzisoft.ligabalonpie.dto.Participante;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.palazzisoft.ligabalonpie.activity.R.id.email;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_SIGNUP = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button = (Button) findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        TextView textSignUp = (TextView) findViewById(R.id.link_signup);
        textSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
    }

    private void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Ingresando...");
        progressDialog.show();

        EditText editText  = (EditText) findViewById(R.id.input_email);
        EditText passText = (EditText) findViewById(R.id.input_password);

        String email = editText.getText().toString();
        String pass  = passText.getText().toString();

        Button button = (Button) findViewById(R.id.btn_login);
        button.setEnabled(false);
        new HttpRequestTask(email,pass).execute();
    }

    private void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login fallido", Toast.LENGTH_LONG).show();
        Button button = (Button) findViewById(R.id.btn_login);
        button.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        EditText editText  = (EditText) findViewById(R.id.input_email);
        EditText passText = (EditText) findViewById(R.id.input_password);

        String email = editText.getText().toString();
        String pass  = passText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editText.setError("El email no es válido");
            valid = false;
        } else {
            editText.setError(null);
        }

        if (pass.isEmpty() || pass.length() < 4 || pass.length() > 10) {
            passText.setError("Contraseña debe ser entre 4 y 10 caracteres");
            valid = false;
        } else {
            passText.setError(null);
        }

        return valid;
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Participante> {

        private String email;
        private String password;

        public HttpRequestTask(final String email, final String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        protected Participante doInBackground(Void... params) {
            final String url = "http://192.168.0.17:8080/login/{email}/{password}";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            Map<String, String> variables = new HashMap<>();
            variables.put("email", email);
            variables.put("password", password);

            ResponseEntity<Participante> response = restTemplate.getForEntity(url, Participante.class, variables);

            if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                Toast.makeText(getBaseContext(), "Usuario Inválido", Toast.LENGTH_LONG).show();
                return null;
            }

            return response.getBody();
        }

        @Override
        protected void onPostExecute(Participante participante) {
            Toast.makeText(getBaseContext(), "Muy bien", Toast.LENGTH_LONG).show();
        }

    }
}
