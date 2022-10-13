package com.anaypant.qrated.Fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anaypant.qrated.Adapters.NewsAdapter;
import com.anaypant.qrated.Frames.ModelNews;
import com.anaypant.qrated.Interfaces.ButtonCallback;
import com.anaypant.qrated.Interfaces.FirebaseNewsCallback;
import com.anaypant.qrated.R;
import com.anaypant.qrated.utils.firebaseUtils;

import java.util.ArrayList;

public class TrendingFragment extends Fragment {

    private static final String ARG_PARAM1 = "tabName";
    private RecyclerView recyclerView;
    NewsAdapter adapter;
    ArrayList<ModelNews> news = new ArrayList<>();
    private String tabName;
    private Parcelable recyclerViewState;
    private int topPosition = 0;



    public TrendingFragment() {this.tabName = "TrendingNews";}
    public TrendingFragment(int top) {this.tabName = "TrendingNews"; topPosition = top;}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tabName = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_trending, container, false);

        recyclerView = v.findViewById(R.id.recycleviewoftrending);
        firebaseUtils.findNewsFromDB(tabName, new FirebaseNewsCallback() {
            @Override
            public void onDBCallback(ArrayList<ModelNews> val) {
                news = val;
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new NewsAdapter(getContext(), news, tabName, new ButtonCallback() {
                    @Override
                    public void onCallback(int pos) {

                        adapter.notifyItemChanged(pos);
                        recyclerView.smoothScrollToPosition(pos);
                    }
                });
                recyclerView.setAdapter(adapter);
                recyclerView.smoothScrollToPosition(topPosition);
            }
        });

        return v;
    }


}