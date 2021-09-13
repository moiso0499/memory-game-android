package com.example.proyecto_moviles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText etCorreo;
    private EditText etContrasena;

    public static FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etCorreo = findViewById(R.id.etCorreo);
        etContrasena = findViewById(R.id.etContrasena);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        actualizarUI(user);
    }

    public void registrar(View view){
        String correo = etCorreo.getText().toString();
        String contrasena = etContrasena.getText().toString();

        mAuth.createUserWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "El usuario se ha registrado con éxito",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Ha habido un error en el registro del usuario",
                                    Toast.LENGTH_SHORT).show();
                            actualizarUI(null);
                        }
                    }
                });
    }

    private void actualizarUI(FirebaseUser user){
        if(user != null){
            Intent intent = new Intent(this,MenuPrincipal.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(),"Inténtelo de nuevo",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void login(View view){
        String correo = etCorreo.getText().toString();
        String contrasena = etContrasena.getText().toString();

        mAuth.signInWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            actualizarUI(user);
                        }
                        else{
                            actualizarUI(null);
                        }
                    }
                });
    }
}