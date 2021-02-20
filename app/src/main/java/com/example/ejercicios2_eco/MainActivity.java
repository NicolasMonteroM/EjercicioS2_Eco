package com.example.ejercicios2_eco;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView pregunta;
    private EditText respuesta;
    private Button ok, retry;
    private Pregunta p;
    private TextView puntajeTV, contadorTV;
    private int contador;
    private int puntaje;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        puntaje = 0;
        contador = 30;

        pregunta = findViewById(R.id.pregunta);
        respuesta = findViewById(R.id.respuesta);
        ok = findViewById(R.id.ok);
        retry = findViewById(R.id.retry);
        puntajeTV = findViewById(R.id.puntaje);
        contadorTV = findViewById(R.id.contador);

        //  Toast.makeText(this, "Respuesta: " + p.getRespuesta(), Toast.LENGTH_SHORT).show();

        nuevaPregunta();

        ok.setOnClickListener(

                v -> {
                    responder();
                }
        );

    }

    public void responder() {

        contador = 30;

        String res = respuesta.getText().toString();
        int resInt = Integer.parseInt(res);
        int correcta = p.getRespuesta();

        if (resInt == correcta) {

            Toast.makeText(this, "Correcto", Toast.LENGTH_SHORT).show();
            puntaje += 5;


        } else {

            Toast.makeText(this, "Incorrecto", Toast.LENGTH_SHORT).show();
            puntaje -= 4;

        }

        retry.setOnClickListener(

                v -> {
                    puntaje = 0;
                    volverIntentar();
                }
        );

        nuevaPregunta();

        respuesta.setText("");
        puntajeTV.setText("Puntaje: " + puntaje);

    }

    public void volverIntentar() {

        puntaje = 0;
        nuevaPregunta();
        retry.setVisibility(View.GONE);
        ok.setVisibility(View.VISIBLE);

    }

    public void nuevaPregunta() {

        contador = 30;
        p = new Pregunta();
        pregunta.setText(p.getPregunta());
        generarContador();

    }

    public void generarContador() {

        new Thread(

                () -> {

                    while (contador > 0) {

                        contador--;

                        runOnUiThread(

                                () -> {
                                    contadorTV.setText("" + contador);
                                }
                        );

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                    runOnUiThread(
                            () -> {
                                ok.setVisibility(View.GONE);
                                retry.setVisibility(View.VISIBLE);
                            }
                    );

                }

        ).start();

    }

}