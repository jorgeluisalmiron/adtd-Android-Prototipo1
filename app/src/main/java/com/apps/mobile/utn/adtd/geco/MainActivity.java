package com.apps.mobile.utn.adtd.geco;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apps.mobile.utn.adtd.geco.Api.ApiClient;
import com.apps.mobile.utn.adtd.geco.Api.ApiInterface;
import com.apps.mobile.utn.adtd.geco.Api.MessageResponse;
import com.apps.mobile.utn.adtd.geco.Model.Profile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,  FragmentLinks.OnFragmentInteractionListener , FragmentYoutube.OnFragmentInteractionListener {



    private LinearLayout welcomeLayout;
    private TextView textViewWelcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        welcomeLayout = (LinearLayout) findViewById(R.id.linearLayoutWelcome) ;



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewWelcome = (TextView) findViewById(R.id.textViewWelcome);
        textViewWelcome.setText(getString(R.string.str_hello)+"\n" + Profile.getInstance().getPerson().getFirstname() + " " + Profile.getInstance().getPerson().getLastname() );

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View header=navigationView.getHeaderView(0);
        TextView textViewLogin = (TextView)header.findViewById(R.id.textViewLogin);
        textViewLogin.setText(Profile.getInstance().getPerson().getFirstname() + " " + Profile.getInstance().getPerson().getLastname());
        welcomeLayout.setVisibility(LinearLayout.VISIBLE);


        //  setContentView(R.layout.content_main);

    }

   /* private void getLoginData(){
        String storedEmail = "";
        Intent mIntent = getIntent();
        Bundle mBundle = mIntent.getExtras();
        if(mBundle != null){
            TOKEN = mBundle.getString("TOKEN");
            email = mBundle.getString("email");

        }

    }*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            logout(getApplicationContext());
            finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Fragment fragment = null;
        boolean fragmentTransaction = false;
        Bundle bundle = new Bundle();
        switch (id){
            case R.id.about:
                Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(aboutIntent);
                 break;
            case R.id.nav_profile:
                Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(profileIntent);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        boolean fragmentTransaction = false;
        Bundle bundle = new Bundle();
        switch (id){

            case R.id.nav_videos:

               // bundle.putString("TOKEN", TOKEN);
                fragment = new FragmentYoutube();
               // fragment.setArguments(bundle);
                fragmentTransaction = true;
                break;

            case R.id.nav_links:

                //bundle.putString("TOKEN", TOKEN);
                fragment = new FragmentLinks();
                //fragment.setArguments(bundle);
                fragmentTransaction = true;
                break;



            default:
                break;
        }



        if (fragmentTransaction) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());
            welcomeLayout.setVisibility(LinearLayout.GONE);


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    private void logout (Context context){

            ApiInterface mApiService = ApiClient.getClient().create(ApiInterface.class);

            Call<MessageResponse> mService = mApiService.logout(Profile.getInstance().getTOKEN());
            mService.enqueue(new Callback<MessageResponse>() {

                @Override
                public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

                    int statusCode = response.code();

                    switch (statusCode)
                    {
                        case 200: //OK

                            Log.d("JLA",response.body().getMsg());

                            break;
                        case 401: //Unauthorized

                            Log.d("JLA",MainActivity.this.getString(R.string.error_incorrect_email_password));


                            break;
                        default: //Server Error
                            Log.d("JLA",MainActivity.this.getString(R.string.error_server));

                            break;
                    }
                    finish();
                }

                @Override
                public void onFailure(Call<MessageResponse> call, Throwable t) {
                    call.cancel();
                    Log.d("JLA",MainActivity.this.getString(R.string.error_connection_failed));
                    finish();

                }

            });
    }



}