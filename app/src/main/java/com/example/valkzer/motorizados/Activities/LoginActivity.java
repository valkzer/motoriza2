package com.example.valkzer.motorizados.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.valkzer.motorizados.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmailField;
    private EditText mPasswordField;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailField = (EditText) findViewById(R.id.field_email);
        mPasswordField = (EditText) findViewById(R.id.field_password);

        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        findViewById(R.id.email_create_account_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.verify_email_button).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    public void onStart()
    {
        super.onStart();
        // se Revisa si el usuario está autenticado y se actualiza la info
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void createAccount(String email, String password)
    {
        Toast.makeText(this, "Creando cuenta: " + email, Toast.LENGTH_SHORT).show();
        if (!validateForm()) { //se valida que la inf esté (correo y contraseña)
            return;
        }
        showProgressDialog();  //ojo que este llamado es de la clabe base....

        // se intenta crear el usuario
        mAuth.createUserWithEmailAndPassword(email, password)
             .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task)
                 {
                     if (task.isSuccessful()) {
                         // Si el usuario se logró crear se actualiza la info
                         Toast.makeText(getApplicationContext(), "Usuario: Creado OK!", Toast.LENGTH_SHORT).show();

                         FirebaseUser user = mAuth.getCurrentUser();
                         updateUI(user);

                         Intent intent = new Intent(getApplicationContext(), DeliveryListActivity.class);
                         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                         startActivity(intent);
                     } else {
                         //Si falló la creación se informa y se actualiza
                         Toast.makeText(getApplicationContext(), "Falló la creación", Toast.LENGTH_SHORT).show();
                         updateUI(null);
                     }

                     hideProgressDialog();
                 }
             });
    }

    private void signIn(String email, String password)
    {
        Toast.makeText(getApplicationContext(), "Conectando usuario: " + email, Toast.LENGTH_SHORT).show();
        if (!validateForm()) {  //se valida que la información este (correo/contraseña)
            return;
        }

        showProgressDialog();

        // se inicia la autenticación
        mAuth.signInWithEmailAndPassword(email, password)
             .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task)
                 {
                     if (task.isSuccessful()) {
                         // Se autenticó y se actualiza la info
                         Toast.makeText(getApplicationContext(), "Usuario: autenticado OK!", Toast.LENGTH_SHORT).show();
                         FirebaseUser user = mAuth.getCurrentUser();
                         updateUI(user);



                         Intent intent = new Intent(getApplicationContext(), DeliveryListActivity.class);
                         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                         intent.putExtra("user_name", user.getDisplayName());
                         intent.putExtra("user_email", user.getEmail());
                         intent.putExtra("user_photo", user.getPhotoUrl());
                         startActivity(intent);
                     } else {
                         // No se autenticó y se actualiza la info
                         Toast.makeText(getApplicationContext(), "Falló autenticación", Toast.LENGTH_SHORT).show();
                         updateUI(null);
                     }
                     if (!task.isSuccessful()) {
                         Toast.makeText(getApplicationContext(), R.string.auth_failed, Toast.LENGTH_SHORT).show();
                         //mStatusTextView.setText(R.string.auth_failed);
                     }
                     hideProgressDialog();

                 }
             });
    }

    //se desconecta el usuario
    private void signOut()
    {
        mAuth.signOut();
        updateUI(null);
    }

    //Enviar un correo de verificación
    private void sendEmailVerification()
    {
        // Deshabilitar el boton
        findViewById(R.id.verify_email_button).setEnabled(false);

        // Enviar un correo para verificar usuario
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
            .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    //se habilita el boton para verificar
                    findViewById(R.id.verify_email_button).setEnabled(true);
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Correo enviado a: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Falló el envio de correo: ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    //Este método básicamente  valida que la información se digitara bien
    private boolean validateForm()
    {
        boolean valid = true;
        String  email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Requerido!");
            valid = false;
        } else {
            mEmailField.setError(null);
        }
        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Requirido!");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }
        return valid;
    }

    //este método básicamente actualiza la información en el layout
    private void updateUI(FirebaseUser user)
    {
        hideProgressDialog();
        if (user != null) {

            //mStatusTextView.setText(getString(R.string.correo_contrasena_status_fmt,user.getEmail(), user.isEmailVerified()));
            //mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);

            findViewById(R.id.verify_email_button).setEnabled(!user.isEmailVerified());
        } else {
            //mStatusTextView.setText(R.string.signed_out);
            //mDetailTextView.setText(null);

            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if (i == R.id.email_create_account_button) {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (i == R.id.email_sign_in_button) {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (i == R.id.sign_out_button) {
            signOut();
        } else if (i == R.id.verify_email_button) {
            sendEmailVerification();
        }
    }

    public void OnClickSms(View v)
    {
        Intent FaceBook = new Intent(this, LoginFacebookActivity.class);
        startActivity(FaceBook);
    }

    public void onClickGoogle(View v)
    {
        Intent googleActivity = new Intent(this, LoginGoogleActivity.class);
        startActivity(googleActivity);
    }

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog()
    {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog()
    {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
        hideProgressDialog();
    }


}
