package com.amayha.dmi.tictacfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Ganador extends AppCompatActivity {

    String ganador;
    TextView tvGanador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganador);

        Bundle bundle = getIntent().getExtras();
        ganador = bundle.getString("ganador");

        tvGanador = findViewById(R.id.txtGanador);
        tvGanador.setText(ganador);



    }
}
