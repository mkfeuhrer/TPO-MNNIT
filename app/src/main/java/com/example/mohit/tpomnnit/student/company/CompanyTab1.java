package com.example.mohit.tpomnnit.student.company;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.mohit.tpomnnit.student.profile.MyProfile;
import com.example.mohit.tpomnnit.student.profile.UserData;
import com.example.mohit.tpomnnit.tpo.VerifyUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;


public class CompanyTab1 extends Fragment {
    //RecyclerView recyclerView;
    List<Companies> companiesList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase,mDatabase1;
    //CompaniesAdapter companiesAdapter;
    ArrayList registered;
    String companyId;
    String regnum;
    static String currsel;
    View view1;
    ValueEventListener vel,vel1;
    CompanyStudent companyStudent=(CompanyStudent)getActivity();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view1=inflater.inflate(R.layout.fragment_companytab1, container, false);
        mDatabase1 = FirebaseDatabase.getInstance().getReference("userdata");
        mDatabase=FirebaseDatabase.getInstance().getReference("companies");
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
                            //ArrayList<String> branch=new ArrayList<String>();
                            System.out.println("branch "+userDetails.child("branch").getValue());
                            //companies.setBranch(userDetails.child("branch").getValue().toString());
                            int flag=0;
                            for(int it=0;it<registered.size();it++)
                            {
                                if(companies.getName().equals(registered.get(it)))
                                {
                                    flag=1;
                                    break;
                                }
                            }
                            if(flag==0)
                                companiesList.add(companies);
                            //System.out.println("in"+ userDetails.child("name").getValue().toString()+" : "+companies.getCtc()+" : "+companies.getLocation());
                        }
                        addCompany();
                       // mDatabase.removeEventListener(vel1);
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
        final FoldingCellCompanyAdapter adapter= new FoldingCellCompanyAdapter(getContext(),companiesList,0);

        adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(getActivity(), currsel);

            }
        });

        // set elements to adapter
        theListView.setAdapter(adapter);

        // set on click event listener to list view
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
    public void showDialog(Activity activity, final String curreg) {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.onBackPressed();

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog_feedback);
        text.setText("Register");
        Button register = (Button) dialog.findViewById(R.id.register);
        final EditText regis = (EditText) dialog.findViewById(R.id.regis);
        final EditText pass = (EditText) dialog.findViewById(R.id.pass);
        regis.setText(regnum);
        regis.setFocusable(false);
        pass.setVisibility(View.GONE);

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Perfome Action

                registerCompanyToUser(regis.getText().toString().trim(),curreg);
                dialog.dismiss();
                Toast.makeText(dialog.getContext(),"Company Registered",Toast.LENGTH_LONG).show();
            }
        });

        dialog.show();

    }
    String key;
    void registerCompanyToUser(String regno,String curreg)
    {
        registered = new ArrayList<String>();
        final String cursel = curreg;
        System.out.println("Cursel"+cursel);
        final String reg=regno;
        mDatabase = FirebaseDatabase.getInstance().getReference("userdata");
        String userId = mDatabase.push().getKey();
        vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData user = dataSnapshot.getValue(UserData.class);
                for (DataSnapshot userDetails : dataSnapshot.getChildren()) {
                    System.out.println(userDetails.child("regnum").getValue().toString());
                    if (reg.equals(userDetails.child("regnum").getValue().toString())) {
                        key=userDetails.getKey();
                        registered= (ArrayList<String>) userDetails.child("companies").getValue();
                        registered.add(cursel);
                    }
                }
                mDatabase.child(key).child("companies").setValue(registered);
                //mDatabase.removeEventListener(vel);
                Intent intent=new Intent(getActivity(),CompanyStudent.class);
                intent.putExtra("reg",regnum);
                getActivity().finish();
                startActivity(intent);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addListenerForSingleValueEvent(vel);

    }
}