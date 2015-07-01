package org.hsbp.urc;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class Main extends Activity
{
    private Sender sender;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        sender = new Sender();
        sender.start();
        attachEventHandlers();
    }

    private void attachEventHandlers() {
        for (final Action a : Action.values()) {
            final Button b = (Button)findViewById(a.res);
            if (a.repeat) {
                b.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                sender.send(a);
                                break;
                            case MotionEvent.ACTION_UP:
                                sender.stop(a);
                                break;
                        }
                        return false;
                    }
                });
            } else {
                b.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        sender.send(a);
                    }
                });
            }
        }
        SeekBar sb = (SeekBar)findViewById(R.id.pause);
        sb.setProgress(Sender.DEFAULT_PAUSE);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {}
            public void onStartTrackingTouch(SeekBar seekBar) {}

            public void onProgressChanged(SeekBar seekBar, int progress,
                    boolean fromUser) {
                sender.setPause(progress);
            }
        });
    }

    public void setTarget(View v) {
        EditText et = (EditText)findViewById(R.id.target);
        sender.setAddress(et.getText().toString());
    }
}
