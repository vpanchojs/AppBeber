package com.jcode.tebocydelevery.init.registrerUser.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.jcode.tebocydelevery.R;
import com.jcode.tebocydelevery.models.User;
import com.jcode.tebocydelevery.main.ui.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrerUserActivity extends AppCompatActivity implements RegisterUserView {


    private static final String TAG = "RegisterUserActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_dni)
    EditText etDni;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_lastname)
    EditText etLastname;
    @BindView(R.id.et_birthdate)
    EditText etBirthdate;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_signup)
    Button btnSignup;
    @BindView(R.id.civ_photo_user)
    CircleImageView civPhotoUser;
    @BindView(R.id.et_phone)
    EditText etPhone;


    private Long birthdate;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private User user;

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrer_user);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registrarse");

        etBirthdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePicker();
                }
            }
        });

        try {
            user = getIntent().getExtras().getParcelable("user");
            setDataUser();
        } catch (Exception e) {

        }

    }

    private void setDataUser() {
        etEmail.setText(user.getEmail());
        etName.setText(user.getName());
        etLastname.setText(user.getLastname());
    }

    public void showDatePicker() {
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendario = Calendar.getInstance();
                calendario.set(Calendar.YEAR, year);
                calendario.set(Calendar.MONTH, monthOfYear);
                calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                birthdate = calendario.getTimeInMillis();
                etBirthdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }

        }, newCalendar.get(Calendar.DAY_OF_MONTH), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.YEAR));
        fromDatePickerDialog.show();
    }

    @Override
    public void navigationToMain() {
        Intent intentMain = new Intent(this, MainActivity.class);
        intentMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentMain);
    }


    @OnClick(R.id.btn_signup)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_signup:
                // String name, String lastname, String dni, Date birthdate, String email, String password
                user.setName(etName.getText().toString());
                user.setLastname(etLastname.getText().toString());
                user.setBirthdate(etBirthdate.getText().toString());
                user.setPhone(etPhone.getText().toString());
                user.setDni(etDni.getText().toString());
                //presenter.doRegister(user);
                break;
        }
    }


    @Override
    public void showProgress(boolean show) {

    }

}
