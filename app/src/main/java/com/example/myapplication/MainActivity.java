package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.ui.home.HomeViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.stream.Collectors;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import tech.gusavila92.websocketclient.WebSocketClient;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Button button;
    private TextView status;
    private WebSocketClient webSocketClient;
    private ListView listView;
    public static boolean isLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null)
        {
            createWebSocketClient(null);
        }
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        status = findViewById(R.id.menu_name);
        listView = findViewById(R.id.zone_list);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }


    private void createWebSocketClient(String message_to_server) {
        URI uri = null;
        try {
            uri=new URI("ws://217.78.177.181:1456/");
        }
        catch (URISyntaxException e){
            e.printStackTrace();
        }
        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {
                Log.i("Websocket","Session is starting");
                if(MainActivity.isLogin==false) {
                    webSocketClient.send("{  \"method\": \"auth\",  \"data\": {    \"email\": \"alex@gmail.com\",    \"password\": \"qweASD123\",    \"deviceId\": \"HASJ2-009A-2933-12GASJJWQP{\"  }}\"");
                }
            }

            @Override
            public void onTextReceived(String message) {
                Log.i("Websocket","Message received");
                try {
                    JSONObject jsonObject = new JSONObject(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onBinaryReceived(byte[] data) {
                try {
                    JSONObject jsonObject = new JSONObject(new String(data));
                    if (jsonObject.getInt("status")==1){
                        MainActivity.isLogin=true;
                        status.setText("Connected");
                    }
                    else MainActivity.isLogin=false;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPingReceived(byte[] data) {

            }

            @Override
            public void onPongReceived(byte[] data) {

            }

            @Override
            public void onException(Exception e) {

            }

            @Override
            public void onCloseReceived() {
                Log.i("Websocket","Closed");
                System.out.println("onCloseReceived");
            }
        };
        webSocketClient.setConnectTimeout(10000);
        webSocketClient.setReadTimeout(60000);
        webSocketClient.enableAutomaticReconnection(5000);
        webSocketClient.addHeader("Origin", "http://developer.example.com");
        webSocketClient.connect();
    }


}
