package com.example.mohit.tpomnnit.messenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mohit.tpomnnit.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class chat extends AppCompatActivity {

    DatabaseReference mdatabase,mdatabase2;
    ArrayList<String> users;
    ArrayList<ChatData> cd;
    ChatData chatData;
    String Arr[];
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    String regnum;
    ValueEventListener vel;
    Button button;
    private ChatUserAdapter chatUserAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        regnum = getIntent().getStringExtra("reg");
        button=(Button)findViewById(R.id.button);
        cd=new ArrayList<ChatData>();
        mdatabase = FirebaseDatabase.getInstance().getReference("messagess");
        vel=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userDetails:dataSnapshot.getChildren())
                {
                    if(userDetails.child("users").getValue().toString().equals(regnum))
                    {
                        //userDetails.child("chatdata").getValue());
                        chatData=new ChatData();
                        chatData.setOtheruser(userDetails.child("chatdata").child("otheruser").getValue().toString());
                        chatData.setChat((ArrayList<String>) userDetails.child("chatdata").child("chat").getValue());
                        chatData.setFlag((ArrayList<Integer>) userDetails.child("chatdata").child("flag").getValue());
                        cd.add(chatData);
                        System.out.println(userDetails.child("chatdata").getValue());

                    }
                    System.out.println("cd : "+cd.toString());
                }
                recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                //progressBar=(ProgressBar)findViewById(R.id.progressBar);
                ArrayList<String> art=new ArrayList<>();
                for(int i=0;i<cd.size();i++)
                {
                    art.add(cd.get(i).getOtheruser());
                }
                chatUserAdapter = new ChatUserAdapter(art);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(chatUserAdapter);
                recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        /*progressBar.setVisibility(View.VISIBLE);
                        Disease_type disease_type = diseaseList.get(position);
                        String ur = "https://api.infermedica.com/v2/conditions/";
                        ur = ur + disease_type.getID();
                        new Disease.QuestionAsynTask().execute(ur);*/
                        //Intent intent=new Intent(starQuestion.this,starQuestionDisplay.class);
                        //intent.putExtra("position",position);
                        //startActivity(intent);
                        //finish();
                        //Toast.makeText(getApplicationContext(), question.getQUESTION() + " is selected!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mdatabase.addListenerForSingleValueEvent(vel);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(chat.this,SendMessage.class);
                intent.putExtra("reg",regnum);
                startActivity(intent);
            }
        });

}
}
