package com.example.passwordmanager.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.passwordmanager.BuildConfig;
import com.example.passwordmanager.R;
import com.example.passwordmanager.adapter.AccountAdapter;
import com.example.passwordmanager.database.AppDatabase;
import com.example.passwordmanager.database.dao.AccountDao;
import com.example.passwordmanager.database.dao.SiteAppDao;
import com.example.passwordmanager.database.entity.Account;
import com.example.passwordmanager.database.entity.SiteApp;
import com.example.passwordmanager.util.Security;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AccountActivity extends AppCompatActivity {

    private long appId;
    private AccountDao accountDao;
    private AccountAdapter accountAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        accountDao = AppDatabase.getInstance(this).accountDao();
        SiteAppDao siteAppDao = AppDatabase.getInstance(this).siteAppDao();

        appId = getIntent().getLongExtra("id", -1);
        SiteApp siteApp = siteAppDao.getSiteAppById(appId);

        TextView tvAppNameAccount = findViewById(R.id.tv_app_name_account);
        tvAppNameAccount.setText(siteApp.getName());

        List<Account> accountList = new ArrayList<>(accountDao.getAccountListByAppId(appId));
        accountAdapter = new AccountAdapter(this, accountList);
        RecyclerView rvAccount = findViewById(R.id.rv_account);
        rvAccount.setAdapter(accountAdapter);

        ImageButton ibBack = findViewById(R.id.ib_back);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        FloatingActionButton fabAddAccount = findViewById(R.id.fab_add_account);
        fabAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupAddAccount();
            }
        });
    }

    private void popupAddAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.PopupAddAppStyle);
        View view = LayoutInflater.from(this)
                .inflate(R.layout.popup_account_item, null);
        builder.setView(view);
        TextInputEditText etUsername = view.findViewById(R.id.et_username);
        TextInputEditText etPassword = view.findViewById(R.id.et_password);
        TextInputEditText etNote = view.findViewById(R.id.et_note);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String username = Objects.requireNonNull(etUsername.getText()).toString();
                String password = Objects.requireNonNull(etPassword.getText()).toString();
                String note = Objects.requireNonNull(etNote.getText()).toString();
                Account account = new Account(username, password, note, appId);
                try {
                    Security security = new Security();
                    account.setPassword(security.encrypt(password));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                Log.d("Password", "Your Password " + account.getPassword());
                accountDao.insert(account);
                accountAdapter.insertAccount(account, 0);
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