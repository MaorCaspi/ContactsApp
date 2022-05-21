package com.finalProject.contactsList.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.finalProject.contactsList.R;
import com.finalProject.contactsList.contact.BaseActivity;
import com.finalProject.contactsList.model.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth;
    EditText fullNameEt;
    EditText emailEt;
    EditText passwordEt;
    Button registerBtn;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        emailEt = view.findViewById(R.id.register_email_et);
        passwordEt = view.findViewById(R.id.register_password_et);
        registerBtn = view.findViewById(R.id.register_register_btn);
        progressBar = view.findViewById(R.id.register_progressbar);
        progressBar.setVisibility(View.GONE);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(v -> {
            String fullName=fullNameEt.getText().toString();
            String email=emailEt.getText().toString();
            String password=passwordEt.getText().toString();
            if(fullName.equals("") || email.equals("") || password.equals("")){
                Toast.makeText(getContext(), "fields can not be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            register(email,password);
        });
        return view;
    }

    private void register(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        registerBtn.setEnabled(false);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            toMainActivity();
                        }
                        else {//If the registration was failed
                            progressBar.setVisibility(View.GONE);
                            registerBtn.setEnabled(true);
                            //Display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
    }

    private void toMainActivity() {
        Model.instance.executor.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                Intent intent = new Intent(getContext(), BaseActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}