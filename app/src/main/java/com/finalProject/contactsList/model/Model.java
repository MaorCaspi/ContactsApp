package com.finalProject.contactsList.model;

import android.os.Handler;
import android.os.Looper;
import androidx.core.os.HandlerCompat;
import com.google.firebase.auth.FirebaseAuth;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {

    public static final Model instance = new Model();
    public Executor executor = Executors.newFixedThreadPool(1);
    public Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());

    private Model(){ }

    List<Contact> ContactList;

    public void editContact(Contact contact) {
        executor.execute(() -> {
            AppLocalDb.db.contactDao().delete(contact);//Delete contact from rom
        });
        addContact(contact);
    }

    public interface GetAllContactsListener{
        void onComplete(List<Contact> list);
    }
    public void getAllContacts(GetAllContactsListener listener){
        String userId= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        executor.execute(()->{
            ContactList = AppLocalDb.db.contactDao().getAll(userId);
            mainThread.post(()->{
                listener.onComplete(ContactList);
            });
        });
    }

    public void addContact(Contact contact){
        executor.execute(()->{
            AppLocalDb.db.contactDao().insertAll(contact);
        });
    }

    public Contact getContactByPosition(int position) {
        return ContactList.get(position);
    }

    public void deleteContactByPosition(int pos) {
        executor.execute(()->{
            Contact contact = getContactByPosition(pos);
            AppLocalDb.db.contactDao().delete(contact);
        });
    }

    /* Authentication */
    ModelFirebase modelFirebase = new ModelFirebase();

    public boolean isSignedIn() {
        return modelFirebase.isSignedIn();
    }
}