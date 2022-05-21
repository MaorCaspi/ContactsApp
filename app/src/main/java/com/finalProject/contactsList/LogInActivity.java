package com.finalProject.contactsList;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.finalProject.contactsList.contact.BaseActivity;
import com.finalProject.contactsList.login.LoginActivity;
import com.finalProject.contactsList.model.Model;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Model.instance.executor.execute(() -> {
            if (Model.instance.isSignedIn()){
                Model.instance.mainThread.post(() -> {
                    toFeedActivity();
                });
            }else{
                Model.instance.mainThread.post(() -> {
                    toLoginActivity();
                });
            }
        });
    }

    private void toLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void toFeedActivity() {
        Intent intent = new Intent(this, BaseActivity.class);
        startActivity(intent);
        finish();
    }
}