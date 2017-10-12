package com.example.mohit.tpomnnit.student.company;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohit.tpomnnit.R;
import com.example.mohit.tpomnnit.student.profile.UserData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;


public class CompanyTab2 extends Fragment {
    List<Companies> companiesList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase,mDatabase1;
    //CompaniesAdapter companiesAdapter;
    ArrayList registered;
    String companyId;
    String regnum;
    static String currsel;
    int curindex=0;
    View view1;
    ValueEventListener vel,vel1;
    CompanyStudent companyStudent=(CompanyStudent)getActivity();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view1= inflater.inflate(R.layout.fragment_companytab2, container, false);
        mDatabase=FirebaseDatabase.getInstance().getReference("companies");
        mDatabase1 = FirebaseDatabase.getInstance().getReference("userdata");
        companyId=mDatabase.push().getKey();
        companiesList=new ArrayList<>();

        regnum=CompanyStudent.regnum;
        prepareData();
        return view1;

    }
    void prepareData()
    {
        System.out.println("in prepare data");
        registered = new ArrayList<String>();
        vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData user = dataSnapshot.getValue(UserData.class);
                for (DataSnapshot userDetails : dataSnapshot.getChildren()) {
                    System.out.println(userDetails.child("regnum").getValue().toString());
                    if (regnum.equals(userDetails.child("regnum").getValue().toString())) {
                        registered= (ArrayList<String>) userDetails.child("companies").getValue();
                    }
                }
                //mDatabase1.removeEventListener(vel);
                vel1 = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        companiesList.clear();
                        for(DataSnapshot userDetails : dataSnapshot.getChildren()) {
                            //Companies companies=new Companies(userDetails.child("name").getValue().toString(),userDetails.child("ctc").getValue().toString(),userDetails.child("location").getValue().toString(),null,null,null,null,null,null,null,null,null);
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
                            companies.setUpdate(userDetails.child("update").getValue().toString());
                            //ArrayList<String> branch=new ArrayList<String>();
                            System.out.println("branch "+userDetails.child("branch").getValue());
                            //companies.setBranch(userDetails.child("branch").getValue().toString());
                            int flag=0;
                            for(int it=0;it<registered.size();it++)
                            {
                                if(companies.getName().equals(registered.get(it)))
                                {
                                    companiesList.add(companies);
                                    break;
                                }
                            }
                            //System.out.println("in"+ userDetails.child("name").getValue().toString()+" : "+companies.getCtc()+" : "+companies.getLocation());
                        }
                        addCompany();
                        //mDatabase.removeEventListener(vel1);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                mDatabase.addListenerForSingleValueEvent(vel1);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase1.addListenerForSingleValueEvent(vel);

    }
    void addCompany()
    {
        ListView theListView = (ListView) view1.findViewById(R.id.mainListView);
        final FoldingCellCompanyAdapter adapter= new FoldingCellCompanyAdapter(getContext(),companiesList,2);
        adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(getActivity(), currsel);

            }
        });

        theListView.setAdapter(adapter);

        // set on click event listener to list view
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // toggle clicked cell state
                System.out.println("toggle");
                currsel=companiesList.get(pos).getName().toString();
                curindex=pos;
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                adapter.registerToggle(pos);
            }
        });
    }
    public void showDialog(Activity activity, final String curreg) {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.update_company_dialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.onBackPressed();
        TextView lable=(TextView)dialog.findViewById(R.id.text_dialog_feedback);
        TextView msg = (TextView) dialog.findViewById(R.id.regis);
        Button register = (Button) dialog.findViewById(R.id.register);
        register.setText("Close");
        lable.setText("        Update Message        ");
        msg.setText(companiesList.get(curindex).getUpdate());
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Perfome Action
                dialog.dismiss();

            }
        });

        dialog.show();

    }

}
