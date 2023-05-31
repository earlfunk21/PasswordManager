package com.example.passwordmanager.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passwordmanager.R;
import com.example.passwordmanager.activity.AccountActivity;
import com.example.passwordmanager.database.AppDatabase;
import com.example.passwordmanager.database.dao.SiteAppDao;
import com.example.passwordmanager.database.entity.SiteApp;

import java.util.List;

public class SiteAppAdapter extends RecyclerView.Adapter<SiteAppAdapter.ViewHolder> {
    private Context context;
    private List<SiteApp> appList;

    public SiteAppAdapter(Context context, List<SiteApp> appList) {
        this.context = context;
        this.appList = appList;
    }

    public SiteApp getAppByPosition(int position) {
        return appList.get(position);
    }

    public void insertApp(SiteApp siteApp, int position){
        appList.add(position, siteApp);
        notifyItemInserted(position);
    }

    public void setAppList(List<SiteApp> appList){
        this.appList = appList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.app_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SiteApp siteApp = appList.get(position);
        holder.getTvAppName().setText(siteApp.getName());
        holder.getLayoutApp().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AccountActivity.class);
                intent.putExtra("id", siteApp.getId());
                context.startActivity(intent);
            }
        });

        holder.getLayoutApp().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.PopupDeleteAppStyle);
                builder.setTitle("Are you sure?");
                builder.setMessage("You want to delete this item? " + siteApp.getName());
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SiteAppDao siteAppDao = AppDatabase.getInstance(context).siteAppDao();
                        siteAppDao.delete(siteApp);
                        int currentPosition = holder.getAdapterPosition();
                        appList.remove(currentPosition);
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
        return appList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAppName;
        private final LinearLayout layoutApp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvAppName = itemView.findViewById(R.id.tv_app_name);
            this.layoutApp = itemView.findViewById(R.id.layout_app_item);
        }

        public TextView getTvAppName() {
            return tvAppName;
        }

        public LinearLayout getLayoutApp() {
            return layoutApp;
        }
    }
}
