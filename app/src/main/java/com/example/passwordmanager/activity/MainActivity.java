package com.example.passwordmanager.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.passwordmanager.R;
import com.example.passwordmanager.adapter.SiteAppAdapter;
import com.example.passwordmanager.database.AppDatabase;
import com.example.passwordmanager.database.dao.SiteAppDao;
import com.example.passwordmanager.database.entity.SiteApp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private SiteAppDao siteAppDao;
    private SiteAppAdapter siteAppAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        siteAppDao = AppDatabase.getInstance(this).siteAppDao();
        List<SiteApp> appList = new ArrayList<>(siteAppDao.getSiteAppList());
        siteAppAdapter = new SiteAppAdapter(this, appList);
        RecyclerView rvApp = findViewById(R.id.rv_app);
        rvApp.setAdapter(siteAppAdapter);

        FloatingActionButton fabAddApp = findViewById(R.id.fab_add_app);
        fabAddApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupAddApp();
            }
        });

        // TODO: Entity, Dao, Adapter, Activity and Layouts for Password
    }

    private void popupAddApp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.PopupAddAppStyle);
        View view = LayoutInflater.from(this)
                .inflate(R.layout.popup_add_app_item, null);
        builder.setView(view);
        TextInputEditText etAppName = view.findViewById(R.id.et_app_name);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String appName = Objects.requireNonNull(etAppName.getText()).toString();
                SiteApp siteApp = new SiteApp(appName);
                long generatedId = siteAppDao.insert(siteApp);
                siteApp = siteAppDao.getSiteAppById(generatedId);
                siteAppAdapter.insertApp(siteApp, 0);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}