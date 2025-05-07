package com.example.helloandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.helloandroid.R;
import com.example.helloandroid.entities.Color;
import com.example.helloandroid.entities.Contacto;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormContactActivity extends AppCompatActivity {
    Button btnSave;
    Button btnDelete;
    EditText etContactoName;
    EditText etContactTel;
    Spinner sGenero;
    EditText etDireccion;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setUpViews();
        setUpButtonSave();
        setUpButtonDelete();

        Intent intent = getIntent();
        String contactName = intent.getStringExtra("contactName");
        String contactTel = intent.getStringExtra("contactTel");
        String contactDireccion = intent.getStringExtra("contactDireccion");
        id = intent.getStringExtra("id");

        if (contactName != null && contactTel != null) {
            etContactoName.setText(contactName);
            etContactTel.setText(contactTel);
            etDireccion.setText(contactDireccion);
        }

        if (id == null) {
            btnDelete.setVisibility(View.GONE);
        }
    }
    private void setUpViews() {
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        etContactoName = findViewById(R.id.etContactoName);
        etContactTel = findViewById(R.id.etContactTel);
        sGenero = findViewById(R.id.sGenero);
        etDireccion = findViewById(R.id.etDireccion);
    }

    private void setUpButtonSave() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id == null) {
                    save();
                } else {
                    update();
                }
            }
        });
    }

    private void setUpButtonDelete() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id != null) {
                    delete();
                }
            }
        });
    }

    private void save() {
        Contacto contacto = new Contacto();
        contacto.setNombre(etContactoName.getText().toString());
        contacto.setTelefono(etContactTel.getText().toString());
        contacto.setGenero(sGenero.getSelectedItem().toString());
        contacto.setDireccion(etDireccion.getText().toString());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference contactRef = myRef.child("contacts").push();
        contacto.setId(contactRef.getKey());
        contactRef.setValue(contacto)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(getApplicationContext(), "Contacto creado", Toast.LENGTH_LONG).show();
                }).addOnFailureListener(f -> {
                    Toast.makeText(getApplicationContext(), "Error al crear contacto", Toast.LENGTH_LONG).show();
                });
    }

    private void update() {
        Contacto contacto = new Contacto();
        contacto.setId(id);
        contacto.setNombre(etContactoName.getText().toString());
        contacto.setTelefono(etContactTel.getText().toString());
        contacto.setGenero(sGenero.getSelectedItem().toString());
        contacto.setDireccion(etDireccion.getText().toString());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("contacts");

        myRef.child(id).setValue(contacto)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(getApplicationContext(), "Contacto actualizado", Toast.LENGTH_LONG).show();
                }).addOnFailureListener(f -> {
                    Toast.makeText(getApplicationContext(), "Error al actualizar contacto", Toast.LENGTH_LONG).show();
                });
    }
    private void delete() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("contacts");
        myRef.child(id).removeValue()
                .addOnSuccessListener(s -> {
                    finish();
                    Toast.makeText(getApplicationContext(), "Contacto eliminado", Toast.LENGTH_LONG).show();
                });
    }
}
