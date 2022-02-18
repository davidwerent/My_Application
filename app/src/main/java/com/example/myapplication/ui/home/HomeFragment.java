package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

import tech.gusavila92.websocketclient.WebSocketClient;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private ArrayAdapter<String> adapter;
    private ListView listView;
    final String[] catNames = new String[] {
            "Рыжик", "Барсик", "Мурзик", "Пушок"
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listView = binding.zoneList;

        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, catNames);
        listView.setAdapter(adapter);
        final TextView textView = binding.menuName;

        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}