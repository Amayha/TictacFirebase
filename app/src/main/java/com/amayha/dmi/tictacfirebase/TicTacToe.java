package com.amayha.dmi.tictacfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class TicTacToe extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String tablaSeleccionJugador = "SeleccionJugador";
    String tablaJugadas = "Jugadas";
    String uidMovil1 = "Movil1";
    String uidMovil2 = "Movil2";
    String seleccionado = "Seleccionado";
    String uid = "uid";
    String quiensoy;
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9;
    Button botones[][];

    String turno;
    String ganador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        //El jugador llega como parametro de la activity principal
        //paarpoder saber como manejamos los colores del tablero
        Bundle bundle = getIntent().getExtras();
        quiensoy = bundle.getString("jugador");

        inicializarFirebase();

        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        b5 = (Button) findViewById(R.id.button5);
        b6 = (Button) findViewById(R.id.button6);
        b7 = (Button) findViewById(R.id.button7);
        b8 = (Button) findViewById(R.id.button8);
        b9 = (Button) findViewById(R.id.button9);

        botones = new Button[3][3];

        llenarMatrizBotones();

        leerDatosJugadas();

        escucharTurno();

        activarListenerBotones();

    }

    private void escucharTurno() {
        databaseReference.child("Turno").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    turno = objSnapshot.getValue(String.class);
                    Log.d("calupe", "Turno: " + turno);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void llenarMatrizBotones() {
        botones[0][0] = b1;
        botones[0][1] = b2;
        botones[0][2] = b3;
        botones[1][0] = b4;
        botones[1][1] = b5;
        botones[1][2] = b6;
        botones[2][0] = b7;
        botones[2][1] = b8;
        botones[2][2] = b9;
    }

    private void leerDatosJugadas() {
        //Establecemos un escucha sobre la tabla de jugadas para saber que se esta oprimiendo
        //en cada tablero
        databaseReference.child(tablaJugadas).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Jugada jg = new Jugada();
                limpiarTablero();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    jg = (Jugada) objSnapshot.getValue(Jugada.class);
                    botones[jg.getY()][jg.getX()].setText(jg.getUid());

                    //Como la casilla ya esta jugada la deshabilitamos
                    botones[jg.getY()][jg.getX()].setEnabled(false);
                }
                if(validarGanador()){
                    Intent ganadorActivity = new Intent(TicTacToe.this,Ganador.class);
                    ganadorActivity.putExtra("ganador", ganador);
                    startActivity(ganadorActivity);
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean validarGanador() {
        boolean gano = false;

        //Falta hacer los if de las posibles combinaciones para saber el ganador
        if (botones[0][0].getText().toString().equals(botones[0][1].getText().toString()) &&
                botones[0][0].getText().toString().equals(botones[0][2].getText().toString()) &&
                botones[0][0].getText().toString() != ""
                ) {
            gano = true;
            ganador = botones[0][0].getText().toString();
            Log.d("calupe", "Ganador: " + ganador);
        }

        if (botones[1][0].getText().toString().equals(botones[1][1].getText().toString()) &&
                botones[1][0].getText().toString().equals(botones[1][2].getText().toString()) &&
                botones[1][0].getText().toString() != ""
                ) {
            gano = true;
            ganador = botones[1][0].getText().toString();
            Log.d("calupe", "Ganador: " + ganador);
        }

        if (botones[2][0].getText().toString().equals(botones[2][1].getText().toString()) &&
                botones[2][0].getText().toString().equals(botones[2][2].getText().toString()) &&
                botones[2][0].getText().toString() != ""
                ) {
            gano = true;
            ganador = botones[2][0].getText().toString();
            Log.d("calupe", "Ganador: " + ganador);
        }

        if (botones[0][0].getText().toString().equals(botones[1][0].getText().toString()) &&
                botones[0][0].getText().toString().equals(botones[2][0].getText().toString()) &&
                botones[0][0].getText().toString() != ""
                ) {
            gano = true;
            ganador = botones[0][0].getText().toString();
            Log.d("calupe", "Ganador: " + ganador);
        }

        if (botones[0][1].getText().toString().equals(botones[1][1].getText().toString()) &&
                botones[0][1].getText().toString().equals(botones[2][1].getText().toString()) &&
                botones[0][1].getText().toString() != ""
                ) {
            gano = true;
            ganador = botones[0][1].getText().toString();
            Log.d("calupe", "Ganador: " + ganador);
        }

        if (botones[0][2].getText().toString().equals(botones[1][2].getText().toString()) &&
                botones[0][2].getText().toString().equals(botones[2][2].getText().toString()) &&
                botones[0][2].getText().toString() != ""
                ) {
            gano = true;
            ganador = botones[0][2].getText().toString();
            Log.d("calupe", "Ganador: " + ganador);
        }

        if (botones[0][0].getText().toString().equals(botones[1][1].getText().toString()) &&
                botones[0][0].getText().toString().equals(botones[2][2].getText().toString()) &&
                botones[0][0].getText().toString() != ""
                ) {
            gano = true;
            ganador = botones[0][0].getText().toString();
            Log.d("calupe", "Ganador: " + ganador);
        }

        if (botones[0][2].getText().toString().equals(botones[1][1].getText().toString()) &&
                botones[0][2].getText().toString().equals(botones[2][0].getText().toString()) &&
                botones[0][2].getText().toString() != ""
                ) {
            gano = true;
            ganador = botones[0][2].getText().toString();
            Log.d("calupe", "Ganador: " + ganador);
        }

        return gano;
    }

    private void limpiarTablero() {
        for (int k = 0; k < 3; k++) {
            for (int p = 0; p < 3; p++) {
                botones[k][p].setEnabled(true);
                botones[k][p].setText("");
            }
        }
    }

    private void activarListenerBotones() {
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quiensoy.equals(turno)) {
                    Jugada jg = new Jugada(quiensoy, 0, 0);
                    databaseReference.child(tablaJugadas).child(UUID.randomUUID().toString()).setValue(jg);

                    //Ajustamos para ceder el turno al otro jugador
                    if (quiensoy.equals(uidMovil1))
                        databaseReference.child("Turno").child("Turno").setValue(uidMovil2);
                    else
                        databaseReference.child("Turno").child("Turno").setValue(uidMovil1);
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quiensoy.equals(turno)) {
                    Jugada jg = new Jugada(quiensoy, 0, 1);
                    databaseReference.child(tablaJugadas).child(UUID.randomUUID().toString()).setValue(jg);

                    //Ajustamos para ceder el turno al otro jugador
                    if (quiensoy.equals(uidMovil1))
                        databaseReference.child("Turno").child("Turno").setValue(uidMovil2);
                    else
                        databaseReference.child("Turno").child("Turno").setValue(uidMovil1);
                }
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quiensoy.equals(turno)) {
                    Jugada jg = new Jugada(quiensoy, 0, 2);
                    databaseReference.child(tablaJugadas).child(UUID.randomUUID().toString()).setValue(jg);

                    //Ajustamos para ceder el turno al otro jugador
                    if (quiensoy.equals(uidMovil1))
                        databaseReference.child("Turno").child("Turno").setValue(uidMovil2);
                    else
                        databaseReference.child("Turno").child("Turno").setValue(uidMovil1);
                }
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quiensoy.equals(turno)) {
                    Jugada jg = new Jugada(quiensoy, 1, 0);
                    databaseReference.child(tablaJugadas).child(UUID.randomUUID().toString()).setValue(jg);

                    //Ajustamos para ceder el turno al otro jugador
                    if (quiensoy.equals(uidMovil1))
                        databaseReference.child("Turno").child("Turno").setValue(uidMovil2);
                    else
                        databaseReference.child("Turno").child("Turno").setValue(uidMovil1);
                }
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quiensoy.equals(turno)) {
                    Jugada jg = new Jugada(quiensoy, 1, 1);
                    databaseReference.child(tablaJugadas).child(UUID.randomUUID().toString()).setValue(jg);

                    //Ajustamos para ceder el turno al otro jugador
                    if (quiensoy.equals(uidMovil1))
                        databaseReference.child("Turno").child("Turno").setValue(uidMovil2);
                    else
                        databaseReference.child("Turno").child("Turno").setValue(uidMovil1);
                }
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quiensoy.equals(turno)) {
                    Jugada jg = new Jugada(quiensoy, 1, 2);
                    databaseReference.child(tablaJugadas).child(UUID.randomUUID().toString()).setValue(jg);

                    //Ajustamos para ceder el turno al otro jugador
                    if (quiensoy.equals(uidMovil1))
                        databaseReference.child("Turno").child("Turno").setValue(uidMovil2);
                    else
                        databaseReference.child("Turno").child("Turno").setValue(uidMovil1);
                }
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quiensoy.equals(turno)) {
                    Jugada jg = new Jugada(quiensoy, 2, 0);
                    databaseReference.child(tablaJugadas).child(UUID.randomUUID().toString()).setValue(jg);

                    //Ajustamos para ceder el turno al otro jugador
                    if (quiensoy.equals(uidMovil1))
                        databaseReference.child("Turno").child("Turno").setValue(uidMovil2);
                    else
                        databaseReference.child("Turno").child("Turno").setValue(uidMovil1);
                }
            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quiensoy.equals(turno)) {
                    Jugada jg = new Jugada(quiensoy, 2, 1);
                    databaseReference.child(tablaJugadas).child(UUID.randomUUID().toString()).setValue(jg);

                    //Ajustamos para ceder el turno al otro jugador
                    if (quiensoy.equals(uidMovil1))
                        databaseReference.child("Turno").child("Turno").setValue(uidMovil2);
                    else
                        databaseReference.child("Turno").child("Turno").setValue(uidMovil1);
                }
            }
        });

        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quiensoy.equals(turno)) {
                    Jugada jg = new Jugada(quiensoy, 2, 2);
                    databaseReference.child(tablaJugadas).child(UUID.randomUUID().toString()).setValue(jg);

                    //Ajustamos para ceder el turno al otro jugador
                    if (quiensoy.equals(uidMovil1))
                        databaseReference.child("Turno").child("Turno").setValue(uidMovil2);
                    else
                        databaseReference.child("Turno").child("Turno").setValue(uidMovil1);
                }
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }
}
