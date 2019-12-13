package mobin.expandablerecyclerview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mobin.expandablerecyclerview.adapters.MyAdapter;
import mobin.expandablerecyclerview.models.Parent;

public class SampleActivityForJava extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.rvM);
        ArrayList<Parent> list = new ArrayList<>(10);

        for (int i = 0; i < 10; i++)
            list.add(new Parent("Parent " + i));

        final MyAdapter myAdapter = new MyAdapter(list);


        myAdapter.setExpanded(true);

        recyclerView.setAdapter(myAdapter);

    }
}
