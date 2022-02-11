package com.jeimandei.projectone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.jeimandei.projectone.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;
    private ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    TextView n_name, n_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        n_name = findViewById(R.id.nav_name);
        n_email = findViewById(R.id.nav_email);

        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_search:
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        setSupportActionBar(mainBinding.toolbar);

        getSupportActionBar().setTitle("Account");
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new AccountFragment()).commit();
        mainBinding.navbarView.setCheckedItem(R.id.nav_account);

        toggle = new ActionBarDrawerToggle(this, mainBinding.navDrawer, mainBinding.toolbar, R.string.open, R.string.close);

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        toggle.syncState();

        final Fragment[] fragments = {null};

        mainBinding.navbarView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_account:
                        fragments[0] = new AccountFragment();
                        getSupportActionBar().setTitle("Account");
                        mainBinding.navDrawer.closeDrawer(GravityCompat.START);
                        callFragment(fragments[0]);
                        break;
                    case R.id.nav_participant:
                        fragments[0] = new ParticipantFragment();
                        getSupportActionBar().setTitle("Participant");
                        mainBinding.navDrawer.closeDrawer(GravityCompat.START);
                        callFragment(fragments[0]);
                        break;
                    case R.id.nav_company:
                        fragments[0] = new CompanyFragment();
                        getSupportActionBar().setTitle("Company");
                        mainBinding.navDrawer.closeDrawer(GravityCompat.START);
                        callFragment(fragments[0]);
                        break;
                    case R.id.nav_instructor:
                        fragments[0] = new InstructorFragment();
                        getSupportActionBar().setTitle("Instructor");
                        mainBinding.navDrawer.closeDrawer(GravityCompat.START);
                        callFragment(fragments[0]);
                        break;
                    case R.id.nav_subject:
                        fragments[0] = new SubjectFragment();
                        getSupportActionBar().setTitle("Subject");
                        mainBinding.navDrawer.closeDrawer(GravityCompat.START);
                        callFragment(fragments[0]);
                        break;
                    case R.id.nav_class:
                        fragments[0] = new ClassFragment();
                        getSupportActionBar().setTitle("Class");
                        mainBinding.navDrawer.closeDrawer(GravityCompat.START);
                        callFragment(fragments[0]);
                        break;
                    case R.id.nav_classdetail:
                        fragments[0] = new Class_DetailFragment();
                        getSupportActionBar().setTitle("Class Detail");
                        mainBinding.navDrawer.closeDrawer(GravityCompat.START);
                        callFragment(fragments[0]);
                        break;
                }
                return true;
            }
        });
    }

    public void callFragment(Fragment fragment) {
        FragmentManager man = getSupportFragmentManager();
        FragmentTransaction trans = man.beginTransaction();
        trans.setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
        );
        trans.replace(R.id.framelayout, fragment);
        trans.addToBackStack(null);
        trans.commit();
    }

}