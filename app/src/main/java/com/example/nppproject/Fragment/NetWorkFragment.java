package com.example.nppproject.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.nppproject.Adapter.SaveAdapter;
import com.example.nppproject.Public.PublicMethod;
import com.example.nppproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NetWorkFragment extends Fragment {
    String rssUrl;
    ProgressBar pgBar;
    Button btnConnect;
    PublicMethod publicMethod = new PublicMethod();

    public NetWorkFragment() {
        // Required empty public constructor
    }

    public static NetWorkFragment newInstance(String rssUrl) {

        Bundle args = new Bundle();
        args.putString("rssUrl", rssUrl);
        NetWorkFragment fragment = new NetWorkFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_net_work, container, false);
        btnConnect = view.findViewById(R.id.btnConnect);
        pgBar = view.findViewById(R.id.pgBar);
        //imgNetWork = view.findViewById(R.id.imgNetwork);
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            NetWork();
            }
        });
        return view;
    }

    public void NetWork() {
        if (publicMethod.checkConnectInternet(getContext()) == true) {
            HomeFragment homeFragment = HomeFragment.newInstance(rssUrl);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
        }
    }


}
