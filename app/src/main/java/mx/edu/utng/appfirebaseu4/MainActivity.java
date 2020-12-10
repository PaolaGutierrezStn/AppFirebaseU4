package mx.edu.utng.appfirebaseu4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import mx.edu.utng.appfirebaseu4.model.Libro;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private List<Libro> listLibro = new ArrayList<Libro>();
    ArrayAdapter<Libro> arrayAdapterLibro;

    EditText nombre;
    EditText autor;
    EditText editorial;

    ListView listaLibros;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Libro libroSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre = findViewById(R.id.edtNomLibro);
        autor = findViewById(R.id.edtNomAutor);
        editorial = findViewById(R.id.edtNomEditorial);

        listaLibros = findViewById(R.id.lsvListaLibros);

        inicializarFirebase();
        listarDatos();

        listaLibros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                libroSelected = (Libro) parent.getItemAtPosition(position);
                nombre.setText(libroSelected.getNombre());
                autor.setText(libroSelected.getAutor());
                editorial.setText(libroSelected.getEditorial());
                }
        });

    }

    private void listarDatos() {
        databaseReference.child("Libro").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listLibro.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    Libro l = objSnaptshot.getValue(Libro.class);
                    listLibro.add(l);

                    arrayAdapterLibro = new ArrayAdapter<Libro>(MainActivity.this, android.R.layout.simple_list_item_1, listLibro);
                    listaLibros.setAdapter(arrayAdapterLibro);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String nombreLibro = nombre.getText().toString();
        String autorLibro = autor.getText().toString();
        String editorialLibro = editorial.getText().toString();

        switch (item.getItemId()){
            case R.id.icon_add:{
                if (nombreLibro.equals("")||autorLibro.equals("")||editorialLibro.equals("")){
                    validacion();
                }
                else {
                    Libro l = new Libro();
                    l.setUid(UUID.randomUUID().toString());
                    l.setNombre(nombreLibro);
                    l.setAutor(autorLibro);
                    l.setEditorial(editorialLibro);
                    databaseReference.child("Libro").child(l.getUid()).setValue(l);
                    Toast.makeText(this, "Agregado", Toast.LENGTH_LONG).show();
                    limpiarCajas();
                }
                break;
            }
            case R.id.icon_save:{
                Libro l = new Libro();
                l.setUid(libroSelected.getUid());
                l.setNombre(nombre.getText().toString().trim());
                l.setAutor(autor.getText().toString().trim());
                l.setEditorial(editorial.getText().toString().trim());
                databaseReference.child("Libro").child(l.getUid()).setValue(l);
                Toast.makeText(this,"Actualizado", Toast.LENGTH_LONG).show();
                limpiarCajas();
                break;
            }
            case R.id.icon_delete:{
                Libro l = new Libro();
                l.setUid(libroSelected.getUid());
                databaseReference.child("Libro").child(l.getUid()).removeValue();
                Toast.makeText(this,"Eliminado", Toast.LENGTH_LONG).show();
                limpiarCajas();
                break;
            }
            default:break;
        }
        return true;
    }

    private void limpiarCajas() {
        nombre.setText("");
        autor.setText("");
        editorial.setText("");
    }

    private void validacion() {
        String nombreLibro = nombre.getText().toString();
        String autorLibro = autor.getText().toString();
        String editorialLibro = editorial.getText().toString();
        if (nombreLibro.equals("")){
            nombre.setError("Required");
        }
        else if (autorLibro.equals("")){
            autor.setError("Required");
        }
        else if (editorialLibro.equals("")){
            editorial.setError("Required");
        }
    }
}