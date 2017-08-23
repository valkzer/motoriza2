package com.example.valkzer.motorizados.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
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
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void createAccount(String email, String password)
    {
        Toast.makeText(this, R.string.creating_account, Toast.LENGTH_SHORT).show();

        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        mAuth.createUserWithEmailAndPassword(email, password)
             .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task)
                 {
                     if (task.isSuccessful()) {
                         Toast.makeText(getApplicationContext(), R.string.account_created, Toast.LENGTH_SHORT).show();

                         FirebaseUser user = mAuth.getCurrentUser();
                         updateUI(user);

                         Intent intent = new Intent(getApplicationContext(), DeliveryListActivity.class);
                         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                         startActivity(intent);
                     } else {
                         Toast.makeText(getApplicationContext(), R.string.failed_to_create_account, Toast.LENGTH_SHORT).show();
                         updateUI(null);
                     }

                     hideProgressDialog();
                 }
             });
    }

    private void signIn(String email, String password)
    {
        Toast.makeText(getApplicationContext(), R.string.auth_started, Toast.LENGTH_SHORT).show();
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        mAuth.signInWithEmailAndPassword(email, password)
             .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task)
                 {
                     if (task.isSuccessful()) {
                         Toast.makeText(getApplicationContext(), R.string.auth_successful, Toast.LENGTH_SHORT).show();
                         FirebaseUser user = mAuth.getCurrentUser();
                         updateUI(user);

                         Intent intent = new Intent(getApplicationContext(), DeliveryListActivity.class);
                         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                         intent.putExtra("user_name", user.getDisplayName());
                         intent.putExtra("user_email", user.getEmail());
                         intent.putExtra("user_photo", user.getPhotoUrl());
                         startActivity(intent);
                     } else {
                         Toast.makeText(getApplicationContext(), R.string.auth_successful, Toast.LENGTH_SHORT).show();
                         updateUI(null);
                     }
                     hideProgressDialog();

                 }
             });
    }

    private void signOut()
    {
        mAuth.signOut();
        updateUI(null);
    }

    private void sendEmailVerification()
    {
        findViewById(R.id.verify_email_button).setEnabled(false);

        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
            .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    findViewById(R.id.verify_email_button).setEnabled(true);
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), R.string.verification_email_sent, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.failed_to_send_verification_email, Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    private boolean validateForm()
    {
        boolean valid = true;
        String  email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError(getString(R.string.required));
            valid = false;
        } else {
            mEmailField.setError(null);
        }
        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError(getString(R.string.required));
            valid = false;
        } else {
            mPasswordField.setError(null);
        }
        return valid;
    }

    private void updateUI(FirebaseUser user)
    {
        hideProgressDialog();
        if (user != null) {
            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.verify_email_button).setEnabled(!user.isEmailVerified());
        } else {
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


    public void onClickAnonymous(View view)
    {
        FirebaseAuth.getInstance().signInAnonymously()
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful()) {

                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);

                                Intent intent = new Intent(getApplicationContext(), DeliveryListActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            } else {

                                Toast.makeText(getApplicationContext(), R.string.auth_failed,
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }

                        }
                    });

    }
}
