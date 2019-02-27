package com.palazzisoft.ligabalonpie.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.palazzisoft.ligabalonpie.dto.Equipo;
import com.palazzisoft.ligabalonpie.dto.Participante;
import com.palazzisoft.ligabalonpie.service.ParticipantePorEmailService;
import com.palazzisoft.ligabalonpie.service.ProfileService;

import org.w3c.dom.Text;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;


public class ParticipanteAdmin extends AppCompatActivity {

    private static final String TAG = "ParticipanteAdmin";

    private Spinner spinner;
    private Button botonBuscar;
    private Button volver;
    private EditText emailText;
    private TextView nombre;
    private TextView apellido;
    private Button cambiarEstado;

    private Participante participante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participante_admin);
        loadSpinner();
        cargarViews();
    }

    private void cargarViews() {
        this.botonBuscar = (Button) findViewById(R.id.boton_buscar_participante);
        this.botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar();
            }
        });

        this.volver = (Button) findViewById(R.id.boton_volver);
        this.volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volver();
            }
        });

        this.emailText = (EditText) findViewById(R.id.admin_input_email2);
        this.nombre = (TextView) findViewById(R.id.input_name);
        this.apellido = (TextView) findViewById(R.id.input_lastname);
        this.cambiarEstado = (Button) findViewById(R.id.boton_cambiar_estado);
        this.cambiarEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarEstado();
            }
        });
    }

    private void loadSpinner() {
        this.spinner = (Spinner) findViewById(R.id.estadoParticipante);
        String[] letra = {"Inactivo","Activo"};
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, letra));
    }

    @Override
    public void onBackPressed() {
        // no hacer nada si apreta el boton back
    }

    private void volver() {
        Intent intent = new Intent(getApplicationContext(), AdminDashboard.class);
        startActivity(intent);
    }

    private void buscar() {
        ParticipantePorEmailService service = new ParticipantePorEmailService(getResources(), emailText.getText().toString());
        service.execute();

        try {
            Participante participante = service.get();
            this.participante = participante;
            this.nombre.setText(participante.getNombre());
            this.apellido.setText(participante.getApellido());

            if (participante.getEstado().intValue() == 0) {
                this.spinner.setSelection(0);
            }
            else {
                this.spinner.setSelection(1);
            }
        }
        catch (Exception e) {
            Log.e("ParticipanteAdmin", "Error trayendo Participante por Email", e);
        }
    }

    private void cambiarEstado() {
        int selected = spinner.getSelectedItemPosition();

        if (selected != participante.getEstado().intValue()) {
            participante.setEstado(new Byte(String.valueOf(selected)));
            cambiarEstadoCall();

            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
            dialogo.setTitle("Mensaje");
            dialogo.setMessage("Participante actualizado correctamente");
            dialogo.setNeutralButton("Cerrar", null);
            dialogo.show();
        }
    }

    private void cambiarEstadoCall() {
        ProfileService service = new ProfileService(participante,  getResources());
        service.execute();

        try {
            Participante participanteNuevo = service.get();
            participante = participanteNuevo;

        }
        catch (Exception e) {
            Log.e(TAG, "Error al actualizar el Participante", e);
        }
    }
}
