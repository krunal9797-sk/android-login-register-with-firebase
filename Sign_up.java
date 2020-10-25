package clg.brijesh.mohit.coffee_desk;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.jar.Attributes;

public class Sign_up extends AppCompatActivity
{

    TextInputLayout li_name,li_email,li_number,li_password;
    EditText txt_name,txt_email,txt_number,txt_password;
    Button btn_Sign_Up,btn_sign_in;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;

    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        allocatememory();
        setEvents();
    }

    private void setEvents()
    {
        btn_Sign_Up.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final String Name = txt_name.getText().toString().trim();
                final String Email = txt_email.getText().toString().trim();
                final String MobileNo = txt_number.getText().toString().trim();
                final String Password = txt_password.getText().toString().trim();

                if(TextUtils.isEmpty(Name))
                {
                    txt_name.setError("Please enter name");
                    return;
                }
                if(TextUtils.isEmpty(Email))
                {
                    txt_email.setError("Please Enter Email");
                    return;
                }
                if(TextUtils.isEmpty(MobileNo))
                {
                    txt_number.setError("Please Enter Number");
                    return;
                }
                if(TextUtils.isEmpty(Password))
                {
                    txt_password.setError("Please Enter Password");
                    return;
                }
                if(Password.length()<6)
                {
                    txt_password.setError("Password too Short");
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(Sign_up.this, new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful())
                                {
                                    Desk_User information = new Desk_User
                                            (
                                            Name,
                                            Email,
                                            MobileNo
                                    );

                                    FirebaseDatabase.getInstance().getReference("Desk_User")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>()
                                    {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            Toast.makeText(ctx,"Register Completed ",Toast.LENGTH_SHORT).show();
                                            Intent SignIn = new Intent(ctx,Sign_InD.class);
                                            startActivity(SignIn);
                                        }
                                    });

                                }

                                else
                                    {
                                        Toast.makeText(ctx,"Network issue",Toast.LENGTH_SHORT).show();

                                    }

                            }
                        });


            }
        });

        btn_sign_in.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent SignIn = new Intent(ctx,Sign_InD.class);
                startActivity(SignIn);
            }
        });
    }

    private void allocatememory()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference("Desk_User");
        firebaseAuth = FirebaseAuth.getInstance();

        txt_name = findViewById(R.id.txt_name);
        txt_email = findViewById(R.id.txt_email);
        txt_number = findViewById(R.id.txt_number);
        txt_password = findViewById(R.id.txt_password);

        btn_Sign_Up = findViewById(R.id.btn_Sign_Up);
        btn_sign_in = findViewById(R.id.btn_sign_in);
    }
}
