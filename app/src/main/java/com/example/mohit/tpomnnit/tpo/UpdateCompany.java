package com.example.mohit.tpomnnit.tpo;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohit.tpomnnit.R;
import com.example.mohit.tpomnnit.student.company.Companies;
import com.example.mohit.tpomnnit.student.company.FoldingCellCompanyAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;

public class UpdateCompany extends AppCompatActivity {
    List<Companies> companiesList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase;
    String companyId;
    String currsel;
    ValueEventListener vel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_company);
        mDatabase=FirebaseDatabase.getInstance().getReference("companies");
        companyId=mDatabase.push().getKey();
        companiesList=new ArrayList<>();
        prepareData();
    }
    void prepareData()
    {
        vel=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userDetails:dataSnapshot.getChildren())
                {
                    Companies companies=new Companies();
                    companies.setName(userDetails.child("name").getValue().toString());
                    companies.setCtc(userDetails.child("ctc").getValue().toString());
                    companies.setLocation(userDetails.child("location").getValue().toString());
                    companies.setProfile(userDetails.child("profile").getValue().toString());
                    companies.setYear(userDetails.child("year").getValue().toString());
                    companies.setPpo(userDetails.child("ppo").getValue().toString());
                    companies.setCompanyid(userDetails.child("companyid").getValue().toString());
                    companies.setDeadline(userDetails.child("deadline").getValue().toString());
                    companies.setLink(userDetails.child("link").getValue().toString());
                    companies.setBranch(userDetails.child("branch").getValue().toString());
                    companies.setCpi(userDetails.child("cpi").getValue().toString());
                    companies.setClass10(userDetails.child("class10").getValue().toString());
                    companies.setClass12(userDetails.child("class12").getValue().toString());
                    companies.setNote(userDetails.child("note").getValue().toString());
                    companiesList.add(companies);
                }
                addCompany();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addListenerForSingleValueEvent(vel);
    }
    String compkey;
    void addCompany()
    {
        ListView theListView=(ListView)findViewById(R.id.mainListView);
        final FoldingCellCompanyAdapter adapter=new FoldingCellCompanyAdapter(UpdateCompany.this,companiesList,1);
        adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(UpdateCompany.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setContentView(R.layout.update_company_dialog);
                dialog.show();
                final TextView perm=(TextView) dialog.findViewById(R.id.text_dialog_feedback);
                final EditText updatemsg=(EditText)dialog.findViewById(R.id.regis);
                final Button update=(Button)dialog.findViewById(R.id.register);
                perm.setText("  Add update Message  ");
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!updatemsg.getText().toString().equals("")) {
                            compkey = "";
                            mDatabase = FirebaseDatabase.getInstance().getReference("companies");
                            vel = new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot userDetails : dataSnapshot.getChildren()) {
                                        if (userDetails.child("name").getValue().toString().equals(currsel)) {
                                            compkey = userDetails.getKey();
                                            break;
                                        }

                                    }
                                    mDatabase.child(compkey).child("update").setValue(updatemsg.getText().toString());
                                    Toast.makeText(UpdateCompany.this,"Update message added",Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            };
                            mDatabase.addListenerForSingleValueEvent(vel);
                        }
                        else
                        {
                            Toast.makeText(UpdateCompany.this,"Please enter some message",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
        theListView.setAdapter(adapter);
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // toggle clicked cell state
                System.out.println("toggle");
                currsel=companiesList.get(pos).getName().toString();
                System.out.println(currsel);
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                adapter.registerToggle(pos);
            }
        });
    }
}
