package mobin.expandablerecyclerview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mobin.expandablerecyclerview.adapters.VerticalStepAdapter;
import mobin.expandablerecyclerview.models.VerticalStep;

public class VerticalStepsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.rvM);


        VerticalStepAdapter verticalStepAdapter = new VerticalStepAdapter(getList());


        recyclerView.setAdapter(verticalStepAdapter);

    }

    private ArrayList<VerticalStep> getList() {
        ArrayList<VerticalStep> verticalSteps = new ArrayList<>(3);
        VerticalStep verticalStep1 = new VerticalStep("Step 1", getString(R.string.step1));
        verticalStep1.setBtnText1("Accept");
        verticalStep1.setBtnText2("Read More");
        VerticalStep verticalStep2 = new VerticalStep("Step 2", getString(R.string.step2));
        verticalStep2.setBtnText1("ok");
        verticalStep2.setBtnText2("details");
        VerticalStep verticalStep3 = new VerticalStep("Step 3", getString(R.string.step3));
        verticalStep3.setBtnText1("done");
        verticalSteps.add(verticalStep1);
        verticalSteps.add(verticalStep2);
        verticalSteps.add(verticalStep3);
        return verticalSteps;
    }
}
