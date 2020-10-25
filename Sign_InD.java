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

public class Sign_InD extends AppCompatActivity
{
    TextInputLayout il_email,il_password;
    EditText txt_email,txt_password;
    private Button btn_sign_in,btn_forgotpass;
    Context ctx = this;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_d);
        allocatememory();
        setEvents();
    }

    private void setEvents()
    {
        btn_sign_in.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final String Email = txt_email.getText().toString().trim();
                final String Password = txt_password.getText().toString().trim();

                if(TextUtils.isEmpty(Email))
                {
                    txt_email.setError("Please Enter Email");
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

                firebaseAuth.signInWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(Sign_InD.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful())
                                {
                                    Intent SignIn = new Intent(ctx,Choose_Table.class);
                                    startActivity(SignIn);
                                }
                                else
                                    {
                                        Toast.makeText(ctx,"Login Failed or User Invalid ",Toast.LENGTH_SHORT).show();
                                    }

                            }
                        });

            }
        });


        btn_forgotpass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });
    }

    private void allocatememory()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference("Desk_User");
        firebaseAuth = FirebaseAuth.getInstance();

        txt_email = findViewById(R.id.txt_email);
        txt_password = findViewById(R.id.txt_password);
        btn_forgotpass = findViewById(R.id.btn_forgotpass);
        btn_sign_in = findViewById(R.id.btn_sign_in);

        il_email = findViewById(R.id.il_email);
        il_password = findViewById(R.id.il_password);
    }
}
