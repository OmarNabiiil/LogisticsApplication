package com.example.omar.logisticsapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.omar.logisticsapplication.classes.Product;
import com.example.omar.logisticsapplication.listsAdapters.CartListAdapter;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private static final String TAG = CartFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private Button add_user;
    private List<Product> products_list;
    private CartListAdapter mAdapter;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        products_list = new ArrayList<>();
        mAdapter = new CartListAdapter(products_list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        return view;
    }

}
