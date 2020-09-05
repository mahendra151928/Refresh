package com.example.refresh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    ArrayList<String>courses=new ArrayList<>(Arrays.asList("mahe","ravi","sai","vijaya"));
    RecyclerSwiperAdaptr recyclerSwiperAdaptr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.refresh);
        recyclerView = findViewById(R.id.recycle);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                courses.add("m");
                courses.add("r");
                courses.add("s");
                courses.add("v");

                recyclerSwiperAdaptr.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);

        itemTouchHelper.attachToRecyclerView(recyclerView);


        recyclerSwiperAdaptr = new RecyclerSwiperAdaptr(MainActivity.this, courses);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerSwiperAdaptr);
    }

    String deleteditems=null;
    ArrayList<String>archieved=new ArrayList<>();
    ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            final int position=viewHolder.getAdapterPosition();
            switch (direction){
                case ItemTouchHelper.LEFT:deleteditems=courses.get(position);
                courses.remove(position);
                recyclerSwiperAdaptr.notifyItemRemoved(position);
                    Snackbar.make(recyclerView,deleteditems,Snackbar.LENGTH_LONG).setAction("undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            courses.add(position,deleteditems);
                            recyclerSwiperAdaptr.notifyItemInserted(position);
                        }
                    }).show();
                    break;
                case ItemTouchHelper.RIGHT:
                    final String coursename=courses.get(position);
                archieved.add(coursename);
                courses.remove(position);
                recyclerSwiperAdaptr.notifyItemRemoved(position);
                Snackbar.make(recyclerView,coursename + ",archived",Snackbar.LENGTH_LONG ).setAction("undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        archieved.remove(archieved.lastIndexOf(coursename));
                        courses.add(position,coursename);
                        recyclerSwiperAdaptr.notifyItemInserted(position);
                    }
                }).show();
                break;

            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary))
                    .addSwipeRightActionIcon(R.drawable.ic_archive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent))

                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
}
