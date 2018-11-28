package com.example.irvaskova.lecture4;

import android.app.Notification;
import android.app.NotificationManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irvaskova.lecture4.adapter.MenuSlideAdapter;
import com.example.irvaskova.lecture4.fragments.Fragment1;
import com.example.irvaskova.lecture4.fragments.Fragment2;
import com.example.irvaskova.lecture4.fragments.FragmentDialogs;
import com.example.irvaskova.lecture4.model.MenuSlideItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements FragmentDialogs.OnInputListener{

    private List<MenuSlideItem> listItm;
    private MenuSlideAdapter adapter;
    private ListView listView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private static final String TAG = "MainActivity";
    NotificationAlert alert;
    Button btnSend;
    EditText edtTitle,edtContent;


    @Override
    public void sendInput(String input) {
        Log.d(TAG, "sendInput: got the input: " + input);

        mInput = input;

        setInputToTextView();
    }

    private Button mOpenDialog;
    public TextView mInputDisplay;
    public String mInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOpenDialog = findViewById(R.id.open_dialog);
        mInputDisplay = findViewById(R.id.input_display);

        mOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog.");

                FragmentDialogs fragmentDialogs = new FragmentDialogs();
                fragmentDialogs.show(getFragmentManager(), "Fragment");

            }
        });

        alert =new NotificationAlert(this);
        edtTitle = (EditText)findViewById(R.id.edtTitle);
        edtContent = (EditText)findViewById(R.id.edtContent);
        btnSend = (Button)findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtTitle.getText().toString();
                String content = edtContent.getText().toString();
                Notification.Builder builder = alert.getChannelNotifications(title,content);
                alert.getManager().notify(new Random().nextInt(),builder.build());
            }
        });

        listView = (ListView) findViewById(R.id.l_sliding_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listItm = new ArrayList<>();

        listItm.add(new MenuSlideItem(R.drawable.ic_archive, "Archive"));
        listItm.add(new MenuSlideItem(R.drawable.ic_toast,"Toast"));
        adapter = new MenuSlideAdapter(this, listItm);
        listView.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(listItm.get(0).getTitle());
        listView.setItemChecked(0,true);
        drawerLayout.closeDrawer(listView);

        replacePosition(0);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                setTitle(listItm.get(position).getTitle());
                listView.setItemChecked(position,true);
                replacePosition(position);
                drawerLayout.closeDrawer(listView);
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.opened_drawer, R.string.closed_drawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    private void setInputToTextView(){
        mInputDisplay.setText(mInput);
    }

    public void displayToast(View v){
        Toast.makeText(MainActivity.this,"Hello",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    private void replacePosition(int position){
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new Fragment1();
                break;
            case 1:
                fragment =new Fragment2();
                break;
            default:
                fragment = new Fragment1();
                break;
        }
        if (null!=fragment){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_content,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
