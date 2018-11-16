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

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Button btn_j1, btn_j2;

    String tablaSeleccionJugador = "SeleccionJugador";
    String tablaJugadas = "Jugadas";
    String uidMovil1 = "Movil1";
    String uidMovil2 = "Movil2";
    String seleccionado = "Seleccionado";
    String uid = "uid";
    String quiensoy;

    SeleccionJugador seleccionJugador1, seleccionJugador2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_j1 = (Button) findViewById(R.id.btn_j1);
        btn_j2 = (Button) findViewById(R.id.btn_j2);


        inicializarFirebase();

        leerDatosSeleccionJugador();

        btn_j1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Debemos informar a Firebase de la seleccion del jugador
                quiensoy = uidMovil1;
                databaseReference.child(tablaSeleccionJugador).child(uidMovil1).child(seleccionado).setValue("1");
                databaseReference.child(tablaSeleccionJugador).child(uidMovil1).child(uid).setValue(uidMovil1);

                //Si el jugador oprime ser jugador 1 ya no puede ser jugador 2, lo inactivamos
                btn_j2.setEnabled(false);
            }
        });

        btn_j2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Debemos informar a Firebase de la seleccion del jugador
                quiensoy = uidMovil2;
                databaseReference.child(tablaSeleccionJugador).child(uidMovil2).child(seleccionado).setValue("1");
                databaseReference.child(tablaSeleccionJugador).child(uidMovil2).child(uid).setValue(uidMovil2);

                //Si el jugador oprime ser jugador 2 ya no puede ser jugador 1, lo inactivamos
                btn_j1.setEnabled(false);
            }
        });


    }

    private void leerDatosSeleccionJugador() {
        //Establecemos un escucha sobre la tabla de seleccion jugador para saber si ya han oprimido
        //el boton de seleccion del jugador
        databaseReference.child(tablaSeleccionJugador).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Log.d("calupe", "Valor de snapshot: " + objSnapshot.getValue(SeleccionJugador.class).getUid());
                    Log.d("calupe", "Valor de snapshot: " + objSnapshot.getValue(SeleccionJugador.class).getSeleccionado());

                    //en las variables seleccion Jugador guardamos las actualizaciones de la seleecion de jugadores
                    //gracias al escucha cada vez que en la base de datos cambie se actualizan los valores
                    if (objSnapshot.getValue(SeleccionJugador.class).getUid().equals(uidMovil1)) {
                        seleccionJugador1 = objSnapshot.getValue(SeleccionJugador.class);
                    } else {
                        seleccionJugador2 = objSnapshot.getValue(SeleccionJugador.class);
                    }
                }
                //Deshabilitamos el boton que ya halla sido seleccionado por alguno de los dos jugadores
                if (seleccionJugador1.getSeleccionado().equals("1")) {
                    btn_j1.setEnabled(false);
                }

                if (seleccionJugador2.getSeleccionado().equals("1")) {
                    btn_j2.setEnabled(false);
                }

                //Si ya ambos jugadores seleccionaron se debe lanzar la activity del juego.
                if (seleccionJugador1.getSeleccionado().equals("1") &&
                        seleccionJugador2.getSeleccionado().equals("1")) {
                    Intent tictactoe = new Intent(MainActivity.this, TicTacToe.class);
                    tictactoe.putExtra("jugador", quiensoy);
                    startActivity(tictactoe);
                    finish();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
