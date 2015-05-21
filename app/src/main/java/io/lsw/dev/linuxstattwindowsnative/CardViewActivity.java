package io.lsw.dev.linuxstattwindowsnative;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CardViewActivity extends Activity {

    TextView name;
    TextView description;
    ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cardview_activity);
        name = (TextView)findViewById(R.id.name);
        description = (TextView)findViewById(R.id.description);
        icon = (ImageView)findViewById(R.id.icon);

        name.setText("Emma Wilson");
        description.setText("23 years old");
        icon.setImageResource(R.drawable.ic_drawer);
    }
}
