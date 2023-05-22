package eu.vsl.nutriplay;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import eu.vsl.nutriplay.R;
import eu.vsl.nutriplay.databinding.FragmentHomeBinding;
import eu.vsl.nutriplay.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {


    FragmentProfileBinding binding;
    FirebaseFirestore database;
    FirebaseUser user;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        database = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, pass, name;

                email = binding.emailBox.getText().toString();
                pass = binding.passBox.getText().toString();
                name = binding.nameBox.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getActivity(), "Please enter your email", Toast.LENGTH_SHORT).show();
                } if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getActivity(), "Please enter your name", Toast.LENGTH_SHORT).show();
                } if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getActivity(), "Please enter your password", Toast.LENGTH_SHORT).show();
                } else {
                    user.updateEmail(email);
                    database.collection("users")
                            .document(FirebaseAuth.getInstance().getUid())
                            .update("email", email, "name", name, "pass", pass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Update successful!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getActivity(), "An error Occurred", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

        return binding.getRoot();
    }
}