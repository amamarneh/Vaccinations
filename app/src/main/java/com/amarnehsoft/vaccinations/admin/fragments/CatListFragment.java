package com.amarnehsoft.vaccinations.admin.fragments;


import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.admin.activities.AddEditCatActivity;
import com.amarnehsoft.vaccinations.beans.Cat;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.app.Activity.RESULT_OK;


public class CatListFragment extends Fragment {
    private RecyclerView recyclerView;
    public static final int MODE_SELECT = 1;
    public static final int MODE_NORMAL = 0;
    private int mMode = MODE_NORMAL;
    public CatListFragment() {
    }

    public static Fragment newInstance(int mode) {
        Fragment fragment = new CatListFragment();
        Bundle args = new Bundle();
        args.putInt("mode",mode);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cat_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerView);
        mMode = getArguments().getInt("mode");
        FirebaseRecyclerAdapter<Cat,MyHolder> adapter = new FirebaseRecyclerAdapter<Cat, MyHolder>(
                Cat.class,
                R.layout.row_cat,
                MyHolder.class,
                FirebaseDatabase.getInstance().getReference().child("cats")
        ) {
            @Override
            protected void populateViewHolder(MyHolder viewHolder, final Cat model, int position) {
                viewHolder.bind(model,mMode);
                viewHolder.setListener(new MyHolder.Listener() {
                    @Override
                    public void onCatClicked(Cat cat) {
                        Log.d("Amarneh","click");
                        if(mMode == MODE_NORMAL){
                            Intent i = AddEditCatActivity.newIntent(getContext(),cat);
                            getContext().startActivity(i);
                        }else{
                            Intent intent = new Intent();
                            intent.putExtra("data",model);
                            getActivity().setResult(RESULT_OK,intent);
                            getActivity().finish();
                        }
                    }
                });
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    public static class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView img;
        private TextView txtName;
        private Cat mCat;
        private int mMode;
        private Listener listener;
        public MyHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            txtName = itemView.findViewById(R.id.txtName);


            itemView.setOnClickListener(this);
        }
        public void bind(Cat cat, int mode){
            mMode = mode;
            mCat = cat;
            Glide.with(itemView.getContext()).load(cat.getImg()).into(img);
            txtName.setText(cat.getName());
        }
        public void setListener(Listener listener){
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
           if(listener != null)
               listener.onCatClicked(mCat);
        }
        public interface Listener{
            void onCatClicked(Cat cat);
        }
    }
}
