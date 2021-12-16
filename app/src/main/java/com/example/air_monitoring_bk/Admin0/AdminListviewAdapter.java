package com.example.air_monitoring_bk.Admin0;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ZygotePreload;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.air_monitoring_bk.Admin.Admin;
import com.example.air_monitoring_bk.Admin.Login;
import com.example.air_monitoring_bk.MainActivity;
import com.example.air_monitoring_bk.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Map;

public class AdminListviewAdapter extends BaseAdapter {

    Admin myContext;
    List<AdminItem> adminListView;
    public ImageView editAdmin;
    public ImageView deleteAdmin;
    public TextView nameAdmin;
    public TextView emailAdmin;
    public View view;


    public AdminListviewAdapter(Admin context, List<AdminItem> adminListView) {
        myContext = context;
        this.adminListView = adminListView;
    }

    @Override
    public int getCount() {
        if(adminListView != null){
            return adminListView.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        AdminItem adminItem = adminListView.get(position);
        if (adminItem != null){
            return adminListView.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = myContext.getLayoutInflater();
        convertView = inflater.inflate(R.layout.admin_listview,null);

        nameAdmin = convertView.findViewById(R.id.nameAdmin);
        emailAdmin = convertView.findViewById(R.id.emailAdmin);
        ImageView imageView = convertView.findViewById(R.id.imageAdmin);
        editAdmin = convertView.findViewById(R.id.edit_admin_btn);
        deleteAdmin = convertView.findViewById(R.id.delete_admin_btn);

        HideShowEdit();

        emailAdmin.setText(adminListView.get(position).getEmail());
        nameAdmin.setText(MainActivity.getNameAdmin(adminListView.get(position).getEmail()));

        editAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view = v;
                openFeedbackDialog(Gravity.CENTER,  position);
            }
        });

        deleteAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this account")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                adminListView.remove(position);
//                                Admin.adapter.notifyDataSetChanged();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("User_Account");
                                AdminItem adminItem = adminListView.get(position);
                                myRef.child(String.valueOf(adminItem.getId())).removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        Toast.makeText(v.getContext(), "Delete account successfully", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        return convertView;
    }

    private void HideShowEdit(){
        if(Admin.editAdminCheck){
            editAdmin.setVisibility(View.VISIBLE);
            deleteAdmin.setVisibility(View.VISIBLE);
        }else {
            editAdmin.setVisibility(View.GONE);
            deleteAdmin.setVisibility(View.GONE);
        }
    }

    private void openFeedbackDialog(int gravity, int position) {
        Dialog dialog = new Dialog(view.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_feeback_account);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(false);

        TextInputEditText LoginEmail = dialog.findViewById(R.id.login_email);
        TextInputEditText LoginPass = dialog.findViewById(R.id.login_password);
        LoginEmail.setText(adminListView.get(position).getEmail());
        LoginPass.setText(adminListView.get(position).getPassword());
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
                String email = LoginEmail.getText().toString().trim();
                String password = LoginPass.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(view.getContext(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
                    LoginEmail.requestFocus();
                }else if(TextUtils.isEmpty(password)){
                    Toast.makeText(view.getContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    LoginPass.requestFocus();
                }else{
                    if(!email.contains("@")){
                        Toast.makeText(view.getContext(), "Error Email", Toast.LENGTH_SHORT).show();
                    }
                    else if (!email.contains(adminListView.get(position).getEmail()) && Admin.ContainEmail(email)){
                        Toast.makeText(view.getContext(), "Email Already Exists", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("User_Account");
                        AdminItem adminItem = adminListView.get(position);
                        adminItem.setEmail(email);
                        adminItem.setPassword(password);
                        myRef.child(String.valueOf(adminItem.getId())).updateChildren(adminItem.toMap(),
                                new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        Toast.makeText(view.getContext(), "Update admin account success", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }


}
