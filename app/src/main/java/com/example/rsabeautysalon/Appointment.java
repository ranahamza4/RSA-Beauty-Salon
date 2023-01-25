package com.example.rsabeautysalon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.rsabeautysalon.adapter.ToDoAdapter;
import com.example.rsabeautysalon.model.DB_Utill;
import com.example.rsabeautysalon.model.ToDoModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Appointment extends AppCompatActivity implements OnDialogCloseListner{

    private RecyclerView recyclerView;
    private FloatingActionButton mFab;
    private FirebaseFirestore firestore;
    private ToDoAdapter adapter;
    private List<ToDoModel> mList;
    private Query query;
    private ListenerRegistration listenerRegistration;
    private DB_Utill db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        BottomNavigationView bottomNavigationView = findViewById( R.id.bottom_nav );
        bottomNavigationView.setSelectedItemId(R.id.mappoint);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected (@NonNull MenuItem item){
                switch (item.getItemId()) {

                    case R.id.mHome:
                        startActivity( new Intent( getApplicationContext(), DashBoard.class ) );
                        overridePendingTransition( 0, 0 );
                        return true;

                    case R.id.mcontact:
                        startActivity( new Intent( getApplicationContext(), Contact.class ) );
                        overridePendingTransition( 0, 0 );
                        return true;

                    case R.id.mappoint:
                        return true;
                    case R.id.mabout:
                        startActivity( new Intent( getApplicationContext(), About.class ) );
                        overridePendingTransition( 0, 0 );
                        return true;

                    case R.id.mprofile:
                        startActivity( new Intent( getApplicationContext(), MainActivity.class ) );
                        overridePendingTransition( 0, 0 );
                        return true;
                }
                return false;
            }

        });
        db = new DB_Utill();
        recyclerView = findViewById(R.id.recyclerview);
        mFab = findViewById(R.id.floatingActionButton);
        firestore = FirebaseFirestore.getInstance();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Appointment.this));

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Addnewtask.newInstance().show(getSupportFragmentManager() , Addnewtask.TAG);
            }
        });

        mList = new ArrayList<>();
        adapter = new ToDoAdapter(Appointment.this , mList);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        showData();
        recyclerView.setAdapter(adapter);
    }
    private void showData(){
        query = firestore.collection("task").document(db.getCurrentUserID()).collection(db.getCurrentUserID()+"tasks").orderBy("time" , Query.Direction.DESCENDING);
        listenerRegistration = query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange documentChange : value.getDocumentChanges()){
                    if (documentChange.getType() == DocumentChange.Type.ADDED){
                        String id = documentChange.getDocument().getId();
                        ToDoModel toDoModel = documentChange.getDocument().toObject(ToDoModel.class).withId(id);
                        mList.add(toDoModel);
                        adapter.notifyDataSetChanged();
                    }
                }
                listenerRegistration.remove();

            }
        });
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mList.clear();
        showData();
        adapter.notifyDataSetChanged();
    }
}