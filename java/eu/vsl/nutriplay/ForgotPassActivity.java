package eu.vsl.nutriplay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import eu.vsl.nutriplay.databinding.ActivityForgotPassBinding;
import eu.vsl.nutriplay.databinding.ActivitySignupBinding;

public class ForgotPassActivity extends AppCompatActivity {

    ActivityForgotPassBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email;
                email = binding.EmailBox.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(ForgotPassActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                } else {
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPassActivity.this, "Please check your email, don't forget to look at your spam folder", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgotPassActivity.this, LoginActivity.class));
                            }
                            else {
                                Toast.makeText(ForgotPassActivity.this, "An error Occurred", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}