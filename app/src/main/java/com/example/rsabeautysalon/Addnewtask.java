package com.example.rsabeautysalon;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rsabeautysalon.model.DB_Utill;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Addnewtask extends BottomSheetDialogFragment {

    public static final String TAG = "AddNewTask";

    private TextView setDueDate;
    private EditText mTaskEdit;
    private Button mSaveBtn;
    private FirebaseFirestore firestore;
    private Context context;
    private String dueDate = "";
    private String id = "";
    private String dueDateUpdate = "";
    private DB_Utill db_utill= new DB_Utill();
    private Spinner mspinner;
    private List<String> services;
    private ArrayAdapter<String> dataAdapter;
    private String task ;
    private boolean isLocationSelected=false;
    private String location;
    private RadioButton radioButtonInHouse;
    private RadioButton radioButtonInSaloon;

    public static Addnewtask newInstance(){
        return new Addnewtask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_task , container , false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setDueDate = view.findViewById(R.id.set_due_tv);
        //   mTaskEdit = view.findViewById(R.id.task_edittext);

//        Spinner Init
        mspinner= view.findViewById(R.id.spinner_add_new_task);
        services= getServices();
        radioButtonInHouse=view.findViewById(R.id.inHouseLocationRadioButton);
        radioButtonInHouse.setOnClickListener(this::onRadioButtonSelected);
        radioButtonInSaloon=view.findViewById(R.id.inSaloonLocationRadioButton);
        radioButtonInSaloon.setOnClickListener(this::onRadioButtonSelected);
        dataAdapter= setSpinnerAdapter(context,(ArrayList<String>) services);
        mspinner.setAdapter(dataAdapter);
        mspinner.setOnItemSelectedListener(getListener(services));
        //      Spinner Init ends here

        mSaveBtn = view.findViewById(R.id.save);

        firestore = FirebaseFirestore.getInstance();

        boolean isUpdate = false;

        final Bundle bundle = getArguments();
        if (bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            id = bundle.getString("id");
            dueDateUpdate = bundle.getString("due");

            //    mTaskEdit.setText(task);
            mspinner.setSelection(dataAdapter.getPosition(task));
            setDueDate.setText(dueDateUpdate);

//            if (task.length() > 0){
//                mSaveBtn.setEnabled(false);
//                mSaveBtn.setBackgroundColor(Color.GRAY);
//            }
        }

//        mTaskEdit.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.toString().equals("")){
//                    mSaveBtn.setEnabled(false);
//                    mSaveBtn.setBackgroundColor(Color.GRAY);
//                }else{
//                    mSaveBtn.setEnabled(true);
//                    mSaveBtn.setBackgroundColor(getResources().getColor(R.color.black));
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        setDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                int MONTH = calendar.get(Calendar.MONTH);
                int YEAR = calendar.get(Calendar.YEAR);
                int DAY = calendar.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        setDueDate.setText(dayOfMonth + "/" + month + "/" + year);
                        dueDate = dayOfMonth + "/" + month +"/"+year;

                    }
                } , YEAR , MONTH , DAY);

                datePickerDialog.show();
            }
        });

        boolean finalIsUpdate = isUpdate;
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (finalIsUpdate){

                    // firestore.collection("task").document(id).update("task" , task , "due" , dueDate);
                    firestore.collection("task")
                            .document(db_utill.getCurrentUserID())
                            .collection(db_utill.getCurrentUserID()+"tasks").document(id)
                            .update("task",task,"due",dueDate,"location",location);

                    Toast.makeText(context, "Task Updated", Toast.LENGTH_SHORT).show();

                }
                else {
                    if (task.isEmpty() || task.equals("Services")) {
                        Toast.makeText(context, "Please Select a Service !!", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        if (!isLocationSelected){
                            Toast.makeText(context, "Please Select a Location !!", Toast.LENGTH_SHORT).show();
                            return;
                        }else {

                        Map<String, Object> taskMap = new HashMap<>();

                        taskMap.put("task", task);
                        taskMap.put("due", dueDate);
                        taskMap.put("status", 0);
                        taskMap.put("time", FieldValue.serverTimestamp());
                        taskMap.put("location",location);

                        firestore.collection("task")
                                .document(db_utill.getCurrentUserID())
                                .collection(db_utill.getCurrentUserID()+"tasks").document().set(taskMap).addOnCompleteListener(new OnCompleteListener<Void>() {


                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "onComplete: Task Saved!!");
                                    Toast.makeText(context, "Task Saved", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.d(TAG, "onComplete: Failed "+task.getException().getMessage());

                                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onComplete: Failed "+e.getMessage());

                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }}
                dismiss();
            }
        });
    }



    private AdapterView.OnItemSelectedListener getListener(List<String> services) {
        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                task= services.get(i);
                Toast.makeText(context, services.get(i), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                ;
            }
        };
        return listener;
    }

    private List<String> getServices() {
        //for now I'm hard coding the data
        // we can fetch this from firestore later
        List<String> myServices = new ArrayList<>();
        myServices.add("Services");
        myServices.add("Make Up");
        myServices.add("Hair Color");
        myServices.add("Hair Cut");
        myServices.add("Manicures");
        myServices.add("Pedicures");
        myServices.add("Other");

        return myServices;
    }

    public void onRadioButtonSelected(View view){
        isLocationSelected= true;
        if (isLocationSelected){

            switch (view.getId()){

                case R.id.inHouseLocationRadioButton:{
                    location= "In House";
                    break;
                }
                case R.id.inSaloonLocationRadioButton:{

                    location= "In Saloon";
                            break;
                }


            }
        }

    }

    private ArrayAdapter<String> setSpinnerAdapter(Context context,ArrayList<String> tservices) {
// Creating adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, tservices) {

            //overriding adapter is method so we can disable very first element of list, and will use it as a hint of spinner
            @Override
            public boolean isEnabled(int position) {

                return position != 0;

            }

            public View getDropDownView(int position, @Nullable View convertView, ViewGroup parent) {

                TextView view = (TextView) super.getDropDownView(position, convertView, parent);
                //set the color of first item in the drop down list to gray to indicate that, its not a value for selection
                if (position == 0) {
                    view.setTextColor(Color.GRAY);
                } else {
                    //here it is possible to define color for other items by
                   // view.setTextColor(Color.WHITE);
                }
                return view;

            }
        };

        // For now,using default layout here, you can design your own and use it here.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof  OnDialogCloseListner){
            ((OnDialogCloseListner)activity).onDialogClose(dialog);
        }
    }
}
