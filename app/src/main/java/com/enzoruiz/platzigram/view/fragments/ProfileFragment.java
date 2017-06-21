package com.enzoruiz.platzigram.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enzoruiz.platzigram.R;
import com.enzoruiz.platzigram.adapter.RecyclerViewAdapter;
import com.enzoruiz.platzigram.model.Picture;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        showToolbar("", false, view);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewProfile);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getDummyPictures(), R.layout.cardview_picture, getActivity());
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }

    public ArrayList<Picture> getDummyPictures(){
        ArrayList<Picture> pictures = new ArrayList<>();

        pictures.add(new Picture("Juan Perez Sanchez", "https://www.anipedia.net/imagenes/caracteristicas-generales-de-los-lobos.jpg", "4 dias", "17 Me Gusta"));
        pictures.add(new Picture("Luis Carranza Perez", "https://www.anipedia.net/imagenes/que-comen-los-perros.jpg", "2 dias", "8 Me Gusta"));
        pictures.add(new Picture("Enzo Ruiz Pelaez", "http://cdn01.ib.infobae.com/adjuntos/162/imagenes/014/129/0014129267.jpg", "8 dias", "15 Me Gusta"));
        pictures.add(new Picture("Cristiano Ronaldo", "https://www.mundoperro.net/wp-content/uploads/Perro-Carlino-485x300.jpg", "1 dias", "20 Me Gusta"));

        return pictures;
    }

    public void showToolbar(String titulo, boolean upButton, View view){
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(titulo);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

}
