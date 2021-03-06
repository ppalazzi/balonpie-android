package com.palazzisoft.ligabalonpie.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.palazzisoft.ligabalonpie.dto.Participante;
import com.palazzisoft.ligabalonpie.fragment.DatePickerFragment;
import com.palazzisoft.ligabalonpie.preference.ParticipantePreference;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import static com.palazzisoft.ligabalonpie.activity.R.id.btn_signup;


public class SignUpActivity extends AppCompatActivity {

    private static final int REQUEST_SIGNUP = 0;

    private TextView tvDisplayDate;
    private DatePicker dpResult;
    private Button btnChangeDate;
    private Button signup;
    private TextView buttonLogin;

    private EditText nombre;
    private EditText apellido;
    private EditText email;
    private EditText password;

    private TextView pickerLabel;

    private int year;
    private int month;
    private int day;

    private static final int DATE_DIALOG_ID = 999;
    private static final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Registro");

        setContentView(R.layout.activity_sign_up);

        iniciarViews();
        addSignUpButtonListener();
        addListenerOnLoginButton();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
        }
        return null;
    }

    private void addListenerOnLoginButton() {
        buttonLogin = (TextView) findViewById(R.id.link_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void iniciarViews() {
        this.nombre = (EditText) findViewById(R.id.input_name);
        this.apellido = (EditText) findViewById(R.id.input_lastname);
        this.email = (EditText) findViewById(R.id.input_email);
        this.password = (EditText) findViewById(R.id.input_password);
        this.pickerLabel = (TextView) findViewById(R.id.picker_text);
        this.pickerLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarCalendario();
            }
        });
    }

    private void mostrarCalendario() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yearS, int monthS, int dayS) {
                day = dayS;
                month = monthS;
                year = yearS;

                final String selectedDate = day + " / " + (month+1) + " / " + year;
                pickerLabel.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");
    }

    private void signup() {
        Log.i(TAG, "Creando Participante");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creando participante...");
        progressDialog.show();

        try {
            executeTask();
        } catch (Exception e) {
            this.nombre.setError("Hubo un error creando el Participante, intentelo nuevamente más tarde");
            Log.e(TAG, "Error al crear el Participante", e);
        }
        finally {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        // no hacer nada si presiona el boton atras
    }

    private void executeTask() throws ExecutionException, InterruptedException {
        HttpRequestTask requestask = new HttpRequestTask(createParticipante());
        requestask.execute();

        Participante participante = requestask.get();
        if (participante != null) {
            saveParticipantePreferece(participante);
            Log.i(TAG, "Participante creado correctamente");
            goToDashboard();
        } else {
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
            dialogo.setTitle("Registro");
            dialogo.setMessage("No se pudo crear el Participante, asegurese de usar un email distinto");
            dialogo.setNeutralButton("Cerrar", null);
            dialogo.show();
            Log.i(TAG, "Error al crear el Participante");
        }
    }

    private void goToDashboard() {
        Intent intent = new Intent(getApplicationContext(), DashboardOptions.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
    }

    private void saveParticipantePreferece(Participante participante) {
        ParticipantePreference preference = new ParticipantePreference(getApplicationContext());
        preference.saveParticipante(participante);
    }

    private void onSignupFailed() {
        Toast.makeText(getBaseContext(), "No fue posible crear la cuenta", Toast.LENGTH_LONG).show();
    }

    private void addSignUpButtonListener() {
        signup = (Button) findViewById(btn_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    private Participante createParticipante() {
        String nombreText = nombre.getText().toString();
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        String apellidoText = apellido.getText().toString();

        Participante participante = new Participante();
        participante.setNombre(nombreText);
        participante.setApellido(apellidoText);
        participante.setPassword(passwordText);
        participante.setEmail(emailText);


        Calendar c = Calendar.getInstance();
        c.set(year, month-1, day);
        participante.setFechaNacimiento(c.getTime());

        return participante;
    }

    private boolean validate() {
        boolean valid = true;

        String nombreText = nombre.getText().toString();
        String emailText = email.getText().toString();
        String apellidoText = apellido.getText().toString();
        String passwordText = password.getText().toString();

        if (nombreText.isEmpty() || nombreText.length() < 3) {
            nombre.setError("El nombre debe tener al menos 3 caracteres");
            valid = false;
        } else {
            nombre.setError(null);
        }

        if (apellidoText.isEmpty() || apellidoText.length() < 3) {
            apellido.setError("El nombre debe tener al menos 3 caracteres");
            valid = false;
        } else {
            apellido.setError(null);
        }

        if (emailText.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.setError("Debes seleccionar un email válido");
            valid = false;
        } else {
            email.setError(null);
        }

        if (passwordText.isEmpty() || passwordText.length() < 4 || passwordText.length() > 10) {
            password.setError("La contraseña debe ser entre 4 y 10 caracteres");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            tvDisplayDate.setText(new StringBuilder().append(day)
                    .append("-").append(month + 1).append("-").append(year)
                    .append(" "));

            // set selected date into datepicker also
            dpResult.init(year, month, day, null);

        }
    };


    private class HttpRequestTask extends AsyncTask<Void, Void, Participante> {

        private Participante participante;

        public HttpRequestTask(final Participante participante) {
            this.participante = participante;
        }

        @Override
        protected Participante doInBackground(Void... params) {
            final String url = getResources().getString(R.string.baseUrl).concat("crearParticipante");

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            try {
                ResponseEntity<Participante> response = restTemplate.postForEntity(url,  participante, Participante.class);
                return response.getBody();
            }
            catch (HttpClientErrorException ex)   {
                if (ex.getStatusCode() != HttpStatus.NOT_ACCEPTABLE) {
                    throw ex;
                }
                return null;
            }
        }

        @Override
        protected void onPostExecute(Participante participante) {
            if (participante != null) {
                Toast.makeText(getBaseContext(), "Muy bien", Toast.LENGTH_LONG).show();
            }
        }

    }
}
