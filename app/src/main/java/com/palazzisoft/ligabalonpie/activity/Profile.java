package com.palazzisoft.ligabalonpie.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.palazzisoft.ligabalonpie.dto.Participante;
import com.palazzisoft.ligabalonpie.fragment.DatePickFragment;
import com.palazzisoft.ligabalonpie.preference.ParticipantePreference;
import com.palazzisoft.ligabalonpie.service.ProfileService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class Profile extends AppCompatActivity {

    private static final int REQUEST_SIGNUP = 0;
    private static final String TAG = "Profile";
    public static final String DATE_SEPARATOR = " / ";

    private EditText birthDate;
    private EditText nombre;
    private EditText apellido;
    private EditText email;
    private EditText password;
    private Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Mi Perfil");

        setContentView(R.layout.activity_profile);

        initializeViews();
        fillScreen();
        setEvents();
    }

    private void initializeViews() {
        this.nombre = (EditText) findViewById(R.id.input_name);
        this.apellido = (EditText) findViewById(R.id.input_lastname);
        this.email = (EditText) findViewById(R.id.input_email);
        this.password = (EditText) findViewById(R.id.input_password);
        this.birthDate = (EditText) findViewById(R.id.birthDateId);
        this.updateButton = (Button) findViewById(R.id.btn_updateParticipanteId);
    }

    private void fillScreen() {
        ParticipantePreference preferences = new ParticipantePreference(getApplicationContext());
        Participante participante = preferences.getParticipante();

        this.nombre.setText(participante.getNombre());
        this.apellido.setText(participante.getApellido());
        this.email.setText(participante.getEmail());
        this.password.setText(participante.getPassword());

        if (participante.getFechaNacimiento() != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(participante.getFechaNacimiento());
            this.birthDate.setText(c.get(DAY_OF_MONTH) + DATE_SEPARATOR + (c.get(MONTH) + 1) + DATE_SEPARATOR + c.get(YEAR));
        }
    }

    private void setEvents() {
        this.birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        this.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateParticipante();
            }
        });
    }

    private void updateParticipante() {
        Log.i(TAG, "Actualizando Participante");

        if (!validate()) {
            Log.i(TAG, "No fue posible el update");
        } else {
            success();
        }
    }

    private void success() {
        try {
            Participante participante = prepareParticipanteToBePersisted();
            ProfileService service = new ProfileService(participante, getResources());
            service.execute();

            Participante participanteACtualizado = callService(service);
            actualizarParticipantePreferences(participanteACtualizado);
            goToDashboard();
        } catch (Exception e) {
            Log.e(TAG, "Error al actualizar el Participante", e);
            this.email.setError("Ocurrió un error al actualizar el Participante");
        }

        Log.i(TAG, "Actualizado");
    }

    @Override
    public void onBackPressed() {
        goToDashboard();
    }

    private Participante prepareParticipanteToBePersisted() {
        Participante participante = new Participante();

        try  {
            String nombreText = nombre.getText().toString();
            String emailText = email.getText().toString();
            String passwordText = password.getText().toString();
            String apellidoText = apellido.getText().toString();

            ParticipantePreference preferences = new ParticipantePreference(getApplicationContext());
            participante.setNombre(nombreText);
            participante.setApellido(apellidoText);
            participante.setPassword(passwordText);
            participante.setEmail(emailText);
            participante.setId(preferences.getParticipante().getId());
            participante.setEstado(preferences.getParticipante().getEstado());

            SimpleDateFormat format = new SimpleDateFormat("dd / MM / yyyy");
            participante.setFechaNacimiento(format.parse(this.birthDate.getText().toString()));
        }
        catch (Exception e) {
            Log.e(TAG, "Error al parsear la fecha");
        }

        return participante;
    }

    private Participante callService(final ProfileService service) throws ExecutionException, InterruptedException {
        if (service.get() == null) {
            throw new IllegalArgumentException();
        }

        return service.get();
    }

    private void actualizarParticipantePreferences(Participante participante) {
        ParticipantePreference preferences = new ParticipantePreference(getApplicationContext());
        preferences.saveParticipante(participante);
    }

    private void goToDashboard() {
        Intent intent = new Intent(getApplicationContext(), DashboardOptions.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
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

        if (passwordText.isEmpty() || passwordText.length() < 4 || passwordText.length() > 8) {
            password.setError("La contraseña debe ser entre 4 y 8 caracteres");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }

    private void showDatePickerDialog() {
        DatePickFragment newFragment = DatePickFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = day + DATE_SEPARATOR + (month + 1) + DATE_SEPARATOR + year;
                birthDate.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
