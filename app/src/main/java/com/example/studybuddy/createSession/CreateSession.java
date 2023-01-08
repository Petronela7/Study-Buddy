package com.example.studybuddy.createSession;

import static android.database.sqlite.SQLiteDatabase.CREATE_IF_NECESSARY;
import static android.database.sqlite.SQLiteDatabase.openDatabase;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;
import static com.example.studybuddy.createSession.MapActivity.LONGITUDE;
import static com.example.studybuddy.createSession.MapActivity.longitude;
import static com.example.studybuddy.createSession.MapActivity.latitude;
import static com.example.studybuddy.database.SQLiteHelper.SESSION_TABLE_NAME;
import static com.example.studybuddy.database.SQLiteHelper.Session_Date_Start;
import static com.example.studybuddy.database.SQLiteHelper.Session_ID;
import static com.example.studybuddy.database.SQLiteHelper.Session_Location;
import static com.example.studybuddy.database.SQLiteHelper.Session_Name;
import static com.example.studybuddy.database.SQLiteHelper.Session_Subject;
import static com.example.studybuddy.database.SQLiteHelper.Session_Time_Start;
import static com.example.studybuddy.database.SQLiteHelper.Session_Year;
import static com.example.studybuddy.database.SQLiteHelper.USER_TABLE_NAME;
import static com.example.studybuddy.login.LoginFragment.UserEmail;

import androidx.appcompat.app.AppCompatActivity;
import static com.example.studybuddy.createSession.MapActivity.LATITUDE;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.studybuddy.R;
import com.example.studybuddy.SearchActivity2;
import com.example.studybuddy.dashboard.DashboardActivity;
import com.example.studybuddy.database.SQLiteHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CreateSession extends AppCompatActivity {

    String[] years = {"I", "II", "III", "IV"};
    Button create_button;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    Boolean EditTextEmptyHolder;
    MaterialTextView date_text, time_text;
    MaterialButton dateButton, timeButton;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    TextInputEditText subject, group;
    String subjectHolder, groupHolder, selectedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);
        sqLiteHelper = new SQLiteHelper(getApplicationContext());
        subject = findViewById(R.id.subjectText);
        group = findViewById(R.id.groupText);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        date_text = findViewById(R.id.date_text);
        dateButton = findViewById(R.id.date_picker_button);
        time_text = findViewById(R.id.time_text);
        timeButton = findViewById(R.id.time_picker_button);

        create_button = findViewById(R.id.create_button);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        adapterItems = new ArrayAdapter<>(this, R.layout.dropdown_years, years);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String year = adapterItems.getItem(position);
            }
        });

       // selectedLocation = findViewById(R.id.selectedLocationOnMap);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(extras.getDouble(latitude), extras.getDouble(longitude), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                selectedLocation = String.format("%s %s", address, city);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select date").build();

                materialDatePicker.addOnPositiveButtonClickListener(selection -> date_text.setText(String.format("%s", materialDatePicker.getHeaderText())));
                materialDatePicker.show(getSupportFragmentManager(), "TAG");
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateSession.this, com.airbnb.lottie.R.style.Theme_AppCompat_Dialog,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                c.setTimeZone(TimeZone.getDefault());
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm");
                                String time = simpleDateFormat.format(c.getTime());
                                time_text.setText(time);
                            }
                        }, hours, mins, false);
                timePickerDialog.show();
            }
        });



       create_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               // Creating SQLite database if dose n't exists
               SQLiteDataBaseBuild();
               // Creating SQLite table if dose n't exists.
               SQLiteTableBuild();
               // Checking EditText is empty or Not.
               CheckEditTextStatus();
               InsertSessionIntoDatabase();
           }
       });
    }

    private void InsertSessionIntoDatabase() {
        // If editText is not empty then this block will executed.
        if(EditTextEmptyHolder)
        {
            // SQLite query to insert data into table.
            SQLiteDataBaseQueryHolder = "INSERT INTO "+ SESSION_TABLE_NAME+" (Name, Subject, Year_Of_Study, Date_Start, Time_Start,Location) VALUES('" +subjectHolder+"', '"+groupHolder+"'," +
                    " '"+autoCompleteTextView.getText().toString()+"', '"+date_text.getText().toString()+"', '"+time_text.getText().toString()+"', '"+selectedLocation+"');";
            // Executing query.
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
            // Closing SQLite database object.
            sqLiteDatabaseObj.close();
            Toast.makeText(getApplicationContext(),"Session added", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
            startActivity(intent);
        }
        // This block will execute if any of the registration EditText is empty.
        else {
            // Printing toast message if any of EditText is empty.
            Toast.makeText(getApplicationContext(),"Please fill all the required Fields.", Toast.LENGTH_LONG).show();
        }
    }


    private void SQLiteDataBaseBuild() {
        File dbpath = getBaseContext().getDatabasePath(SQLiteHelper.DATABASE_NAME);
        if (!dbpath.getParentFile().exists()) {
            dbpath.getParentFile().mkdirs();
        }
        sqLiteDatabaseObj = openDatabase(dbpath.getPath(), null, CREATE_IF_NECESSARY);
    }
    private void SQLiteTableBuild() {
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS "+SESSION_TABLE_NAME+
                " ("+Session_ID+" INTEGER PRIMARY KEY, "+Session_Name+" VARCHAR, "+Session_Subject+" VARCHAR," +
                ""+Session_Year+" INT, "+Session_Date_Start+" VARCHAR, "+Session_Time_Start+" VARCHAR, "+Session_Location+" VARCHAR)");
    }
    private void CheckEditTextStatus() {
       // Getting value from All EditText and storing into String Variables.
       subjectHolder = subject.getText().toString();
       groupHolder = group.getText().toString();

       if(TextUtils.isEmpty(subjectHolder) || TextUtils.isEmpty(groupHolder)
               || TextUtils.isEmpty(date_text.getText()) || TextUtils.isEmpty(time_text.getText()) ){
           EditTextEmptyHolder = false ;
       }
       else {
           EditTextEmptyHolder = true ;
       }
    }

}