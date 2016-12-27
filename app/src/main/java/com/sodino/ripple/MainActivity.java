package com.sodino.ripple;

import android.graphics.drawable.RippleDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
// //
// //
// //
// //
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager llMgr = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llMgr);
        recyclerView.setAdapter(new RippleAdapter());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, llMgr.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        LinearLayout toolbar = ((LinearLayout)findViewById(R.id.toolbar));
        int count = toolbar.getChildCount();
        for (int i = 0;i < count;i ++) {
            View v = toolbar.getChildAt(i);
            v.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
