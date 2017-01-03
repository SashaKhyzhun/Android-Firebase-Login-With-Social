package com.sashakhyzhun.android_firebase_login_with_social;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by SashaKhyzhun on 1/3/17.
 */

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();
    private Firebase mRef = new Firebase("https://android-firebase-login-b7861.firebaseio.com/users/");
    private User user;
    private EditText name;
    private EditText phoneNumber;
    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        mAuth = FirebaseAuth.getInstance();
        name = (EditText) findViewById(R.id.edit_text_username);
        phoneNumber = (EditText) findViewById(R.id.edit_text_phone_number);
        email = (EditText) findViewById(R.id.edit_text_new_email);
        password = (EditText) findViewById(R.id.edit_text_new_password);

    }

    //This method sets up a new User by fetching the user entered details.
    protected void setUpUser() {
        user = new User();
        user.setName(name.getText().toString());
        user.setPhoneNumber(phoneNumber.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
    }

    public void onSignUpClicked(View view) {
        createNewAccount(email.getText().toString(), password.getText().toString());
        showProgressDialog();
    }


    private void createNewAccount(String email, String password) {
        Log.d(TAG, "createNewAccount:" + email);
        if (!validateForm()) {
            return;
        }
        //This method sets up a new User by fetching the user entered details.
        setUpUser();
        //This method  method  takes in an email address and password, validates them and then creates a new user
        // with the createUserWithEmailAndPassword method.
        // If the new account was created, the user is also signed in, and the AuthStateListener runs the onAuthStateChanged callback.
        // In the callback, you can use the getCurrentUser method to get the user's account data.

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                hideProgressDialog();

                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                } else {
                    onAuthenticationSucess(task.getResult().getUser());
                }


            }
        });

    }

    private void onAuthenticationSucess(FirebaseUser mUser) {
        // Write new user
        saveNewUser(mUser.getUid(), user.getName(), user.getPhoneNumber(), user.getEmail(), user.getPassword());
        signOut();
        // Go to LoginActivity
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        finish();
    }

    private void saveNewUser(String userId, String name, String phone, String email, String password) {
        User user = new User(userId,name,phone,email,password);

        mRef.child("users").child(userId).setValue(user);
    }


    private void signOut() {
        mAuth.signOut();
    }
    //This method, validates email address and password
    private boolean validateForm() {
        boolean valid = true;

        String userEmail = email.getText().toString();
        if (TextUtils.isEmpty(userEmail)) {
            email.setError("Required.");
            valid = false;
        } else {
            email.setError(null);
        }

        String userPassword = password.getText().toString();
        if (TextUtils.isEmpty(userPassword)) {
            password.setError("Required.");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }


    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}