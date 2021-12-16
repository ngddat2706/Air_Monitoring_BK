package com.example.air_monitoring_bk.Admin;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.air_monitoring_bk.Admin0.AdminItem;
import com.example.air_monitoring_bk.Admin0.AdminListviewAdapter;
import com.example.air_monitoring_bk.MainActivity;
import com.example.air_monitoring_bk.R;
import com.example.air_monitoring_bk.Station.ExpandableListViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Admin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Admin extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static Boolean editAdminCheck;
    public ImageView editAdminbtn;
    public ListView listViewAdmin;
    public static AdminListviewAdapter adapter;
    public static List<AdminItem> Admin_List;

    Admin myContext;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Admin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Admin.
     */
    // TODO: Rename and change types and number of parameters
    public static Admin newInstance(String param1, String param2) {
        Admin fragment = new Admin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        editAdminbtn = view.findViewById(R.id.edit_admin_btn);
        ImageView addAdminbtn = view.findViewById(R.id.add_admin_btn);
        listViewAdmin = view.findViewById(R.id.ListViewAdmin);
        Admin_List = new ArrayList<>();
        //onClickGetDataAdminList();
        Admin_List = MainActivity.Admin_List;
        editAdminCheck = false;
        adapter = new AdminListviewAdapter(this,Admin_List);
        listViewAdmin.setAdapter(adapter);

        addAdminbtn.setVisibility(View.GONE);
        editAdminbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAdminCheck = !editAdminCheck;
                if(editAdminCheck){
                    editAdminbtn.setImageResource(R.drawable.done_icon);
                    addAdminbtn.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                }else {
                    editAdminbtn.setImageResource(R.drawable.edit_icon);
                    addAdminbtn.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        addAdminbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeedbackDialog();
            }
        });

        return view;
    }

    private void openFeedbackDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_feeback_account);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(false);

        TextInputEditText LoginEmail = dialog.findViewById(R.id.login_email);
        TextInputEditText LoginPass = dialog.findViewById(R.id.login_password);
        Button Cancelbtn = dialog.findViewById(R.id.cancel_button);
        Button Sendbtn = dialog.findViewById(R.id.send_button);

        Cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = LoginEmail.getText().toString();
                String password = LoginPass.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getActivity(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
                    LoginEmail.requestFocus();
                }else if(TextUtils.isEmpty(password)){
                    Toast.makeText(getActivity(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    LoginPass.requestFocus();
                }else{
                    if(!email.contains("@")){
                        Toast.makeText(getActivity(), "Error Email", Toast.LENGTH_SHORT).show();
                    }
                    else if (ContainEmail(email)){
                        Toast.makeText(getActivity(), "Email Already Exists", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        AdminItem adminItem = new AdminItem(MainActivity.Admin_List.size(), email,password);
                        onClickAddUser(adminItem);
                    }
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    public static boolean ContainEmail(String email){
        for(int i=0; i<MainActivity.Admin_List.size(); i++){
            if(MainActivity.Admin_List.get(i).getEmail().contains(email)){
                return true;
            }
        }
        return false;
    }

    public void onClickAddUser(AdminItem adminItem){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User_Account");
        String pathObject = String.valueOf(adminItem.getId());
        myRef.child(pathObject).setValue(adminItem, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getActivity(), "Create admin account success", Toast.LENGTH_SHORT).show();
            }
        });
    }

}