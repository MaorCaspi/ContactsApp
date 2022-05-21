package com.finalProject.contactsList.contact;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.finalProject.contactsList.model.Contact;
import com.finalProject.contactsList.model.Model;
import com.finalProject.contactsList.R;

public class ContactDetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_details, container, false);

        int position = (int) ContactDetailsFragmentArgs.fromBundle(getArguments()).getPosition();
        Contact contact = Model.instance.getContactByPosition(position);

        TextView fNameTv = view.findViewById(R.id.details_fName_tv);
        TextView lNameTv = view.findViewById(R.id.details_lName_tv);
        TextView phoneNumberTV = view.findViewById(R.id.details_phoneNumber_tv);
        TextView genderTv = view.findViewById(R.id.details_gender_tv);

        fNameTv.setText(contact.getfName());
        lNameTv.setText(contact.getlName());
        phoneNumberTV.setText(contact.getPhoneNumber());
        genderTv.setText(contact.getGender());

        Button backBtn = view.findViewById(R.id.details_back_btn);
        backBtn.setOnClickListener((v)->{
            Navigation.findNavController(v).navigateUp();
        });

        Button deleteBtn = view.findViewById(R.id.details_delete_btn);
        deleteBtn.setOnClickListener((v)->{
            Model.instance.deleteContactByPosition(position);
            Navigation.findNavController(v).navigateUp();
        });

        Button editBtn = view.findViewById(R.id.details_edit_btn);
        editBtn.setOnClickListener((v)->{
            Navigation.findNavController(v).navigate(ContactDetailsFragmentDirections.actionContactDetailsFragmentToEditContactFragment(position));
        });

        return view;
    }
}