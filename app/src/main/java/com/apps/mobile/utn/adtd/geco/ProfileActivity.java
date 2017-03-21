package com.apps.mobile.utn.adtd.geco;

import android.app.DatePickerDialog;
import android.content.Context;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.mobile.utn.adtd.geco.Api.ApiClient;
import com.apps.mobile.utn.adtd.geco.Api.ApiInterface;
import com.apps.mobile.utn.adtd.geco.Api.PersonResponse;
import com.apps.mobile.utn.adtd.geco.Model.Person;
import com.apps.mobile.utn.adtd.geco.Model.Profile;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private String TOKEN = null;

    private EditText firstname;
    private EditText lastname;
    private EditText skype;
    private EditText phone_mobile;
    private EditText phone_home;
    private EditText address;
    private EditText birthdate;
    private EditText email;
    private DatePickerDialog birthdatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private AppCompatImageButton button_save;
    private AppCompatImageButton button_close;
    private Person profile;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TOKEN = null;

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        firstname = (EditText)  findViewById(R.id.editTextFirstName);
        lastname = (EditText)  findViewById(R.id.editTextLastName);
        skype = (EditText)  findViewById(R.id.editTextSkype);
        phone_home = (EditText)  findViewById(R.id.editTextPhoneHome);
        phone_mobile = (EditText)  findViewById(R.id.editTextPhoneMobile);
        address = (EditText)  findViewById(R.id.editTextAddress);
        birthdate = (EditText)  findViewById(R.id.editTextBirthdate);
        email = (EditText)  findViewById(R.id.editTextEmail);

        button_save = (AppCompatImageButton) findViewById(R.id.button_profile_save);
        button_close = (AppCompatImageButton) findViewById(R.id.button_profile_close);

        button_save.setOnClickListener(this);
        button_close.setOnClickListener(this);
        birthdate.setOnClickListener(this);
        /*Intent mIntent = getIntent();
        Bundle mBundle = mIntent.getExtras();
         if(mBundle != null){
            TOKEN = mBundle.getString("TOKEN");
        }*/


        fillProfile();
        setDateTimeField();
    }


    private void setDateTimeField() {


        Calendar newCalendar = Calendar.getInstance();
        birthdatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                birthdate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }
    public void updateProfile(final Context context){


        ApiInterface mApiService = ApiClient.getClient().create(ApiInterface.class);

        Profile.getInstance().getPerson().setAddress(address.getText().toString());
        Profile.getInstance().getPerson().setFirstname(firstname.getText().toString());
        Profile.getInstance().getPerson().setLastname(lastname.getText().toString());
        Profile.getInstance().getPerson().setPhone_home(phone_home.getText().toString());
        Profile.getInstance().getPerson().setPhone_mobile(phone_mobile.getText().toString());
        Profile.getInstance().getPerson().setSkype(skype.getText().toString());
        Profile.getInstance().getPerson().setBirthdate(new Date(birthdate.getText().toString()));

        Call<PersonResponse> mService = mApiService.updateProfile(Profile.getInstance().getTOKEN(), Profile.getInstance().getPerson());
        mService.enqueue(new Callback<PersonResponse>() {

            @Override
            public void onResponse(Call<PersonResponse> call, Response<PersonResponse> response) {

                int statusCode = response.code();

                switch (statusCode)
                {
                    case 200: //OK

                        Toast.makeText(context, context.getString(R.string.msg_updated), Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    case 401: //Unauthorized

                        Toast.makeText(context, context.getString(R.string.error_incorrect_email_password), Toast.LENGTH_LONG).show();

                        break;
                    default: //Server Error
                        Toast.makeText(context, context.getString(R.string.error_server), Toast.LENGTH_LONG).show();
                        break;
                }

            }

            @Override
            public void onFailure(Call<PersonResponse> call, Throwable t) {
                call.cancel();
                Log.d("JLA",context.getString(R.string.error_connection_failed));
                Toast.makeText(context, context.getString(R.string.error_connection_failed), Toast.LENGTH_LONG).show();
            }

        });
    }
    public void fillProfile(){

         firstname.setText(Profile.getInstance().getPerson().getFirstname());
         lastname.setText(Profile.getInstance().getPerson().getLastname());
         skype.setText(Profile.getInstance().getPerson().getSkype());
         phone_home.setText(Profile.getInstance().getPerson().getPhone_home());
         phone_mobile.setText(Profile.getInstance().getPerson().getPhone_mobile());
         address.setText(Profile.getInstance().getPerson().getAddress());

        SimpleDateFormat simpleDate =  new SimpleDateFormat("dd/MM/yyyy");

        String strDt = simpleDate.format(Profile.getInstance().getPerson().getBirthdate());


         birthdate.setText(strDt);
         email.setText(Profile.getInstance().getEmail());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_profile_save:
                Log.d("JLA","Update");
                updateProfile(v.getContext());
                break;

            case R.id.button_profile_close:
                finish();
                break;

            case R.id.editTextBirthdate:
                birthdatePickerDialog.show();
                break;
            default:
                break;

        }

    }



}
