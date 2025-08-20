package org.xedox.blocklist.demo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.List;
import org.xedox.blocklist.util.EventItem;
import java.util.ArrayList;
import org.xedox.blocklist.util.BlockItem;
import org.xedox.blocklist.widget.BlockListView;

public class MainActivity extends AppCompatActivity {

    private BlockListView blocks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            blocks = findViewById(R.id.blocks);
        try {
            loadDemo();
        } catch (Exception err) {
            err.printStackTrace();
            new AlertDialog.Builder(this).setMessage(err.getMessage()).show();
        }
    }

    public void loadDemo() {
        List<EventItem> events = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            EventItem ev = new EventItem(i + ". " + "Event name");
            for (int ii = 0; ii < 10; ++ii) {
                ev.addBlock(new BlockItem(ii + ". " + "Block name"));
            }
            events.add(ev);
        }
        blocks.setEvents(events);
    }
}
