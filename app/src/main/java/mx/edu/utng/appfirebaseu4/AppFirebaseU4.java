package mx.edu.utng.appfirebaseu4;

import com.google.firebase.database.FirebaseDatabase;

public class AppFirebaseU4 extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
