package com.example.passwordmanager.adapter;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passwordmanager.R;
import com.example.passwordmanager.activity.AccountActivity;
import com.example.passwordmanager.database.AppDatabase;
import com.example.passwordmanager.database.dao.AccountDao;
import com.example.passwordmanager.database.dao.SiteAppDao;
import com.example.passwordmanager.database.entity.Account;
import com.example.passwordmanager.util.Security;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {
    private final Context context;
    private final List<Account> accountList;

    public AccountAdapter(Context context, List<Account> accountList) {
        this.context = context;
        this.accountList = accountList;
    }

    public Account getAccountByPosition(int position) {
        return accountList.get(position);
    }

    public void insertAccount(Account account, int position) {
        accountList.add(position, account);
        notifyItemInserted(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Account account = accountList.get(position);
        holder.getTvUsername().setText(account.getUsername());
        holder.getTvNote().setText(account.getNote());
        holder.getLayoutAccount().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: add a bottom sheet to view details
                // TODO: don't forget the copy field after clicking the fields
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
                bottomSheetDialog.setContentView(R.layout.popup_account_item);
                TextInputEditText etUsername = bottomSheetDialog.findViewById(R.id.et_username);
                TextInputEditText etPassword = bottomSheetDialog.findViewById(R.id.et_password);
                TextInputEditText etNote = bottomSheetDialog.findViewById(R.id.et_note);

                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);

                if (etUsername != null) {
                    etUsername.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClipData clipData = ClipData.newPlainText("label", account.getUsername());
                            clipboardManager.setPrimaryClip(clipData);
                            Toast.makeText(context, "Username copied to clipboard", Toast.LENGTH_SHORT).show();
                        }
                    });
                    etUsername.setFocusable(false);
                    etUsername.setInputType(InputType.TYPE_NULL);
                    etUsername.setText(account.getUsername());
                }
                if (etPassword != null) {
                    Security security = new Security();
                    etPassword.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClipData clipData = ClipData.newPlainText("label", security.decrypt(account.getPassword()));
                            clipboardManager.setPrimaryClip(clipData);
                            Toast.makeText(context, "Password copied to clipboard", Toast.LENGTH_SHORT).show();
                        }
                    });
                    etPassword.setFocusable(false);
                    etPassword.setInputType(InputType.TYPE_NULL);
                    etPassword.setText(account.getPassword());
                }
                if (etNote != null) {
                    etNote.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClipData clipData = ClipData.newPlainText("label", account.getNote());
                            clipboardManager.setPrimaryClip(clipData);
                            Toast.makeText(context, "Note copied to clipboard", Toast.LENGTH_SHORT).show();
                        }
                    });
                    etNote.setFocusable(false);
                    etNote.setInputType(InputType.TYPE_NULL);
                    etNote.setText(account.getNote());
                }
                bottomSheetDialog.show();
            }
        });
        holder.getLayoutAccount().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.PopupDeleteAppStyle);
                builder.setTitle("Are you sure?");
                builder.setMessage("You want to delete this item? " + account.getUsername());
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AccountDao accountDao = AppDatabase.getInstance(context).accountDao();
                        accountDao.delete(account);
                        int currentPosition = holder.getAdapterPosition();
                        accountList.remove(currentPosition);
                        notifyItemRemoved(currentPosition);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvUsername, tvNote;
        private final LinearLayout layoutAccount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvUsername = itemView.findViewById(R.id.tv_username);
            this.tvNote = itemView.findViewById(R.id.tv_note);
            this.layoutAccount = itemView.findViewById(R.id.layout_account_item);
        }

        public TextView getTvUsername() {
            return tvUsername;
        }

        public TextView getTvNote() {
            return tvNote;
        }

        public LinearLayout getLayoutAccount() {
            return layoutAccount;
        }
    }
}
