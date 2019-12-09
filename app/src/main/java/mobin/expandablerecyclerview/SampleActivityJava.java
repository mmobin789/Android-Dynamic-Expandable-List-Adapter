package mobin.expandablerecyclerview;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import mobin.expandablerecyclerview.adapters.MyAdapter;
import mobin.expandablerecyclerview.models.Child;
import mobin.expandablerecyclerview.models.Parent;

public class SampleActivityJava extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.rvM);
        ArrayList<Parent> list = new ArrayList<>(10);

        for (int i = 0; i < 10; i++)
            list.add(new Parent("Parent " + i));

        final MyAdapter myAdapter = new MyAdapter(list);

        myAdapter.setExpandableViewClickListener(new Function2<Parent, Integer, Unit>() {
            @Override
            public Unit invoke(Parent parent, Integer integer) {
                Toast.makeText(
                        SampleActivityJava.this,
                        parent.getName() + " Position: " + integer,
                        Toast.LENGTH_SHORT
                ).show();
                return null;
            }
        });

        myAdapter.setExpandedViewClickListener(new Function3<Child, Parent, Integer, Unit>() {
            @Override
            public Unit invoke(Child child, Parent parent, Integer integer) {
                Toast.makeText(
                        SampleActivityJava.this,
                        parent.getName() + " " + child.getName() + " Position: " + integer,
                        Toast.LENGTH_SHORT
                ).show();


                return null;
            }
        });


        myAdapter.setExpanded(true);

        recyclerView.setAdapter(myAdapter);

    }
}
