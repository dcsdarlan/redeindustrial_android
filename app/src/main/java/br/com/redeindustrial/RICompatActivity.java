package br.com.redeindustrial;

import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;

public class RICompatActivity extends AppCompatActivity {

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
