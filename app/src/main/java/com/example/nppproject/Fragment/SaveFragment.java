package com.example.nppproject.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
    Button btnDelete;

    public static SaveFragment newInstance() {

        Bundle args = new Bundle();

        SaveFragment fragment = new SaveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_save, container, false);
        rcvSave = view.findViewById(R.id.rcvSave);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvSave.setLayoutManager(layoutManager);
        sqlHelper = new SQLHelper(getContext());
        ListSave = sqlHelper.getAllContent();
        btnDelete=view.findViewById(R.id.btnDelAll);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlHelper.deleteAll();

            }
        });
        SaveAdapter adapter = new SaveAdapter(getContext(), ListSave);
        rcvSave.setAdapter(adapter);
        adapter.setOnClickListener(new SaveAdapter.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), "đã click", Toast.LENGTH_SHORT).show();
                ContentSaveFragment contentSaveFragment = ContentSaveFragment.newInstance(ListSave.get(position).getLink());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, contentSaveFragment, contentSaveFragment.getTag()).addToBackStack("stack").commit();
            }
        });
        adapter.notifyDataSetChanged();
        return view;
    }
}
