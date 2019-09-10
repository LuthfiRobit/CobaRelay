package luthfi.unuja.cobarelay;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference RELAY_CONTROL = database.getReference().child("LED1");

    TextView tvcoba, tvcoba1;
    SwitchCompat scPower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvcoba = (TextView) findViewById(R.id.coba);
        tvcoba1 = (TextView) findViewById(R.id.coba1);
        scPower = (SwitchCompat) findViewById(R.id.scPower);
        scPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scPower.isChecked()) {
                    {
                        RELAY_CONTROL.child("RELAY_ON").setValue("ON");
                        RELAY_CONTROL.child("RELAY_OFF").setValue("OFF");
                    }
                } else {
                    {
                        RELAY_CONTROL.child("RELAY_ON").setValue("OFF");
                        RELAY_CONTROL.child("RELAY_OFF").setValue("ON");
                    }
                }
            }
        });

        RELAY_CONTROL.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value1 = dataSnapshot.child("RELAY_ON").getValue(String.class);
                String value2 = dataSnapshot.child("RELAY_OFF").getValue(String.class);
                tvcoba.setText(value1);
                tvcoba1.setText(value2);
                if (value1.toString().equals("ON") && value2.toString().equals("OFF")) {
                    scPower.setChecked(true);
                } else if (value1.toString().equals("OFF") && value2.toString().equals("ON")) {
                    scPower.setChecked(false);
                } else {
                    scPower.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }
}
