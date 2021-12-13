package com.optic.makeitbetter.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.optic.makeitbetter.R;
import com.optic.makeitbetter.models.User;
import com.optic.makeitbetter.providers.AuthProvider;
import com.optic.makeitbetter.providers.UsersProvider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dmax.dialog.SpotsDialog;

public class CompleteProfileActivity extends AppCompatActivity {

    TextInputEditText mTextInputUsername;
    TextInputEditText mTextInputPhone;
    TextInputEditText mTextInputPeso;
    TextInputEditText mTextInputTalla;
    TextInputEditText mTextInputFecha;
    TextInputEditText mTextInputGenero;
    Button mButtonRegister;
    AuthProvider mAuthProvider;
    UsersProvider mUsersProvider;
    AlertDialog mDialog;
    int edad = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

        mTextInputUsername = findViewById(R.id.textInputUsername);
        mTextInputPhone = findViewById(R.id.textInputPhone);
        mTextInputPeso = findViewById(R.id.textInputPeso);
        mTextInputTalla = findViewById(R.id.textInputTalla);
        mTextInputFecha = findViewById(R.id.textInputDate);
        mTextInputGenero = findViewById(R.id.textInputGenero);
        mButtonRegister = findViewById(R.id.btnRegister);

        mAuthProvider = new AuthProvider();
        mUsersProvider = new UsersProvider();

        mDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Espere un momento")
                .setCancelable(false).build();

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {

        String username = mTextInputUsername.getText().toString();
        String phone = mTextInputPhone.getText().toString();
        Double peso =  Double.parseDouble(mTextInputPeso.getText().toString());
        Double talla = Double.parseDouble(mTextInputTalla.getText().toString());
        String age = mTextInputFecha.getText().toString();

        if (!username.isEmpty()) {
            updateUser(username, phone);
        } else {
            Toast.makeText(this, "Para continuar inserta todos los campos", Toast.LENGTH_SHORT).show();
        }
    }



    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            //c.set(Calendar.DAY_OF_MONTH, day);
            //String format = new SimpleDateFormat("dd MMM YYYY").format(c.getTime());
            //mTextInputFecha.setText(format);
            //this.edad = Integer.toString(calculaEdad(c.getTimeInMillis()));
        }
    };

    private int calculaEdad(long date){
        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(date);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)){
            age--;
        }
        return age;
    }

    private void updateUser(final String username, final String phone) {
        String id = mAuthProvider.getUid();
        User user = new User();
        user.setUsername(username);
        user.setId(id);
        user.setPhone(phone);
        user.setTimestamp(new Date().getTime());

        mDialog.show();
        mUsersProvider.update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mDialog.dismiss();
                if (task.isSuccessful()) {
                    Intent intent = new Intent(CompleteProfileActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(CompleteProfileActivity.this, "No se pudo almacenar el usuario en la base de datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
