package com.finalProject.contactsList.contact;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.finalProject.contactsList.model.Contact;
import com.finalProject.contactsList.model.Model;
import com.finalProject.contactsList.R;
import com.google.firebase.auth.FirebaseAuth;
import java.util.UUID;

public class AddContactFragment extends Fragment {
    EditText fNameEt;
    EditText lNameEt;
    EditText phoneNumberEt;
    Button saveBtn;
    Button cancelBtn;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_contact,container, false);
        fNameEt = view.findViewById(R.id.main_fName_et);
        lNameEt = view.findViewById(R.id.main_emailAddress_et);
        phoneNumberEt = view.findViewById(R.id.main_phoneNumber_et);
        saveBtn = view.findViewById(R.id.main_save_btn);
        cancelBtn = view.findViewById(R.id.main_cancel_btn);
        progressBar = view.findViewById(R.id.main_progressbar);
        progressBar.setVisibility(View.GONE);

        saveBtn.setOnClickListener((v)->{
            save();
        });

        cancelBtn.setOnClickListener((v)->{
            Navigation.findNavController(v).navigateUp();
        });
        return view;
    }

    private void save() {
        progressBar.setVisibility(View.VISIBLE);
        saveBtn.setEnabled(false);
        cancelBtn.setEnabled(false);
        String id = UUID.randomUUID().toString();
        String fName = fNameEt.getText().toString();
        String lName = lNameEt.getText().toString();
        String phoneNumber = phoneNumberEt.getText().toString();
        String userId= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Contact contact = new Contact(id,fName,lName,phoneNumber,"Male",userId);
        Model.instance.addContact(contact);
        Navigation.findNavController(getView()).navigateUp();
    }
}