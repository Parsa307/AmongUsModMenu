package com.parsast.modmenu;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.widget.LinearLayout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.os.Handler;

public class MainActivity extends Activity {

    //Only if you have changed MainActivity to yours and you wanna call game's activity.
    public String GameActivity = "com.unity3d.player.UnityPlayerActivity";
    public boolean hasLaunched = false;

    //To call onCreate, please refer to README.md
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout[] stepLayouts = {
            findViewById(R.id.layout1),
            findViewById(R.id.layout2),
            findViewById(R.id.layout3)
        };

        int[] stepNumbers = {1, 2, 3}; // The array of case numbers
        for (int i = 0; i < stepLayouts.length; i++) {
            LinearLayout step = stepLayouts[i]; // Get the current layout
            int currentStep = stepNumbers[i];  // Get the corresponding case number

            TextView subtitle = step.findViewById(R.id.subtitle);
            TextView textheading = step.findViewById(R.id.textheading);
            final TextView textcode = step.findViewById(R.id.textcode);
            final TextView btncopy = step.findViewById(R.id.btncopy);

            switch (currentStep) {
                case 1:
                    subtitle.setText("1. Enable System Floating Window Permission (Insert the following code into the AndroidManifest.xml file)");
                    textheading.setText("Floating Window Permission");
                    textcode.setText("<uses-permission android:name=\"android.permission.SYSTEM_ALERT_WINDOW\"/>");
                    break;
                case 2:
                    subtitle.setText("2. Add Service for Mod Menu (Place the following code before the closing </application> tag in AndroidManifest.xml)");
                    textheading.setText("Service Configuration");
                    textcode.setText("<service android:name=\"com.parsast.modmenu.Launcher\"\nandroid:enabled=\"true\"\nandroid:exported=\"false\"\nandroid:stopWithTask=\"true\" />");
                    break;
                case 3:
                    subtitle.setText("3. Configure MainActivity of the Game (Locate the \"onCreate\" method and paste the following code inside it)");
                    textheading.setText("MainActivity Setup");
                    textcode.setText("invoke-static {p0}, Lcom/parsast/modmenu/Main;->Start(Landroid/content/Context;)V");
                    break;
            }

            btncopy.setOnClickListener(v -> {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(null, textcode.getText());
                clipboard.setPrimaryClip(clip);
                btncopy.setText("✓ Copied!");

                // Reset button text after 2 seconds
                new Handler().postDelayed(() -> btncopy.setText("❏ Copy Code"), 2000);
            });
        }
        
        //To launch game activity
        if (!hasLaunched) {
            try {
                //Start service
                hasLaunched = true;
                //Launch mod menu.
                MainActivity.this.startActivity(new Intent(MainActivity.this, Class.forName(MainActivity.this.GameActivity)));
                Main.Start(this);
                return;
            } catch (ClassNotFoundException e) {
                Log.e("Mod_menu", "Error. Game's main activity does not exist");
                //Uncomment this if you are following METHOD 2 to launch menu
                //Toast.makeText(MainActivity.this, "Error. Game's main activity does not exist", Toast.LENGTH_LONG).show();
            }
        }

        //Launch mod menu.
        Main.Start(this);
       // Main.StartWithoutPermission(this);
    }
}
