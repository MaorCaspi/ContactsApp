package com.finalProject.contactsList.contact;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.finalProject.contactsList.model.Model;
import com.finalProject.contactsList.model.Contact;
import com.finalProject.contactsList.R;
import java.util.List;

public class ContactListRvFragment extends Fragment {
    List<Contact> data;
    MyAdapter adapter;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts_list,container,false);

        progressBar = view.findViewById(R.id.contactsList_progressbar);
        progressBar.setVisibility(View.GONE);
        RecyclerView list = view.findViewById(R.id.contactsList_rv);
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MyAdapter();
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v,int position) {
                Navigation.findNavController(v).navigate(ContactListRvFragmentDirections.actionContactListRvFragmentToContactDetailsFragment(position));
            }
        });

        setHasOptionsMenu(true);
        refresh();
        return view;
    }

    private void refresh() {
        progressBar.setVisibility(View.VISIBLE);
        Model.instance.getAllContacts((list)->{
            data = list;
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView fnameTv;
        TextView phoneNumberTv;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            fnameTv = itemView.findViewById(R.id.listrow_fName_tv);
            phoneNumberTv = itemView.findViewById(R.id.listrow_phoneNumber_tv);
            itemView.setOnClickListener((v)->{
                int pos = getAdapterPosition();
                listener.onItemClick(v,pos);
            });

        }
    }

    interface OnItemClickListener{
        void onItemClick(View v,int position);
    }
    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        OnItemClickListener listener;
        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.contact_list_row,parent,false);
            MyViewHolder holder = new MyViewHolder(view,listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Contact contact = data.get(position);
            holder.fnameTv.setText(contact.getfName());
            holder.phoneNumberTv.setText(contact.getPhoneNumber());
        }

        @Override
        public int getItemCount() {
            if(data == null){
                return 0;
            }
            return data.size();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.contact_list_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addContactFragment){
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}