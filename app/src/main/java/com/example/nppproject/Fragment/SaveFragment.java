package com.example.nppproject.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nppproject.Adapter.SaveAdapter;
import com.example.nppproject.Entity.ContentSaveEntity;
import com.example.nppproject.R;
import com.example.nppproject.SQL.SQLHelper;

import java.util.List;

public class SaveFragment extends Fragment {
    RecyclerView rcvSave;
    SQLHelper sqlHelper;
    List<ContentSaveEntity> ListSave;
    public static SaveFragment newInstance() {

        Bundle args = new Bundle();

        SaveFragment fragment = new SaveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_save, container, false);
        rcvSave= view.findViewById(R.id.rcvSave);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvSave.setLayoutManager(layoutManager);
        sqlHelper= new SQLHelper(getContext());
        ListSave= sqlHelper.getAllContent();

        SaveAdapter adapter= new SaveAdapter(getContext(), ListSave);
        rcvSave.setAdapter(adapter);
        return view;
    }
}
